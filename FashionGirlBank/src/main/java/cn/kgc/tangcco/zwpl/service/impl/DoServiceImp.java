package cn.kgc.tangcco.zwpl.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.kgc.tangcco.zwpl.common.jdbc.BaseDBUtils;
import cn.kgc.tangcco.zwpl.common.spring.ClassPathXmlApplicationContext;
import cn.kgc.tangcco.zwpl.dao.DoDao;
import cn.kgc.tangcco.zwpl.pojo.AdminUser;
import cn.kgc.tangcco.zwpl.pojo.Orders;
import cn.kgc.tangcco.zwpl.service.DoService;

public class DoServiceImp implements DoService {
	static DoDao doDao;
	static {
		try {
			ClassPathXmlApplicationContext cpx = new ClassPathXmlApplicationContext("spring/ApplicationContext-dao.xml");
			doDao = (DoDao) cpx.getBean(DoDao.class.getSimpleName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public Map<String, Object> login(AdminUser adminUser) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		AdminUser queryAdminUser = doDao.login(adminUser);
		if (queryAdminUser != null) {
			map.put("status", "success");
			map.put("queryUser", queryAdminUser);
		}
		return map;
	}
	/**
	 *查询订单 
	 */
	public Map<String, Object> queryAllOrders(Map<String, Object> map){
		Map<String, Object> allMap=new HashMap<String, Object>();
		allMap.put("status","failed");
		try {
			List<Orders> ordersList=doDao.SelectAllOrders(map);
			if (ordersList.size()>0&&ordersList!=null) {
				allMap.put("status", "success");
				allMap.put("ordersList", ordersList);
			}
			BaseDBUtils.closeAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allMap;
	}
	/**
	 *修改订单状态
	 */
	public Map<String, Object> modifyState(Orders orders) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("status","failed");
		try {
			BaseDBUtils.startTransaction();
			int i=doDao.updateState(orders);
			if (i>0) {
				map.put("status", "success");
			}
			BaseDBUtils.commitAndClose();
		} catch (SQLException e) {
			try {
				BaseDBUtils.RollbackAndclose();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return map;
	}
	/**
	 *查看订单详情 
	 *@param order_id 订单编号
	 *@return 
	 */
	public Map<String, Object> viewOrderDetails(String order_id) {
		Map<String, Object> viewMap=new HashMap<String, Object>();
		viewMap.put("status","failed");
		try {
			Orders orders=doDao.SelectOrderDetails(order_id);
			if (order_id!=null) {
				viewMap.put("status","success");
				viewMap.put("orders", orders);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return viewMap;
	}
}
