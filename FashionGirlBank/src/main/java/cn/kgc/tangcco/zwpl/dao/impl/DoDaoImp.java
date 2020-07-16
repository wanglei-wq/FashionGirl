package cn.kgc.tangcco.zwpl.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.lang3.StringUtils;

import cn.kgc.tangcco.zwpl.common.jdbc.BaseDBUtils;
import cn.kgc.tangcco.zwpl.dao.DoDao;
import cn.kgc.tangcco.zwpl.pojo.AdminUser;
import cn.kgc.tangcco.zwpl.pojo.Orders;

public class DoDaoImp implements DoDao {
	QueryRunner qrds = new QueryRunner(BaseDBUtils.getDataSource());
	QueryRunner qr = new QueryRunner();

	@Override
	public AdminUser login(AdminUser adminUser) throws SQLException {
		StringBuilder sql = new StringBuilder(" select * from adminuser where 1=1  ");
		sql.append(" and username = ? ");
		sql.append(" and password = ? ");
		Object[] params = { adminUser.getUsername(), adminUser.getPassword() };
		return qrds.query(sql.toString(), new BeanHandler<AdminUser>(AdminUser.class), params);
	}

	/**
	 * 查询所有订单
	 * 
	 * @throws SQLException
	 */
	public List SelectAllOrders(Map<String, Object> map) throws SQLException {
		StringBuilder sql = new StringBuilder(" SELECT * FROM orders ");
		sql.append(" WHERE 1=1 ");
		List list = new ArrayList();
		List<Orders> ordersList = new ArrayList<Orders>();
		if (map != null && map.size() > 0) {
			if (!StringUtils.isEmpty(map.get("information").toString())) {
				sql.append(" and order_id like ? ");
				sql.append(" OR user_name LIKE ? ");
				sql.append(" OR user_phone LIKE ? ");
				sql.append(" OR address LIKE ? ");
				list.add("%" + map.get("information").toString() + "%");
				list.add("%" + map.get("information").toString() + "%");
				list.add("%" + map.get("information").toString() + "%");
				list.add("%" + map.get("information").toString() + "%");
			}
		}
		sql.append(" ORDER BY ordertime desc ");
		sql.append(" LIMIT 10 ");
		System.out.println(sql.toString());
		Connection conn = BaseDBUtils.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		pst = BaseDBUtils.getPreparedStatement(conn, sql.toString());
		rs = BaseDBUtils.executeQuery(pst, list.toArray());
		if (rs != null) {
			while (rs.next()) {
				ordersList.add(new Orders(rs.getInt("oid"), rs.getString("order_id"), rs.getString("user_name"),
						rs.getString("user_phone"), rs.getDouble("money"), rs.getString("address"),
						rs.getDate("ordertime"), rs.getInt("state"), rs.getInt("user_id")));
			}
		}
		return ordersList;
	}

	/**
	 * 修改订单状态
	 * 
	 * @throws SQLException
	 */
	public int updateState(Orders orders) throws SQLException {
		StringBuilder sql = null;
		if (orders.getState() == 1) {
			sql = new StringBuilder(" UPDATE orders SET state=2 ");
			sql.append("  WHERE 1=1 ");
			sql.append(" AND order_id=? ");

		} else if (orders.getState() == 3) {
			sql = new StringBuilder(" UPDATE orders SET state=4 ");
			sql.append("  WHERE 1=1 ");
			sql.append(" AND order_id=? ");

		}
		System.out.println(sql.toString());
		Object[] params = { orders.getOrder_id() };
		return qr.update(BaseDBUtils.getConnection(), sql.toString(), params);
	}

	/**
	 * 查看订单详情
	 * 
	 * @param order_id 订单编号
	 * @throws SQLException
	 */
	public Orders SelectOrderDetails(String order_id) throws SQLException {
		StringBuilder sql = new StringBuilder(
				" SELECT order_id,user_name,user_phone,money,address,ordertime,state FROM orders ");
		sql.append("  WHERE 1=1  ");
		sql.append(" AND order_id=? ");
		Connection conn = BaseDBUtils.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		pst = BaseDBUtils.getPreparedStatement(conn, sql.toString());
		rs = BaseDBUtils.executeQuery(pst, order_id);
		if (rs != null) {
			while (rs.next()) {
				return new Orders(rs.getString("order_id"), rs.getString("user_name"), rs.getString("user_phone"),
						rs.getDouble("money"), rs.getString("address"), rs.getDate("ordertime"), rs.getInt("state"));
			}
		}
		return null;
	}
}
