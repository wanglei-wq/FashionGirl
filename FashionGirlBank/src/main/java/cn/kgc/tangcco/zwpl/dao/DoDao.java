package cn.kgc.tangcco.zwpl.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import cn.kgc.tangcco.zwpl.pojo.AdminUser;
import cn.kgc.tangcco.zwpl.pojo.Orders;

public interface DoDao {
	/**
	 * 登陆功能
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	AdminUser login(AdminUser adminUser) throws SQLException;

	/**
	 * 查询所有订单
	 * 
	 * @throws SQLException
	 */
	public List SelectAllOrders(Map<String, Object> map) throws SQLException;

	/**
	 * 修改订单状态
	 * 
	 * @throws SQLException
	 */
	public int updateState(Orders orders) throws SQLException;

	/**
	 * 查看订单详情
	 * 
	 * @param order_id 订单编号
	 * @throws SQLException
	 */
	public Orders SelectOrderDetails(String order_id) throws SQLException;
}
