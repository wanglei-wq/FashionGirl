package cn.kgc.tangcco.zwpl.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cn.kgc.tangcco.zwpl.pojo.CustomerFeedBack;
import cn.kgc.tangcco.zwpl.pojo.DeliveryInformation;
import cn.kgc.tangcco.zwpl.pojo.Orderitem;
import cn.kgc.tangcco.zwpl.pojo.Orders;
import cn.kgc.tangcco.zwpl.pojo.Products;
import cn.kgc.tangcco.zwpl.pojo.ShoppingCart;
import cn.kgc.tangcco.zwpl.pojo.User;
import cn.kgc.tangcco.zwpl.pojo.WishList;


public interface LoginDao {
	/**
	 * 登陆功能
	 * @param user
	 * @return 
	 * @throws SQLException 
	 */
	User loginByAccountAndPassword(User user) throws SQLException;
	/**
	 * 注册功能
	 * @param user
	 * @return
	 * @throws SQLException 
	 */
	int regist(User user) throws SQLException;
	/**
	 * ajax异步提交判断账号存不存在
	 * @param user
	 * @return
	 * @throws SQLException 
	 */
	Long checkAccount(User user) throws SQLException;
	/**
	 *修改功能,修改完在查询一遍
	 * @param user
	 * @return
	 * @throws SQLException 
	 */
	int updateUser(User user) throws SQLException;
	/**
	 * 根据用户id查询用户的信息
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	User queryUserById(int id) throws SQLException;
	/**
	 * 查询所有商品
	 * @param map 条件查询
	 * @return
	 * @throws SQLException 
	 */
	List<Products> selectAllProducts(Map<String, Object> map) throws SQLException;
	/**
	 * 添加faceId到数据库
	 * @param userId
	 * @return
	 * @throws SQLException 
	 */
	int addFaceId(String faceid) throws SQLException;
	/**
	 * 查询faceId登陆的用户信息，拿到id保存信息用
	 * @param userId
	 * @return
	 * @throws SQLException 
	 */
	User queryFaceId(String faceid) throws SQLException;
	/**
	 * 根据faceId查询用户的信息，返回给界面！
	 * @param faceId
	 * @return
	 * @throws SQLException 
	 */
	User queryUserByFaceId(String faceId) throws SQLException;
	/**
	 * 通过user_id找到订单
	 * @param user_id
	 * @return
	 * @throws SQLException 
	 */
	List<Orders> selectOrdersByUser_id(int user_id) throws SQLException;
	/**
	 * 通过订单order_id(主键)查询所有订单项
	 * @param o_oid
	 * @return
	 * @throws SQLException
	 */
	List<Orderitem> selectAllOrderitemByOrder_id(String order_id) throws SQLException;
	/**
	 * 通过商品id(pid)拿到商品的所有信息
	 * @param pid
	 * @return
	 * @throws SQLException 
	 */
	Products selectProductsByPid(int pid) throws SQLException;
	/**
	 *查询商品简略信息功能
	 *@param map 页数
	 * @return  商品简略信息
	 * @throws SQLException 
	 */
	List<Products> selectBriefInformationOfGoods(Map<String, Object> map) throws SQLException;
	/**
	 *查询商品总记录数
	 *@param map 
	 * @return  总记录数
	 * @throws SQLException 
	 */
	long selectCountOfProducts(Map<String, Object> map) throws SQLException;
	/**
	 * 用户提交用户反馈内容
	 * @param customerfeedback
	 * @return
	 * @throws SQLException 
	 */
	int addCustomerFeedback(CustomerFeedBack customerfeedback) throws SQLException;
	/**
	 *查询心愿单 
	 * @throws SQLException 
	 */
	List selectWishLists() throws SQLException;
	/**
	 *删除心愿单中的商品 
	 *@param wid  心愿单编号
	 * @throws SQLException 
	 */
	int deletWishList(int wid) throws SQLException;
	/**
	 *将商品添加到心愿单 
	 *@param wishList心愿单的编号
	 * @throws SQLException 
	 */
	int insertWishList(WishList wishList) throws SQLException;
	/**
	 *查询购物车 
	 * @param user_id 
	 * @throws SQLException 
	 */
	public List<ShoppingCart> selectShoppingCarts(int user_id) throws SQLException;
	/**
	 *移除购物车中的商品 
	 * @throws SQLException 
	 */
	int delectShoppingCart(int sid) throws SQLException;
	/**
	 *添加购物车 
	 * @throws SQLException 
	 */
	int insertShoopingCart(ShoppingCart shoppingCart) throws SQLException;
	/**
	 *修改商品数量 
	 * @throws SQLException 
	 */
	int updateShoppingSnumBySid(ShoppingCart shoppingCart) throws SQLException;
	/**
	 *查询购物车选中购物车的商品，生成订单，跳到支付界面进行支付
	 * @throws SQLException 
	 */
	int optionProductsAddOrderAndCheckOut(Orders orders) throws SQLException;
	Orders selectOrderByOrderId(String order_id) throws SQLException;
	/**
	 * 增加订单项，（注意）先生成订单
	 * @param order_id
	 * @param ordersItem
	 * @throws SQLException 
	 */
	int addOrderItemByOrderId(Orderitem orderItemObject) throws SQLException;
	/**
	 * 添加收货人信息 
	 * @param deliveryinformation
	 * @return
	 * @throws SQLException 
	 */
	int addDeliveryInformation(DeliveryInformation deliveryinformation) throws SQLException;
	
	/**
	 * 根据用户ID查找收货地址
	 * @return
	 * @throws SQLException 
	 */
	List<DeliveryInformation> selectDeliveryInformation(int user_id) throws SQLException;
	
	/**
	 * 根据用户收货地址ID删除收货地址
	 * @return
	 * @throws SQLException 
	 */
	int deleteDeliveryInformation(int id) throws SQLException;
	
	/**
	 * 根据用户的收货地址ID去修改收货信息
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	int updateDeliveryInformation(DeliveryInformation deliveryinformation) throws SQLException;
	
	/**
	 * 根据id查找信息
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	List<DeliveryInformation> selectDeliveryInformationByid(int id) throws SQLException;
	/**
	 *查询主页面商品简略信息 (index01-04)通用的方法（12条数据）
	 */
	List<Products> selectQueryInformation(int cid) throws SQLException;
	/**
	 * 标题栏购物车商品
	 * @param userid
	 * @return
	 * @throws SQLException
	 */
	List<Products> selectUnpaidShoppingCartByUserid(int userid) throws SQLException;
	/**
	 * 根据用户id和年份查询该用户本年各月份的消费情况
	 * @param user_id
	 * @param year
	 * @return
	 * @throws SQLException 
	 */
	Map<String, Object> selectDataByUser_id (Map<String, Object> map) throws SQLException;
	/**
	 * 修改订单状态，买家收货
	 * @param req
	 * @param resp
	 */
	int updateOrderStateByOrder_id3(String order_id) throws SQLException;
	
}
