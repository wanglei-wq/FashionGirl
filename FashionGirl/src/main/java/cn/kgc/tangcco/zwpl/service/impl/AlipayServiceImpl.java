package cn.kgc.tangcco.zwpl.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import cn.kgc.tangcco.zwpl.common.jdbc.BaseDBUtils;
import cn.kgc.tangcco.zwpl.common.spring.ClassPathXmlApplicationContext;
import cn.kgc.tangcco.zwpl.dao.AlipayDao;
import cn.kgc.tangcco.zwpl.pojo.AlipayOrder;
import cn.kgc.tangcco.zwpl.pojo.Orders;
import cn.kgc.tangcco.zwpl.pojo.UserALP;
import cn.kgc.tangcco.zwpl.service.AlipayService;

public class AlipayServiceImpl implements AlipayService{
	private static ClassPathXmlApplicationContext ca=new ClassPathXmlApplicationContext("spring/ApplicationContext-dao.xml");
	private static AlipayDao alipayDao=null;
	static {
		try {
			alipayDao=(AlipayDao) ca.getBean("AlipayDao");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public Map<String, Object> addAlipayOrder(AlipayOrder alipayOrder){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		try {
			BaseDBUtils.startTransaction();
			int i = alipayDao.addAlipayOrder(alipayOrder);
			if(i>0) {
				BaseDBUtils.commitAndClose();
				map.put("status", "success");
				return map;
			}
		} catch (SQLException e) {
			try {
				BaseDBUtils.RollbackAndclose();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return map;
	}
	/**
	 *  * 修改订单地址。电话和收货人
	 */
	@Override
	public int updateOrderNameAndPhoneAndAddressByOrderId(Orders order) throws SQLException {
		try {
			BaseDBUtils.startTransaction();
			int i = alipayDao.updateOrderNameAndPhoneAndAddressByOrderId(order);
			if(i>0) {
				BaseDBUtils.commitAndClose();
				return i;
			}
		} catch (SQLException e) {
			try {
				BaseDBUtils.RollbackAndclose();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 修改订单的状态
	 * @param alipayOrder
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int updateOrderStateByOrderId(String out_trade_no) throws SQLException {
		try {
			BaseDBUtils.startTransaction();
			int i = alipayDao.updateOrderStateByOrderId(out_trade_no);
			if(i>0) {
				BaseDBUtils.commitAndClose();
				return i;
			}
		} catch (SQLException e) {
			try {
				BaseDBUtils.RollbackAndclose();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return 0;
	}
}
