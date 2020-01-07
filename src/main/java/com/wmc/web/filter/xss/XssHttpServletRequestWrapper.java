package com.wmc.web.filter.xss;

import com.wmc.common.util.JSoupUtils;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Springboot提供的Wrapper类 - 包装HttpServletRequest，对其获取参数的方法进行额外处理
 * <p>
 * getParameterNames、getParameterValues、getParameterMap根据需要决定是否覆盖（目前未覆盖）
 *
 * @author 王敏聪
 * @date 2019年11月16日19:40:15
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 原始的http请求对象
     */
    @Getter
    private HttpServletRequest orgRequest;

    private boolean isIncludeRichText;

    XssHttpServletRequestWrapper(HttpServletRequest request, boolean isIncludeRichText) {
        super(request);
        orgRequest = request;
        this.isIncludeRichText = isIncludeRichText;
    }

    /**
     * 覆盖getParameter方法，将参数名和参数值都做xss过滤如果需要获得原始的值，则通过super.getParameterValues(name)来获取
     */
    @Override
    public String getParameter(String name) {
        // name 为content 或者 以WithHtml结尾，说明是富文本？
        boolean isRichTest = ("content".equals(name) || name.endsWith("WithHtml"));
        if (isRichTest && !isIncludeRichText) {
            return super.getParameter(name);
        }
        name = JSoupUtils.clean(name);
        String value = super.getParameter(name);
        if (StringUtils.isNotBlank(value)) {
            value = JSoupUtils.clean(value);
        }
        return value;
    }

    /**
     * 覆盖getParameterValues方法
     *
     * @param name
     * @return
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] arr = super.getParameterValues(name);
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = JSoupUtils.clean(arr[i]);
            }
        }
        return arr;
    }

    /**
     * 覆盖getHeader方法，将参数名和参数值都做xss过滤如果需要获得原始的值，则通过super.getHeaders(name)来获取
     * getHeaderNames 也可能需要覆盖
     */
    @Override
    public String getHeader(String name) {
        name = JSoupUtils.clean(name);
        String value = super.getHeader(name);
        if (StringUtils.isNotBlank(value)) {
            value = JSoupUtils.clean(value);
        }
        return value;
    }

}
