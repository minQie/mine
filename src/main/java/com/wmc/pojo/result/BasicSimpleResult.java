package com.wmc.pojo.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.Lists;
import com.wmc.common.domain.BaseIdDomain;
import com.wmc.common.exception.ApiErrorCodes;
import com.wmc.common.exception.ApiException;
import io.ebean.annotation.DbArray;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 作用：EBean规范的数据表实体 --转化得到--> 交给Jackson处理的结果集实体
 * <p>
 * 样例：（Xxx是IdDomian的子类）
 * BasicSimpleResult&#060;Xxx&#062; xxxSimpleResult = BasicSimpleResult.of(xxx)
 * BasicSimpleResult&#060;Xxx&#062; xxxSimpleResult = BasicSimpleResult.entity(xxx).level(ForeignEntityRelatedLevel.BASIC).build()
 * <p>
 * 转化过程概括：
 * <p>
 * 一、转化硬性条件：
 * 1、必须有get方法
 * 2、不能被@JsonIgnore修饰
 * <p>
 * 二、可指定ForeignEntityRelatedLevel“外键实体关联级别”，会影响转化过程
 * BASIC：数据表实体的所有非数据表实体都会被序列化
 * ID：数据表实体的ID会被序列化
 * NONE（默认）：不序列化数据表实体实体的任何字段
 * <p>
 * PS:
 * 允许在给定的数据库实体的基础上，添加额外的属性，调用方法
 *
 * @Author 王敏聪
 * @Date 2019/11/1 15:37
 */
public class BasicSimpleResult<T extends BaseIdDomain> {

    /**
     * 默认忽略的属性名称
     */
    private static List<String> defaultIgnorePropertyList = Lists.newArrayList("_ebean_props", "_EBEAN_MARKER", "class");

    /**
     * 存储最终结果集
     */
    @Getter
    @JsonValue
    private Map<String, Object> resultMap;

    /**
     * 数据表实体
     */
    private T entity;

    /**
     * 关联级别
     */
    @JsonIgnore
    private ForeignEntityRelatedLevel level = ForeignEntityRelatedLevel.NONE;

    /**
     * 构造方法私有
     */
    private BasicSimpleResult(T entity) {
        this.entity = entity;
    }

    /**
     * 一步到位获取实例的静态方法
     *
     * @param entity 本项目的数据表实体
     * @param <T>    IdDomain子类
     * @return 本类实例
     */
    public static <T extends BaseIdDomain> BasicSimpleResult<T> of(T entity) {
        return new BasicSimpleResult<>(entity).build();
    }

    /**
     * 按步骤构建实例的第一步：设置实体
     *
     * @param entity 本项目的数据表实体
     * @param <T>    IdDomain子类
     * @return
     */
    public static <T extends BaseIdDomain> BasicSimpleResult<T> entity(T entity) {
        return new BasicSimpleResult<>(entity);
    }

    /**
     * 按步骤构建实例的第二步：设置构建级别
     *
     * @param level 构建级别
     * @return
     */
    public BasicSimpleResult<T> level(ForeignEntityRelatedLevel level) {
        this.level = level;
        return this;
    }

    /**
     * 构建实例的最后也是核心的一步：开始构建
     *
     * @return
     */
    public BasicSimpleResult<T> build() {
        if (entity == null) {
            return null;
        }
        return construct();
    }

    /**
     * 将数据表实体序列化得到结果集的核心方法
     *
     * @return
     */
    private BasicSimpleResult<T> construct() {
        if (entity == null) {
            throw new ApiException(ApiErrorCodes.FORM_NULL, "entity must not be null");
        }

        Field[] declaredFields = entity.getClass().getDeclaredFields();

        resultMap = new HashMap<>(declaredFields.length);

        Class<?> entityClass = entity.getClass();
        // 返回的PropertyDescriptor数组中，居然还有根据Object的getClass方法生成的（走到下面判断是否有被@JsonIgnore修饰那里，就凉凉了）
        // 原因：BeanUtil.getPropertyDescriptors是根据get方法来找属性的，反射机制则是找变量
        PropertyDescriptor[] entityPds = BeanUtils.getPropertyDescriptors(entityClass);

        // 遍历result的所有成员变量
        for (PropertyDescriptor entityPd : entityPds) {
            // 成员名称
            String propertyName = entityPd.getName();
            /*
             * 结果集实体的变量能够被序列化的条件:
             * 1、不在默认忽略列表中
             */
            if (defaultIgnorePropertyList.contains(propertyName)) {
                continue;
            }
            // 成员引用
            Field field = this.getClassField(entityClass, propertyName);
            // 成员类型
            Class<?> propertyType = entityPd.getPropertyType();
            // 2、必须有get方法
            Method readMethod = entityPd.getReadMethod();
            boolean haveReadMethod = readMethod != null;

            // 3、没有被@JsonIgnore修饰
            boolean isNotDecorateWithJsonIgnore = field.getAnnotation(JsonIgnore.class) == null;

            if (haveReadMethod && isNotDecorateWithJsonIgnore) {
                try {
                    if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                        readMethod.setAccessible(true);
                    }
                    Object propertyValue = readMethod.invoke(entity);

                    // 1、枚举的额外处理
                    boolean ifIsMesEnum = ValueEnum.class.isAssignableFrom(propertyType) && VerboseEnum.class.isAssignableFrom(propertyType);
                    if (ifIsMesEnum) {
                        propertyValue = BasicEnumResult.of((ValueEnum & VerboseEnum) propertyValue);
                    }

                    // 2、数据库实体的额外处理
                    /*
                      必须实现Serializable接口（即默认的基本的包装数据类型，想要被序列化只要实现改接口）
                      打开反编译的WhenDomain字节码，会发现多实现了一个EntityBean(extends Serializable)接口（神坑）
                     */
                    boolean isMesDomain = BaseIdDomain.class.isAssignableFrom(propertyType);
                    boolean ifList = Collection.class.isAssignableFrom(propertyType);
                    boolean ifListIsNotDecorateWithDbArray = field.getAnnotation(DbArray.class) == null;
                    if (isMesDomain) {
                        switch (level) {
                            case BASIC:
                                propertyValue = BasicSimpleResult.of((BaseIdDomain) propertyValue);
                                break;
                            case ID:
                                propertyName += "Id";
                                propertyValue = ((BaseIdDomain) propertyValue).getId();
                                break;
                            case NONE:
                                propertyName = null;
                                break;
                            default:
                        }
                    }
                    // 3、集合（没有被@DbArray修饰，即实体集合）
                    else if (ifList && ifListIsNotDecorateWithDbArray) {
                        switch (level) {
                            case BASIC:
                                propertyValue = ((List<BaseIdDomain>) propertyValue).stream().map(BasicSimpleResult::of).collect(Collectors.toList());
                                break;
                            case ID:
                                propertyName = propertyName.substring(0, propertyName.length() - 1) + "Ids";
                                propertyValue = ((List<BaseIdDomain>) propertyValue).stream().map(BaseIdDomain::getId).collect(Collectors.toList());
                                break;
                            case NONE:
                                propertyName = null;
                                break;
                            default:
                        }
                    }

                    if (StringUtils.isNotBlank(propertyName)) {
                        resultMap.put(propertyName, propertyValue);
                    }
                } catch (Throwable ex) {
                    throw new FatalBeanException("Could not resolve property '" + propertyName + "'", ex);
                }
            }
        }
        return this;
    }

    /**
     * 查询一个类指定名称的属性（递归找：当前类找不到，就找当前类的父类）
     *
     * @param clazz     字节码对象
     * @param fieldName 属性名
     * @return
     */
    private Field getClassField(Class clazz, String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException ignored) {
            return this.getClassField(clazz.getSuperclass(), fieldName);
        } catch (NullPointerException e) {
            System.out.println(fieldName);
        }
        return field;
    }

    /**
     * 为返回的结果集添加额外的属性
     *
     * @param key   key
     * @param value value
     */
    public void addProperty(String key, Object value) {
        resultMap.put(key, value);
    }

}

