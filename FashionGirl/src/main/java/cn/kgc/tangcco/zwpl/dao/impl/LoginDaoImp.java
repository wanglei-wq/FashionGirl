package cn.kgc.tangcco.zwpl.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;
import cn.kgc.tangcco.zwpl.common.jdbc.BaseDBUtils;
import cn.kgc.tangcco.zwpl.common.jdbc.PageRang;
import cn.kgc.tangcco.zwpl.dao.LoginDao;
import cn.kgc.tangcco.zwpl.pojo.CustomerFeedBack;
import cn.kgc.tangcco.zwpl.pojo.DeliveryInformation;
import cn.kgc.tangcco.zwpl.pojo.Orderitem;
import cn.kgc.tangcco.zwpl.pojo.Orders;
import cn.kgc.tangcco.zwpl.pojo.PWVO;
import cn.kgc.tangcco.zwpl.pojo.Products;
import cn.kgc.tangcco.zwpl.pojo.ShoppingCart;
import cn.kgc.tangcco.zwpl.pojo.User;
import cn.kgc.tangcco.zwpl.pojo.WishList;

public class LoginDaoImp implements LoginDao {
	QueryRunner qrds = new QueryRunner(BaseDBUtils.getDataSource());
	QueryRunner qr = new QueryRunner();

	/**
	 * 登陆
	 */
	@Override
	public User loginByAccountAndPassword(User user) throws SQLException {
		StringBuilder sql = new StringBuilder("select * from user where 1=1 ");
		sql.append(" and account = ? ");
		sql.append(" and password = ? ");
		Object[] params = { user.getAccount(), user.getPassword() };
		User queryUser = qrds.query(sql.toString(), new BeanHandler<User>(User.class), params);
		return queryUser;
	}

	/**
	 * 注册
	 */
	@Override
	public int regist(User user) throws SQLException {
		StringBuilder sql = new StringBuilder("insert into user(account,password,nickname,phone)");
		sql.append(" select  ?,?,?,? ");
		sql.append(" where not exists ");
		sql.append(" (select account from user where account=?) ");
		Object[] params = { user.getAccount(), user.getPassword(), user.getNickname(), user.getPhone(),
				user.getAccount() };
		return qr.update(BaseDBUtils.getConnection(), sql.toString(), params);
	}

	/**
	 * ajax异步检查检查账号是否存在
	 */
	@Override
	public Long checkAccount(User user) throws SQLException {
		StringBuilder sql = new StringBuilder(" select count(id) from user where 1=1 ");
		sql.append(" and account = ? ");
		Object[] params = { user.getAccount() };
		return qrds.query(sql.toString(), new ScalarHandler<Long>(1), params);
	}

	/**
	 * 修改用户信息
	 */
	@Override
	public int updateUser(User user) throws SQLException {
		StringBuilder sql = new StringBuilder("UPDATE `user` SET ");
		sql.append(" `nickname` = ?, ");
		sql.append(" `password` = ?, ");
		sql.append(" `account` = ?, ");
		sql.append(" `sex` = ?, ");
		sql.append(" `email` = ?, ");
		sql.append(" `address` = ?, ");
		sql.append(" `phone` = ? ");
		sql.append(" WHERE 1 = 1 ");
		sql.append(" and `id` = ? ");
		System.out.println(sql.toString());
		Object[] params = { user.getNickname(), user.getPassword(), user.getAccount(), user.getSex(), user.getEmail(),
				user.getAddress(), user.getPhone(), user.getId() };
		return qr.update(BaseDBUtils.getConnection(), sql.toString(), params);
	}
	@Override
	public User queryUserById(int id) throws SQLException {
		StringBuilder sql = new StringBuilder("select * from user where 1=1 ");
		sql.append(" and id = ? ");
		User queryUser = qrds.query(sql.toString(), new BeanHandler<User>(User.class), id);
		return queryUser;
	}

	@Override
	public List<Products> selectAllProducts(Map<String, Object> map) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT * FROM products");
		List list = new ArrayList();
		if (map != null && map.size() != 0) {
			// 条件查询
			if (!StringUtils.isEmpty(map.get("cid").toString())) {
				sql.append(" WHERE 1=1 ");
				sql.append(" AND cid = ? ");
				list.add(map.get("cid"));
			}
		}
		Object[] params = list.toArray();
		return qr.query(BaseDBUtils.getConnection(), sql.toString(), new BeanListHandler<Products>(Products.class),
				params);
	}

	@Override
	public int addFaceId(String faceid) throws SQLException {
		StringBuilder sql = new StringBuilder("insert into user (faceid) ");
		sql.append(" select ? ");
		sql.append(" where not exists ");
		sql.append(" (select faceid from user where faceid=?) ");
		Object[] params = { faceid, faceid };
		return qr.update(BaseDBUtils.getConnection(), sql.toString(), params);
	}

	@Override
	public User queryFaceId(String faceid) throws SQLException {
		StringBuilder sql = new StringBuilder("select * from user where 1=1 ");
		sql.append(" and faceid = ? ");
		Object[] params = { faceid };
		return qrds.query(sql.toString(), new BeanHandler<User>(User.class), params);
	}

	@Override
	public User queryUserByFaceId(String faceId) throws SQLException {
		StringBuilder sql = new StringBuilder("select * from user where 1=1 ");
		sql.append(" and faceid = ? ");
		Object[] params = { faceId };
		User queryUser = qrds.query(sql.toString(), new BeanHandler<User>(User.class), params);
		return queryUser;
	}

	/**
	 * 通过user_id找到订单
	 * 
	 * @param user_id
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<Orders> selectOrdersByUser_id(int user_id) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT * FROM orders");
		sql.append(" WHERE 1=1 ");
		sql.append(" AND user_id = ? ");
		return qr.query(BaseDBUtils.getConnection(), sql.toString(), user_id,
				new BeanListHandler<Orders>(Orders.class));
	}

	/**
	 * 通过订单order_id查询所有订单项
	 * 
	 * @param o_oid
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<Orderitem> selectAllOrderitemByOrder_id(String order_id) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT a.*,b.pname");
		sql.append(" FROM orderitem AS a ");
		sql.append(" INNER JOIN ");
		sql.append(" products AS b ");
		sql.append(" WHERE 1=1 ");
		sql.append(" AND order_id=? ");//
		sql.append(" AND a.p_pid=b.pid ");
		return qr.query(BaseDBUtils.getConnection(), sql.toString(), new BeanListHandler<Orderitem>(Orderitem.class),
				order_id);
	}

	/**
	 * 查询商品简略信息
	 * 
	 * @throws SQLException
	 */
	public List<Products> selectBriefInformationOfGoods(Map<String, Object> map) throws SQLException {
		// 存储参数
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder(" SELECT * FROM products ");
		sql.append(" WHERE 1=1 ");
		// sql.append("LIMIT ?,9");
		// if (map!=null&&map.size()>0) {
		// if
		// (!StringUtils.isEmpty(map.get("pageNum").toString())&&Integer.parseInt(map.get("pageNum").toString())!=0)
		// {
		// sql.append(" LIMIT ?,9 ");
		// list.add((int)map.get("pageNum"));
		// }
		// }
		System.out.println(sql.toString());
		// 存储查询结果
		List<Products> productsList = new ArrayList<Products>();
		// 开链接
		Connection conn = BaseDBUtils.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		// 预编译
		pst = BaseDBUtils.getPreparedStatement(conn, sql.toString(), (PageRang) map.get("pr"));
		// 结果集
		rs = BaseDBUtils.executeQuery(pst, list.toArray());
		if (rs != null) {
			while (rs.next()) {
				productsList.add(new Products(rs.getInt("pid"), rs.getString("pname"), rs.getDouble("market_price"),
						rs.getDouble("shop_price"), rs.getString("image"), rs.getString("pdesc")));
			}
		}
		return productsList;
	}

	/**
	 * 获取总记录数
	 * 
	 * @throws SQLException
	 */
	public long selectCountOfProducts(Map<String, Object> map) throws SQLException {
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder(" SELECT COUNT(pid) FROM products ");
		sql.append(" where 1 = 1 ");
		Object[] params = list.toArray();
		long count = 0;
		if (params.length > 0) {
			count = qr.query(BaseDBUtils.getConnection(), sql.toString(), params, new ScalarHandler<Long>(1));
		} else {
			count = qr.query(BaseDBUtils.getConnection(), sql.toString(), new ScalarHandler<Long>(1));
		}
		return count;
	}

	/**
	 * 反馈我们界面
	 * 
	 * @param customerfeedback
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int addCustomerFeedback(CustomerFeedBack customerfeedback) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO customerfeedback(name,phone,mailbox,theme,feedback) ");
		sql.append(" SELECT ?,?,?,?,? ");
		sql.append(" FROM DUAL ");
		Connection conn = BaseDBUtils.getConnection();
		Object[] params = { customerfeedback.getName(), customerfeedback.getPhone(), customerfeedback.getMailbox(),
				customerfeedback.getTheme(), customerfeedback.getFeedback() };
		return qr.update(conn, sql.toString(), params);
	}

	/**
	 * 查询心愿单
	 * 
	 * @throws SQLException
	 */
	public List selectWishLists() throws SQLException {
		StringBuilder sql = new StringBuilder(
				" SELECT w.wid,p.pid,p.pname,p.image,p.shop_price,p.pnum,u.id FROM wishlist w,products p,`user` u WHERE w.pid=p.pid AND w.userid=u.id; ");
		return qr.query(BaseDBUtils.getConnection(), sql.toString(), new BeanListHandler<PWVO>(PWVO.class));
	}

	/**
	 * 删除心愿单中的商品
	 * 
	 * @param wid 心愿单编号
	 * @throws SQLException
	 */
	public int deletWishList(int wid) throws SQLException {
		StringBuilder sql = new StringBuilder(" DELETE FROM wishlist ");
		sql.append(" WHERE 1=1 ");
		sql.append("  AND wid=? ");
		System.out.println(sql.toString());
		return qr.update(BaseDBUtils.getConnection(), sql.toString(), wid);
	}

	/**
	 * 将商品添加到心愿单
	 * 
	 * @param wishList心愿单的编号
	 * @throws SQLException
	 */
	public int insertWishList(WishList wishList) throws SQLException {
		StringBuilder sql = new StringBuilder(" INSERT INTO wishlist(pid,userid) VALUES(?,?) ");
		System.out.println(sql.toString());
		Object[] params = { wishList.getPid(), wishList.getUserid() };
		return qr.update(BaseDBUtils.getConnection(), sql.toString(), params);
	}

	/**
	 * 查询商品详情
	 */
	@Override
	public Products selectProductsByPid(int pid) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT * FROM products");
		sql.append(" WHERE 1=1 ");
		sql.append(" AND pid=? ");
		return qr.query(BaseDBUtils.getConnection(), sql.toString(), pid, new BeanHandler<Products>(Products.class));
	}

	/**
	 * 查询购物车
	 * 
	 * @throws SQLException
	 */
	public List<ShoppingCart> selectShoppingCarts(int user_id) throws SQLException {
		StringBuilder sql = new StringBuilder(
				" SELECT s.sid,p.pid,p.pname,p.image,p.shop_price,s.total,s.snum,u.id FROM shoppingcart s,products p,`user` u WHERE s.pid=p.pid AND s.userid=u.id and s.state = 1 and userid = ? ");
		return qr.query(BaseDBUtils.getConnection(), sql.toString(),new BeanListHandler<ShoppingCart>(ShoppingCart.class),user_id);
	}

	/**
	 * 移除购物车中的商品
	 * 
	 * @throws SQLException
	 */
	public int delectShoppingCart(int sid) throws SQLException {
		StringBuilder sql = new StringBuilder("DELETE FROM shoppingcart ");
		sql.append("WHERE 1=1 ");
		sql.append(" AND sid=? ");
		return qr.update(BaseDBUtils.getConnection(), sql.toString(), sid);
	}

	/**
	 * 添加购物车
	 * 
	 * @throws SQLException
	 */
	public int insertShoopingCart(ShoppingCart shoppingCart) throws SQLException {
		StringBuilder sql = new StringBuilder(" INSERT INTO shoppingcart (pid,userid,snum,total) VALUES (?,?,?,?) ");
		System.out.println(sql.toString());
		Object[] params = { shoppingCart.getPid(), shoppingCart.getUserid(), shoppingCart.getSnum(),
				shoppingCart.getTotal() };
		return qr.update(BaseDBUtils.getConnection(), sql.toString(), params);
	}

	/**
	 * 修改商品数量
	 * 
	 * @throws SQLException
	 */
	public int updateShoppingSnumBySid(ShoppingCart shoppingCart) throws SQLException {
		StringBuilder sql = new StringBuilder("UPDATE `shoppingcart` SET ");
		sql.append(" `snum` = ?, ");
		sql.append(" `total` = ? ");
		sql.append(" WHERE 1 = 1 ");
		sql.append(" and `sid` = ? ");
		System.out.println(sql.toString());
		Object[] params = { shoppingCart.getSnum(), shoppingCart.getTotal(), shoppingCart.getSid() };
		return qr.update(BaseDBUtils.getConnection(), sql.toString(), params);
	}

	/**
	 * 查询购物车选中购物车的商品，生成订单，跳到支付界面进行支付
	 * 
	 * @throws SQLException
	 */
	@Override
	public int optionProductsAddOrderAndCheckOut(Orders orders) throws SQLException {
		StringBuilder sql = new StringBuilder(" INSERT INTO orders (order_id,money,ordertime,state,user_id)");
		sql.append(" select ?,?,?,?,? ");
		sql.append(" where not exists ");
		sql.append(" (select order_id from orders where order_id= ? ) ");
		Object[] params = { orders.getOrder_id(), orders.getMoney(), orders.getOrdertime(), orders.getState(),
				orders.getUser_id(), orders.getOrder_id() };
		return qr.update(BaseDBUtils.getConnection(), sql.toString(), params);
	}

	@Override
	public Orders selectOrderByOrderId(String order_id) throws SQLException {
		StringBuilder sql = new StringBuilder(" SELECT * FROM orders ");
		sql.append(" WHERE 1=1 ");
		sql.append(" AND order_id= ? ");
		return qrds.query(sql.toString(), new BeanHandler<Orders>(Orders.class), order_id);
	}

	@Override
	public int addOrderItemByOrderId(Orderitem orderItemObject) throws SQLException {
		StringBuilder sql = new StringBuilder(
				" INSERT INTO orderitem (count,subtotal,p_pid,order_id) values(?,?,?,?) ");
		Object[] params = { orderItemObject.getCount(), orderItemObject.getSubtotal(), orderItemObject.getP_pid(),
				orderItemObject.getOrder_id() };
		return qr.update(BaseDBUtils.getConnection(), sql.toString(), params);
	}

	// 添加收货信息
	@Override
	public int addDeliveryInformation(DeliveryInformation deliveryinformation) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(" INSERT INTO user_addr(user_id,nickname,phone,address) ");
		sql.append(" SELECT ?,?,?,? ");
		sql.append(" from dual ");
		Connection conn = BaseDBUtils.getConnection();
		Object[] params = { deliveryinformation.getUser_id(), deliveryinformation.getNickname(),
				deliveryinformation.getPhone(), deliveryinformation.getAddress() };
		return qr.update(conn, sql.toString(), params);
	}

	// 根据当前用户ID查找收货信息
	@Override
	public List<DeliveryInformation> selectDeliveryInformation(int user_id) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(" select * from user_addr where 1=1 ");
		sql.append(" and user_id = ? ");
		Connection conn = BaseDBUtils.getConnection();
		return qr.query(conn, sql.toString(), new BeanListHandler<DeliveryInformation>(DeliveryInformation.class),
				user_id);
	}

	@Override
	public int deleteDeliveryInformation(int id) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE from user_addr where 1=1 ");
		sql.append(" and id = ? ");
		Connection conn = BaseDBUtils.getConnection();
		return qr.update(conn, sql.toString(), id);
	}

	@Override
	public int updateDeliveryInformation(DeliveryInformation deliveryinformation) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE user_addr set address=?,nickname=?,phone=? ");
		sql.append(" where id = ? ");
		Connection conn = BaseDBUtils.getConnection();
		Object[] params = { deliveryinformation.getAddress(), deliveryinformation.getNickname(),
				deliveryinformation.getPhone(), deliveryinformation.getId() };
		return qr.update(conn, sql.toString(), params);
	}

	@Override
	public List<DeliveryInformation> selectDeliveryInformationByid(int id) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT * from user_addr where 1=1 ");
		sql.append(" and id = ? ");
		Connection conn = BaseDBUtils.getConnection();
		return qr.query(conn, sql.toString(), new BeanListHandler<DeliveryInformation>(DeliveryInformation.class), id);
	}
	@Override
	public List<Products> selectQueryInformation(int cid) throws SQLException {
		StringBuilder sql = new StringBuilder(" SELECT * FROM products ");
		sql.append(" WHERE 1=1 ");
		sql.append(" AND type = 1 ");
		sql.append(" AND cid=? ");
		sql.append(" LIMIT 0,15 ");
		System.out.println(sql.toString());
		List<Products> productsList = new ArrayList<Products>();
		Connection conn = BaseDBUtils.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		pst = BaseDBUtils.getPreparedStatement(conn, sql.toString());
		rs = BaseDBUtils.executeQuery(pst, cid);
		if (rs != null) {
			while (rs.next()) {
				productsList.add(new Products(rs.getInt("pid"), rs.getString("pname"), rs.getDouble("market_price"),
						rs.getDouble("shop_price"), rs.getString("image"), rs.getString("pdesc")));
			}
		}
		return productsList;
	}

	@Override
	public List<Products> selectUnpaidShoppingCartByUserid(int userid) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT b.pname,b.shop_price,a.snum");
		sql.append(" FROM shoppingcart AS a INNER JOIN products AS b ");
		sql.append(" WHERE 1=1 ");
		sql.append(" AND a.pid = b.pid ");
		sql.append(" AND a.userid = ? ");
		sql.append(" AND a.state = 1 ");
		PreparedStatement ps = BaseDBUtils.getPreparedStatement(BaseDBUtils.getConnection(), sql.toString());
		ResultSet rs = BaseDBUtils.executeQuery(ps, userid);
		List<Products> list = new ArrayList<Products>();
		while (rs.next()) {
			list.add(new Products(rs.getString(1), rs.getDouble(2), rs.getInt(3)));
		}
		return  list;
	}
	@Override
	public Map<String, Object> selectDataByUser_id(Map<String, Object> map) throws SQLException {
		StringBuilder sql = new StringBuilder("SELECT MONTH(ordertime),SUM(money) FROM orders");
		sql.append(" WHERE 1=1 ");
		sql.append(" AND state = 4 ");
		sql.append(" AND user_id = ? ");
		sql.append(" AND YEAR(ordertime) = ? ");
		sql.append(" GROUP BY MONTH(ordertime) ");
		Object[] params = {map.get("user_id"),map.get("year")};
		PreparedStatement ps = BaseDBUtils.getPreparedStatement(BaseDBUtils.getConnection(), sql.toString());
		ResultSet rs = BaseDBUtils.executeQuery(ps, params);
		Map<String, Object> info = new HashMap<String, Object>();
		while (rs.next()) {
			info.put("m"+rs.getInt(1), rs.getDouble(2));
		}
		return info;
	}

	@Override
	public int updateOrderStateByOrder_id3(String order_id) throws SQLException {
		StringBuilder sql = new StringBuilder(" UPDATE `orders` SET ");
		sql.append(" `state` = 3 ");
		sql.append(" WHERE 1 = 1 ");
		sql.append(" and order_id = ? ");
		return qr.update(BaseDBUtils.getConnection(), sql.toString(), order_id);
	}

}
