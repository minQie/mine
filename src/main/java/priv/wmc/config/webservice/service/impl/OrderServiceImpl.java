package priv.wmc.config.webservice.service.impl;

import org.springframework.stereotype.Service;
import priv.wmc.config.webservice.service.OrderService;

import javax.jws.WebService;

/**
 * 定义webservice的service接口调用实现
 *
 * @author 王敏聪
 * @date 2020-03-27 10:13:08
 */
@Service
@WebService(
    serviceName = "orderService",
    // 下面的这个规则一定是“倒写的接口定义类所在的包名”，并且最后面的“/”不能少
    targetNamespace = "http://service.webservice.config.wmc.priv/",
    endpointInterface = "priv.wmc.config.webservice.service.OrderService"
)
public class OrderServiceImpl implements OrderService {

    @Override
    public String orderAdd(String orderCode) {
        return "[code: " + orderCode + "] - 添加成功";
    }

    @Override
    public String orderDetail(String orderCode) {
        return "[code: " + orderCode + "] - 50元";
    }

}
