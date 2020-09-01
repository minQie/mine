package priv.wmc.main.web.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import priv.wmc.common.util.JSOUPUtils;

/**
 * 核心Filter，原理如下：
 * <p>
 * 1、封装一个工具类，处理字符串中的js标签、html标签等...（自定义要过滤的标签、自定义过滤规则：是否保留标签中的内容等、自定义使用的String工具类...）
 * 2、封装一个HttpServletRequestWrapper，复写getParameter等方法，返回参数值前调用工具类的处理方法（框架封装的最终还是通过servlet的基础方法）
 * 3、自定义一个Filter，针对doFilter方法，针对doFilter方法中的chain.doFilter(request, response);
 * 语句前后，根据参数进行自定义的逻辑，决定要不要进行将request替换为封装的request
 * （自定义过滤规则：可以由参数决定规则、自定义要进行过滤请求路径：正则能够表现出任何规则字符串）
 * <p>
 * ps：整条线还可以决定很多过滤的细节，比如将自定义参数传送到封装的request来决定是否过滤提交的富文本请求（富文本实现一般都是将html标签作为参数）
 *
 * @author 王敏聪
 * @date 2019年11月19日09:48:00
 */
@Slf4j
@Order(2)
@WebFilter(filterName = "xssFilter", urlPatterns = "/**")
public class XssFilter implements Filter {

    /** 是否过滤富文本内容 */
    private static final boolean IS_INCLUDE_RICH_TEXT = true;

    /**
     * 存储不需要过滤的请求，除此之外都需要过滤
     */
    private List<String> excludes = Collections.singletonList("");

    @Override
    public void init(FilterConfig filterConfig) {
        // do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest req = request;
        if (handleExcludeUrl(((HttpServletRequest)request).getServletPath())) {
            req = new XssHttpServletRequestWrapper((HttpServletRequest) request, IS_INCLUDE_RICH_TEXT);
        }
        chain.doFilter(req, response);
    }

    /**
     * 判断请求路径是否在设置的要排除的配置中（即是否不需要过滤）
     */
    private boolean handleExcludeUrl(String servletPath) {
        for (String pattern : excludes) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(servletPath);
            if (m.find()) {
                log.debug("请求: [{}]不需要过滤", servletPath);
                return true;
            }
        }
        log.debug("请求: [{}]需要过滤", servletPath);
        return false;
    }

    /**
     * Springboot提供的Wrapper类 - 包装HttpServletRequest，对其获取参数的方法进行额外处理
     * <p>
     * getParameterNames、getParameterValues、getParameterMap根据需要决定是否覆盖（目前未覆盖）
     */
    static class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

        /**
         * 原始的http请求对象
         */
        @Getter
        private final HttpServletRequest orgRequest;

        private final boolean isIncludeRichText;

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
            name = JSOUPUtils.clean(name);
            String value = super.getParameter(name);
            if (StringUtils.isNotBlank(value)) {
                value = JSOUPUtils.clean(value);
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
                    arr[i] = JSOUPUtils.clean(arr[i]);
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
            name = JSOUPUtils.clean(name);
            String value = super.getHeader(name);
            if (StringUtils.isNotBlank(value)) {
                value = JSOUPUtils.clean(value);
            }
            return value;
        }

    }
}
