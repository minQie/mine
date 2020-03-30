package priv.wmc.config.webservice;

import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.Test;

/**
 * @author 王敏聪
 * @date 2020-03-27 16:00:19
 */
@Slf4j
public class OrderServiceTest {

    /**
     * @WebService（类上）
     *      name                名称
     *      targetNamespace     命名空间
     *      serviceName         ？
     *      portName            ？
     *      wsdLocation         ？
     *      endpointInterface   当前修饰类的类完整类名
     * @WebMethod（方法上）
     *      operationName       名称
     *      action              命名空间？
     *      exclude             是否排除（默认“false”）
     * @WebParam（形参上）
     *      name                名称
     *      partName            ？
     *      targetNamespace     命名空间
     *      mode                ？（默认“IN”）
     *      header              ？（默认“false”）
     * @WebResult（方法上）
     *      name                名称（默认“方法名Response”）
     *      partName            ？
     *      targetNamespace     命名空间
     *      header              ？（默认“false”）
     *
     * @RequestWrapper（方法上）
     *
     * @ResponseWrapper（方法上）
     */
    @Test
    public void test() {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        try (Client client = dcf.createClient("http://localhost:8081/webservice/orderService?wsdl")) {
            // 需要密码的情况需要加上用户名和密码
            // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME, PASS_WORD));

            Object[] objects = client.invoke("orderAdd", "110");
            log.info("返回数据：{}", objects[0]);
        } catch (Exception e) {
            log.error("content", e);
        }
    }

}
