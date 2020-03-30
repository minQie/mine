package priv.wmc.config.webservice;

import lombok.RequiredArgsConstructor;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import priv.wmc.config.webservice.service.OrderService;

import javax.annotation.Resource;
import javax.xml.ws.Endpoint;

/**
 * webservice核心配置
 *
 * @author 王敏聪
 * @date 2020-03-27 10:19:38
 */
@Configuration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class WebServiceConfig {

    private final OrderService orderService;

    @Resource(name = Bus.DEFAULT_BUS_ID)
    private SpringBus bus;

    /**
     * 1、核心的webservice请求处理器，名称不能为dispatcherServlet
     * 2、访问这里定义的父级url（http://localhost:8080/websocket），会展示出所有可用的service
     * 3、这里定义的路径一定要有后面的通配符，否则访问webservice定义的接口如“/webservice/orderService/orderAdd”不交由核心处理器处理 - 凉凉
     */
    @Bean
    public ServletRegistrationBean cxfServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/webservice/*", "/ws/*");
    }

    /**
     * 将webservice的server实际注册进来
     */
    @Bean
    public Endpoint sweptPayEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, orderService);
        endpoint.publish("/orderService");
        return endpoint;
    }

}
