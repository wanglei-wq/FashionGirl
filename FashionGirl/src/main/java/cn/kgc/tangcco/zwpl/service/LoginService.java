package cn.kgc.tangcco.zwpl.service;

import java.sql.SQLException;
import java.util.Map;

import cn.kgc.tangcco.zwpl.pojo.CustomerFeedBack;
import cn.kgc.tangcco.zwpl.pojo.DeliveryInformation;
import cn.kgc.tangcco.zwpl.pojo.Orders;
import cn.kgc.tangcco.zwpl.pojo.ShoppingCart;
import cn.kgc.tangcco.zwpl.pojo.User;
import cn.kgc.tangcco.zwpl.pojo.WishList;

public interface LoginService {
	/**
	 * 登陆功能
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	Map<String, Object> loginByAccountAndPassword(User user) throws SQLException;

	/**
	 * 注册功能
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	Map<String, Object> regist(User user) throws SQLException;

	/**
	 * ajax异步提交判断账号存不存在
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	Long checkAccount(User user) throws SQLException;
	/**
	 * 根据用户id查询用户信息
	 * @param id
	 * @return
	 */
	Map<String, Object> queryUserById(int id);
	/**
	 * 修改功能
	 * @param user
	 * @return
	 */
	Map<String, Object> updateUser(User user);
	/**
	 * 查询所有商品
	 * @param map
	 * @return
	 */
	Map<String, Object> queryAllProducts(Map<String, Object> map);
	/**
	 * 人脸注册把faceId添加到数据库表中
	 * @param userId
	 * @return
	 */
	Map<String, Object> addFaceId(String faceid);
	/**
	  * 人脸登陆根据faceid查询用户信息显示到页面上，便于修改！
	 * @param userId
	 * @return
	 */
	Map<String, Object> queryUserByFaceId(String faceId);
	
	/**
	 * 通过商品id(pid)拿到商品的所有信息
	 * @param pid
	 * @return 
	 */
	Map<String, Object> queryProductsByPid(int pid);
	
	/**
	 * 通过user_id找到订单
	 * @param user_id
	 * @return
	 */
	Map<String, Object> queryOrdersByUser_id(int user_id);
	
	/**
	 * 通过订单id查询所有的订单项
	 * @param order_id
	 * @return
	 */
	Map<String, Object> queryAllOrderitemByOrder_id(String order_id);
   /**
	 *查询商品简略信息功能
	 *@param map 页数
	 * @return  商品简略信息
	 * @throws SQLException 
	 */
	Map<String, Object> queryBriefInformationOfGoods(Map<String, Object> map);
	/**
	 * 用户提交用户反馈
	 * @param customerfeedback
	 * @return
	 */
	Map<String, Object> addCustomerFeedback(CustomerFeedBack customerfeedback);
	/**
	 *查询心愿单 
	 */
	Map<String, Object> queryWishList();
	/**
	 *删除心愿单中的商品 
	 */
	Map<String, Object> removeWishList(int wid);
	/**
	 *将商品添加到心愿单 
	 *@param wishList心愿单的编号
	 */
	Map<String, Object> addWishList(WishList wishList);
	/**
	 *查询购物车 
	 * @param user_id 
	 */
	Map<String, Object> queryShoppingCarts(int user_id);
	/**
	 *移除购物车中的商品 
	 */
	Map<String, Object> removeShoppingCart(int sid);
	/**
	 *添加购物车 
	 */
	Map<String, Object> addShoopingCart(ShoppingCart shoppingCart);
	/**
	 *修改购物车商品数量 
	 */
	Map<String, Object> modifyShoppingSnumBySid(ShoppingCart shoppingCart);
	/**
	 *查询购物车选中购物车的商品，生成订单，跳到支付界面进行支付
	 */
	Map<String, Object> optionProductsAddOrderAndCheckOut(Orders orders);
	/**
	 * 增加订单项，（注意）先生成订单
	 * @param order_id
	 * @param ordersItem
	 */
	Map<String, Object> AddOrdersItemAndCheckOut(String order_id, String ordersItem);
	/**
	 * 用户添加收货地址
	 * @param deliveryinformation
	 * @return
	 */
	Map<String, Object> addDeliveryInformation(DeliveryInformation deliveryinformation);
	
	/**
	 * 按用户ID查找收货地址
	 * @param user_id
	 * @return
	 */
	Map<String, Object> selectDeliveryInformation(int user_id);
	
	/**
	 * 根据用户的收货地址ID去删除地址
	 * @param id
	 * @return
	 */
	Map<String, Object> deleteDeliveryInformation(int id);
	
	/**
	 * 根据用户的收货地址ID去修改收货信息
	 * @param deliveryinformation
	 * @return
	 */
	Map<String, Object> updateDeliveryInformation(DeliveryInformation deliveryinformation);
	
	/**
	 *  根据id查找信息
	 * @param id
	 * @return
	 */
	Map<String, Object> selectDeliveryInformationByid(int id);
	/**
	 *查询主页面商品简略信息 (index01-04)通用的方法（12条数据）
	 */
	public Map<String, Object> selectQueryInformation(int cid);
	//标题栏购物车商品
	Map<String, Object> queryUnpaidShoppingCartByUserid(int userid);
	/**
	 * 根据用户id和年份查询该用户本年各月份的消费情况
	 * @param map
	 * @return
	 */
	Map<String, Object> queryDataByUser_id(Map<String, Object> map);
	/**
	 * 根据用户id和年份查询该用户本年各月份的消费情况
	 * @param map
	 * @return
	 */
	Map<String, Object> updateOrderStateByOrder_id3(String order_id);

}
