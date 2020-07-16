package cn.kgc.tangcco.zwpl.dao.impl;

import java.sql.SQLException;
import org.apache.commons.dbutils.QueryRunner;

import cn.kgc.tangcco.zwpl.common.jdbc.BaseDBUtils;
import cn.kgc.tangcco.zwpl.dao.AlipayDao;
import cn.kgc.tangcco.zwpl.pojo.AlipayOrder;
import cn.kgc.tangcco.zwpl.pojo.Orders;
import cn.kgc.tangcco.zwpl.pojo.User;

public class AlipayDaoImpl implements AlipayDao {
	private QueryRunner qr = new QueryRunner();
	private QueryRunner qrds = new QueryRunner(BaseDBUtils.getDataSource());

	@Override
	public int addAlipayOrder(AlipayOrder alipayOrder) throws SQLException {
		StringBuilder sql = new StringBuilder(" insert into alipayorder (out_trade_no,trade_no,total_amount,user_id) ");
		sql.append(" select ?,?,?,? from dual ");
		sql.append(" where not exists ");
		sql.append(" ( select out_trade_no from alipayorder where out_trade_no = ?) ");
		Object[] parmas = { alipayOrder.getOut_trade_no(), alipayOrder.getTrade_no(), alipayOrder.getTotal_amount(),
				alipayOrder.getUser_id(), alipayOrder.getOut_trade_no() };
		return qrds.update(sql.toString(), parmas);
	}

	@Override
	public int updateOrderStateByOrderId(String out_trade_no) throws SQLException {
		StringBuilder sql = new StringBuilder(" UPDATE `orders` SET ");
		sql.append(" `state` = 1 ");
		sql.append(" WHERE 1 = 1 ");
		sql.append(" and `order_id` = ? ");
		System.out.println(sql.toString());
		return qr.update(BaseDBUtils.getConnection(), sql.toString(), out_trade_no);
	}

	@Override
	public int updateOrderNameAndPhoneAndAddressByOrderId(Orders order) throws SQLException {
		StringBuilder sql = new StringBuilder(" UPDATE `orders` SET ");
		sql.append(" `user_name` = ? , ");
		sql.append(" `user_phone` = ? , ");
		sql.append(" `address` = ? ");
		sql.append(" WHERE 1 = 1 ");
		sql.append(" and `order_id` = ? ");
		Object[] params = {order.getUser_name(),order.getUser_phone(),order.getAddress(),order.getOrder_id()};
		return qr.update(BaseDBUtils.getConnection(), sql.toString(), params);
	}
}
