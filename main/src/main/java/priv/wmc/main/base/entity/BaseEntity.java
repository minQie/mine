package priv.wmc.main.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import priv.wmc.main.config.mybatis.MyMetaObjectHandler;

/**
 * 所有数据表实体都应具有的字段
 *
 * 注意：想要使用Mybatis Plus的AR（Active Record）则数据表实体一定需要有对应的Mapper
 *
 * @author Wang Mincong
 * @date 2020-08-18 08:25:48
 */
@Getter
@Setter
public abstract class BaseEntity<T extends Model<?>> extends Model<T> {

    /**
     * <p>NONE 不设置注解等同于注解的默认注解策略：插入数据时，不给id设值，实际mybatis plus 会设置一个随机整数
     * <p>AUTO 设置自增策略：首先，数据库中的主键字段一定要设置自增策略，否则就会报错。
     *     并且自增基于 SHOW CREATE TABLE `xxx` 命令查询结果中的 AUTO_INCREMENT
     * <p>INPUT 手动指定：无论你是否有为实体的id字段设置，mybatis plus 就将该值作为插入语句中id字段的值，如果没有设置值 - null，相当于采用数据库字段的默认策略
     * <p>ASSIGN_ID：当插入对象ID 为空，才自动填充，主键类型为number或string，默认的实现是雪花算法
     * <p>ASSIGN_UUID：当插入对象ID 为空，才自动填充，主键类型为String，UUID
     */
    @TableId(type = IdType.INPUT)
    private Long id;

    /**
     * 乐观锁
     *
     * 初始值同样需要在自定义的 {@link MyMetaObjectHandler} 中处理，重点说一下update属性的作用，通过特殊表达式来指定更新的值
     */
    @Version
    @TableField(fill = FieldFill.INSERT, update="%s+1")
    private Long version;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /** 更新时间 */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime gmtModified;

    /** 逻辑删除：非特例，不推荐局部配置注解的value和delval属性，不推荐特例 */
    @TableLogic
    private Boolean deleted;

}
