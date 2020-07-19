package priv.wmc.modules.log;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 1、为输每一个请求添加一个拦截处理的日志打印
 * 2、配合修改日志格式使用，除上述的日志，日志输出都带上用户信息
 *
 * @author 王敏聪
 * @date 2020-03-19 10:58:37
 * @copyright 码农小胖哥
 */
@Slf4j
@Component
public class MdcFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            // 这个Filter的核心就是这个：实际打印日可以定义日志的格式化，这样取"%X{user}"
            MDC.put("user", request.getRemoteUser());
            // 请求的查询参数
            String query = request.getQueryString() != null ? "?" + request.getQueryString() : "";
            // 请求方式：POST需要额外将请求体打印出来
            if (request.getMethod().equals(HttpMethod.POST.name())) {
                // 自定义这个类的目的是为了能够读取到请求体中的内容
                request = new MultiReadHttpServletRequest(request);
                log.info("IP:{}, Method:{}, URI:{} Body:{}", request.getRemoteAddr(), request.getMethod(), request.getRequestURI() + query, ((MultiReadHttpServletRequest) request).getRequestBody().replaceAll("\n", ""));
            } else {
                log.info("IP:{}, Method:{}, URI:{}", request.getRemoteAddr(), request.getMethod(), request.getRequestURI() + query);
            }
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

    @Getter
    class MultiReadHttpServletRequest extends HttpServletRequestWrapper {
        // 缓存 RequestBody
        private String requestBody;

        // 读取请求体
        MultiReadHttpServletRequest(HttpServletRequest request) {
            super(request);
            requestBody = "";
            try {
                StringBuilder stringBuilder = new StringBuilder();
                InputStream inputStream = request.getInputStream();
                byte[] bs = new byte[1024];
                int len;
                while ((len = inputStream.read(bs)) != -1) {
                    stringBuilder.append(new String(bs, 0, len));
                }
                requestBody = stringBuilder.toString();
            } catch (IOException e) {
                // SonarLint 不推荐控制台输出：会输出敏感信息，推荐使用下面的方式
                log.error("context", e);
            }
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody.getBytes());

            return new ServletInputStream() {
                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }

                @Override
                public boolean isFinished() {
                    return byteArrayInputStream.available() == 0;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener readListener) {
                    // DO nothing
                }
            };
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(this.getInputStream()));
        }

    }
}
