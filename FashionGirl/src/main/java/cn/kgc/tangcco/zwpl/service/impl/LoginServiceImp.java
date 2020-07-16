package cn.kgc.tangcco.zwpl.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import cn.kgc.tangcco.zwpl.common.jdbc.BaseDBUtils;
import cn.kgc.tangcco.zwpl.common.jdbc.PageRang;
import cn.kgc.tangcco.zwpl.common.spring.ClassPathXmlApplicationContext;
import cn.kgc.tangcco.zwpl.dao.LoginDao;
import cn.kgc.tangcco.zwpl.pojo.CustomerFeedBack;
import cn.kgc.tangcco.zwpl.pojo.DeliveryInformation;
import cn.kgc.tangcco.zwpl.pojo.Orderitem;
import cn.kgc.tangcco.zwpl.pojo.Orders;
import cn.kgc.tangcco.zwpl.pojo.Products;
import cn.kgc.tangcco.zwpl.pojo.ShoppingCart;
import cn.kgc.tangcco.zwpl.pojo.User;
import cn.kgc.tangcco.zwpl.pojo.WishList;
import cn.kgc.tangcco.zwpl.service.LoginService;

public class LoginServiceImp implements LoginService {

	static LoginDao loginDao;
	static {
		try {
			ClassPathXmlApplicationContext cpx = new ClassPathXmlApplicationContext(
					"spring/ApplicationContext-dao.xml");
			loginDao = (LoginDao) cpx.getBean(LoginDao.class.getSimpleName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Object> loginByAccountAndPassword(User user) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		User queryUser = loginDao.loginByAccountAndPassword(user);
		if (queryUser != null) {
			map.put("status", "success");
			map.put("queryUser", queryUser);
		}
		return map;
	}

	@Override
	public Map<String, Object> regist(User user) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		try {
			BaseDBUtils.startTransaction();
			int i = loginDao.regist(user);
			if (i > 0) {
				BaseDBUtils.commitAndClose();
				map.put("status", "success");
			}
			return map;
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

	@Override
	public Long checkAccount(User user) throws SQLException {
		long count = 0;
		try {
			count = loginDao.checkAccount(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 根据用户id查询用户id，修改用户信息
	 */
	@Override
	public Map<String, Object> queryUserById(int id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		User user;
		try {
			user = loginDao.queryUserById(id);
			if (user != null) {
				map.put("status", "success");
				map.put("user", user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 修改用户信息
	 */
	@Override
	public Map<String, Object> updateUser(User user) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		try {
			BaseDBUtils.startTransaction();
			int status = loginDao.updateUser(user);
			if (status > 0) {
				BaseDBUtils.commitAndClose();
				//更新完用户在查询一遍返给前端，防止人脸登陆后更新完用户信息，不能技术存到sessionStoreage中
				User queryUser = loginDao.queryUserById(user.getId());
				if(queryUser!=null) {
					map.put("status", "success");
					map.put("user", queryUser);
				}
				
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
	

	@Override
	public Map<String, Object> queryAllProducts(Map<String, Object> map) {
		Map<String, Object> info = new HashMap<String, Object>();
		try {
			List<Products> list = loginDao.selectAllProducts(map);
			info.put("code", 0);
			info.put("msg", "");
			if (list != null && list.size() != 0) {
				info.put("count", list.size());
				info.put("data", list);
			} else {
				info.put("count", 0);
				info.put("data", new ArrayList());
			}
			BaseDBUtils.closeAll(null, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;
	}

	@Override
	public Map<String, Object> addFaceId(String faceid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		try {
			BaseDBUtils.startTransaction();
			int i = loginDao.addFaceId(faceid);
			if (i > 0) {
				BaseDBUtils.commitAndClose();
				User user = loginDao.queryFaceId(faceid);
				System.out.println(user);
				map.put("status", "success");
				map.put("user", user);
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
	 * 根据人脸id查询用户id，修改用户信息
	 */
	@Override
	public Map<String, Object> queryUserByFaceId(String faceId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		User user;
		try {
			user = loginDao.queryUserByFaceId(faceId);
			if (user != null) {
				map.put("status", "success");
				map.put("user", user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 查询商品详情
	 */
	@Override
	public Map<String, Object> queryProductsByPid(int pid) {
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("status", "failed");
		try {
			Products products = loginDao.selectProductsByPid(pid);
			if (products != null) {
				info.put("status", "success");
				info.put("products", products);
			}
			// 关闭事务
			BaseDBUtils.closeAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * 根据用户id查询该用户的所有与订单
	 */
	@Override
	public Map<String, Object> queryOrdersByUser_id(int user_id) {
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("code", 0);
		info.put("msg", "");
		info.put("count", 0);
		info.put("data", new ArrayList());
		try {
			List<Orders> list = loginDao.selectOrdersByUser_id(user_id);
			if (list != null && list.size() > 0) {
				info.put("count", list.size());
				info.put("data", list);
			}
			BaseDBUtils.closeAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * 根据订单id查询所有的订单项
	 */
	@Override
	public Map<String, Object> queryAllOrderitemByOrder_id(String order_id) {
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("code", 0);
		info.put("msg", "");
		info.put("count", 0);
		info.put("data", new ArrayList());
		try {
			List<Orderitem> list = loginDao.selectAllOrderitemByOrder_id(order_id);
			if (list != null && list.size() > 0) {
				info.put("count", list.size());
				info.put("data", list);
			}
			BaseDBUtils.closeAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * 查询商品简略信息
	 */
	public Map<String, Object> queryBriefInformationOfGoods(Map<String, Object> map) {
		Map<String, Object> productMaps = new LinkedHashMap<String, Object>();
		productMaps.put("code", 0);
		productMaps.put("msg", "");
		productMaps.put("status", "failed");
		try {
			// 存储当前页码号和每页记录数
			PageRang pr = (PageRang) map.get("pr");
			// 获取总记录数
			long rowCount = loginDao.selectCountOfProducts(map);
			List<Products> productList = loginDao.selectBriefInformationOfGoods(map);
			// 总页码号
			int pageCount = 1;
			if (productList != null) {
				productMaps.put("status", "success");
				productMaps.put("count", rowCount);
				productMaps.put("data", productList);
				// 每页纪录数
				int pageSize = ((PageRang) map.get("pr")).getPageSize();
				// 总页面号
				pageCount = (int) ((rowCount % pageSize != 0) ? Math.ceil((rowCount + 0.0) / pageSize)
						: (rowCount / pageSize));
				// 存储总页面数
				productMaps.put("pageCount", pageCount);
				// 保护性代码保护传小于1的页面和大于最大页面的值
				// int pageNumber = pr.getPageNumber();
				productMaps.put("pr", pr);
			} else {
				productMaps.put("count", 0);
				productMaps.put("pageCount", pageCount);
				productMaps.put("data", new ArrayList());
			}
			BaseDBUtils.closeAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productMaps;
	}

	@Override
	public Map<String, Object> addCustomerFeedback(CustomerFeedBack customerfeedback) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		try {
			BaseDBUtils.startTransaction();
			int acf = loginDao.addCustomerFeedback(customerfeedback);
			if (acf > 0) {
				map.put("data", customerfeedback);
				map.put("status", "success");
				BaseDBUtils.commitAndClose();
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
		return null;
	}

	/**
	 * 查询心愿单
	 */
	public Map<String, Object> queryWishList() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		try {
			List<Products> wishListList = loginDao.selectWishLists();
			if (wishListList != null && wishListList.size() > 0) {
				map.put("status", "success");
				map.put("wishListList", wishListList);
			}
			BaseDBUtils.closeAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 删除心愿单中的商品
	 */
	public Map<String, Object> removeWishList(int wid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		try {
			BaseDBUtils.startTransaction();
			int i = loginDao.deletWishList(wid);
			if (i > 0) {
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
	 * 将商品添加到心愿单
	 * 
	 * @param wishList心愿单的编号
	 */
	public Map<String, Object> addWishList(WishList wishList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		try {
			BaseDBUtils.startTransaction();
			int a = loginDao.insertWishList(wishList);
			if (a > 0) {
				map.put("status", "success");
			}
			BaseDBUtils.commitAndClose();
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
	 * 查询购物车
	 */
	public Map<String, Object> queryShoppingCarts(int user_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		try {
			List<ShoppingCart> shoppingCartList = loginDao.selectShoppingCarts(user_id);
			if (shoppingCartList != null && shoppingCartList.size() > 0) {
				map.put("status", "success");
				map.put("shoppingCartList", shoppingCartList);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 移除购物车中的商品
	 */
	public Map<String, Object> removeShoppingCart(int sid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		try {
			int d = loginDao.delectShoppingCart(sid);
			if (d > 0) {
				map.put("status", "success");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 添加购物车
	 */
	public Map<String, Object> addShoopingCart(ShoppingCart shoppingCart) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		try {
			int a = loginDao.insertShoopingCart(shoppingCart);
			if (a > 0) {
				map.put("status", "success");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 修改购物车商品数量
	 */
	@Override
	public Map<String, Object> modifyShoppingSnumBySid(ShoppingCart shoppingCart) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		try {
			BaseDBUtils.startTransaction();
			int u = loginDao.updateShoppingSnumBySid(shoppingCart);
			if (u > 0) {
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
	 * 查询购物车选中购物车的商品，生成订单，跳到支付界面进行支付
	 */
	@Override
	public Map<String, Object> optionProductsAddOrderAndCheckOut(Orders orders) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		try {
			BaseDBUtils.startTransaction();
			// 增加订单
			int i = loginDao.optionProductsAddOrderAndCheckOut(orders);
			if (i > 0) {
				BaseDBUtils.commitAndClose();
				System.out.println("订单添加成功！");
				map.put("status", "success");
				// 查询出来
				Orders order = loginDao.selectOrderByOrderId(orders.getOrder_id());
				map.put("data", order);
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

	@Override
	public Map<String, Object> AddOrdersItemAndCheckOut(String order_id, String ordersItem) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		try {
			BaseDBUtils.startTransaction();
			// 增加订单
			// 增加订单项的表 数据类型6X68886X1,8X13899X1,
			String[] split = StringUtils.split(ordersItem, ",");
			for (int i = 0; i < split.length; i++) {
				String string = split[i];
				String[] split2 = StringUtils.split(string, "X");
				Orderitem orderItemObject = new Orderitem(order_id, Integer.parseInt(split2[0]),
						new BigDecimal(split2[1]), Integer.parseInt(split2[2]));
				int iii = loginDao.addOrderItemByOrderId(orderItemObject);
				if (iii > 0) {
					BaseDBUtils.commitAndClose();
					System.out.println("订单添加成功！");
					map.put("status", "success");
				}
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

	@Override
	public Map<String, Object> addDeliveryInformation(DeliveryInformation deliveryinformation) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		try {
			BaseDBUtils.startTransaction();
			int adf = loginDao.addDeliveryInformation(deliveryinformation);
			if (adf > 0) {
				map.put("data", deliveryinformation);
				map.put("status", "success");
				BaseDBUtils.commitAndClose();
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

	@Override
	public Map<String, Object> selectDeliveryInformation(int user_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		try {
			List<DeliveryInformation> list = loginDao.selectDeliveryInformation(user_id);
			if (list != null && list.size() > 0) {
				map.put("data", list);
				map.put("status", "success");
				BaseDBUtils.closeAll();
				return map;
			}
		} catch (Exception e) {
			try {
				BaseDBUtils.closeAll();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> deleteDeliveryInformation(int id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		try {
			BaseDBUtils.startTransaction();
			int adf = loginDao.deleteDeliveryInformation(id);
			if (adf > 0) {
				map.put("status", "success");
				BaseDBUtils.commitAndClose();
				return map;
			}
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

	@Override
	public Map<String, Object> updateDeliveryInformation(DeliveryInformation deliveryinformation) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		try {
			BaseDBUtils.startTransaction();
			int udi = loginDao.updateDeliveryInformation(deliveryinformation);
			if (udi > 0) {
				map.put("status", "success");
				BaseDBUtils.commitAndClose();
			}
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

	@Override
	public Map<String, Object> selectDeliveryInformationByid(int id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "failed");
		try {
			BaseDBUtils.startTransaction();
			List<DeliveryInformation> list = loginDao.selectDeliveryInformationByid(id);
			if (list != null && list.size() > 0) {
				map.put("data", list);
				map.put("status", "success");
				BaseDBUtils.commitAndClose();
				return map;
			}
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
	 * 查询主页面商品简略信息 (index01-04)通用的方法（12条数据）
	 */
	public Map<String, Object> selectQueryInformation(int cid) {
		Map<String, Object> productMaps = new LinkedHashMap<String, Object>();
		productMaps.put("code", 0);
		productMaps.put("msg", "");
		productMaps.put("status", "failed");
		try {
			List<Products> productList = loginDao.selectQueryInformation(cid);
			if (productList != null) {
				productMaps.put("status", "success");
				productMaps.put("data", productList);

			} else {
				productMaps.put("count", 0);

				productMaps.put("data", new ArrayList());
			}
			BaseDBUtils.closeAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productMaps;
	}
	/**
	 * //标题栏购物车商品
	 */
	@Override
	public Map<String, Object> queryUnpaidShoppingCartByUserid(int userid) {
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("code", 0);
		info.put("msg", "空空如也");
		info.put("count", 0);
		info.put("data", new ArrayList<Products>());
		try {
			List<Products> list = loginDao.selectUnpaidShoppingCartByUserid(userid);
			if (list != null && list.size() > 0) {
				info.put("msg", "");
				info.put("count", list.size());
				info.put("data", list);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;
	}
	/**
	 * echart生成图表
	 * @param map
	 * @return
	 */
	@Override
	public Map<String, Object> queryDataByUser_id(Map<String, Object> map) {
		Map<String, Object> info = new HashMap<String, Object>();
		try {
			info = loginDao.selectDataByUser_id(map);
			if (!info.containsKey("m1")) {
				info.put("m1", 0);
			} 
			if (!info.containsKey("m2")) {
				info.put("m2", 0);
			}
			if (!info.containsKey("m3")) {
				info.put("m3", 0);
			}
			if (!info.containsKey("m4")) {
				info.put("m4", 0);
			}
			if (!info.containsKey("m5")) {
				info.put("m5", 0);
			}
			if (!info.containsKey("m6")) {
				info.put("m6", 0);
			}
			if (!info.containsKey("m7")) {
				info.put("m7", 0);
			}
			if (!info.containsKey("m8")) {
				info.put("m8", 0);
			}
			if (!info.containsKey("m9")) {
				info.put("m9", 0);
			}
			if (!info.containsKey("m10")) {
				info.put("m10", 0);
			} 
			if (!info.containsKey("m11")) {
				info.put("m11", 0);
			} 
			if (!info.containsKey("m12")) {
				info.put("m12", 0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;
	}

	@Override
	public Map<String, Object> updateOrderStateByOrder_id3(String order_id) {
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("status", "failed");
		try {
			BaseDBUtils.startTransaction();
			int i = loginDao.updateOrderStateByOrder_id3(order_id);
			if(i>0) {
				BaseDBUtils.commitAndClose();
				info.put("status", "success");
				return info;
			}
			BaseDBUtils.closeAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;
	}
}
