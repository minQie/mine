package priv.wmc.config.webservice.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * 定义webservice服务端的接口
 *
 * @author 王敏聪
 * @date 2020-03-27 10:09:09
 */
@WebService
public interface OrderService {

    /**
     * 加订单
     * @param orderCode 订单编号
     * @return 加订单是否成功
     */
    @WebMethod
    String orderAdd(@WebParam(name = "orderCode") String orderCode);

    /**
     * 获取订单详情
     *
     * @param orderCode 订单编号
     * @return 订单详情
     */
    @WebMethod
    String orderDetail(@WebParam(name = "orderCode") String orderCode);

}
