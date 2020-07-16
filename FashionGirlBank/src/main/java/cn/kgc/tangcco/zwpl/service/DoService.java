package cn.kgc.tangcco.zwpl.service;

import java.sql.SQLException;
import java.util.Map;

import cn.kgc.tangcco.zwpl.pojo.AdminUser;
import cn.kgc.tangcco.zwpl.pojo.Orders;

public interface DoService {
	//后台登陆方法
	Map<String, Object> login(AdminUser adminUser) throws SQLException;
	/**
	 *查询订单 
	 */
	public Map<String, Object> queryAllOrders(Map<String, Object> map);
	/**
	 *修改订单状态
	 */
	public Map<String, Object> modifyState(Orders orders);
	/**
	 *查看订单详情 
	 *@param order_id 订单编号
	 *@return 
	 */
	public Map<String, Object> viewOrderDetails(String order_id);	
}
