package cn.kgc.tangcco.zwpl.service;

import java.sql.SQLException;
import java.util.Map;
import cn.kgc.tangcco.zwpl.pojo.AlipayOrder;
import cn.kgc.tangcco.zwpl.pojo.Orders;

public interface AlipayService {
	/**
	 * 添加交易订单信息
	 * @param alipayOrder
	 * @return
	 * @throws SQLException
	 */
	Map<String,Object> addAlipayOrder(AlipayOrder alipayOrder) throws SQLException;
	/**
	 * 修改购物车的商品状态
	 * @param alipayOrder
	 * @return
	 * @throws SQLException
	 */
	int updateOrderStateByOrderId(String out_trade_no) throws SQLException;
	/**
	 *  * 修改订单地址。电话和收货人
	 */
	int updateOrderNameAndPhoneAndAddressByOrderId(Orders order) throws SQLException;
}
