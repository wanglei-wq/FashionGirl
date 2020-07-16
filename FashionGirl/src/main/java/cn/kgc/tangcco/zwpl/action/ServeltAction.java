package cn.kgc.tangcco.zwpl.action;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import cn.kgc.tangcco.zwpl.common.jdbc.PageRang;
import cn.kgc.tangcco.zwpl.common.servelt.BaseServlet;
import cn.kgc.tangcco.zwpl.common.spring.ClassPathXmlApplicationContext;
import cn.kgc.tangcco.zwpl.pojo.CustomerFeedBack;
import cn.kgc.tangcco.zwpl.pojo.DeliveryInformation;
import cn.kgc.tangcco.zwpl.pojo.Orderitem;
import cn.kgc.tangcco.zwpl.pojo.Orders;
import cn.kgc.tangcco.zwpl.pojo.Products;
import cn.kgc.tangcco.zwpl.pojo.ShoppingCart;
import cn.kgc.tangcco.zwpl.pojo.User;
import cn.kgc.tangcco.zwpl.pojo.WishList;
import cn.kgc.tangcco.zwpl.service.LoginService;
import cn.kgc.tangcco.zwpl.utils.RandomNumber;

/**
 * Servlet implementation class serveltAction
 */
@WebServlet("/serveltAction.action")
public class ServeltAction extends BaseServlet {
	public static final long serialVersionUID = 995016456805343078L;
	static LoginService loginService;
	static {
		try {
			ClassPathXmlApplicationContext cpx = new ClassPathXmlApplicationContext(
					"spring/ApplicationContext-service.xml");
			loginService = (LoginService) cpx.getBean(LoginService.class.getSimpleName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 注册
	 * 
	 * @param req
	 * @param resp
	 */
	public void regist(HttpServletRequest req, HttpServletResponse resp) {
		String account = req.getParameter("account").trim();
		String password = req.getParameter("password").trim();
		String phone = req.getParameter("phone").trim();
		String nickname = req.getParameter("nickname").trim();
		Map<String, Object> map;
		try {
			map = loginService.regist(new User(account, password, nickname, phone));
			switch (map.get("status").toString()) {
			case "success":
				printJson(req, resp, map);
				break;
			case "failed":
				printJson(req, resp, map);
				break;
			default:
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 登陆
	 * 
	 * @param req
	 * @param resp
	 */
	public void login(HttpServletRequest req, HttpServletResponse resp) {
		String account = req.getParameter("account").trim();
		String password = req.getParameter("password").trim();
		Map<String, Object> map;
		try {
			map = loginService.loginByAccountAndPassword(new User(account, password));
			switch (map.get("status").toString()) {
			case "success":
				map.put("queryUser", map.get("queryUser"));
				printJson(req, resp, map);
				break;
			case "failed":
				printJson(req, resp, map);
				break;
			default:
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ajax异步检查账号是否存在
	 * 
	 * @param req
	 * @param resp
	 */
	public void checkAccount(HttpServletRequest req, HttpServletResponse resp) {
		String account = req.getParameter("account").trim();
		try {
			printJson(req, resp, loginService.checkAccount(new User(account, null)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新用户信息
	 * 
	 * @param request
	 * @param response
	 */
	public void updateUser(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		String nickname = request.getParameter("nickname").trim();
		String address = request.getParameter("address").trim();
		String phone = request.getParameter("phone").trim();
		String account = request.getParameter("account").trim();
		// 获取新密码
		String newpassword = request.getParameter("newpassword").trim();
		// 判断得到的密码和编号是否为空
		System.out.println(nickname);
		String sex = request.getParameter("sex").trim();
		String email = request.getParameter("email").trim();
		Map<String, Object> updateMap = loginService.updateUser(new User(account, newpassword, nickname, sex, email, address, phone, id));
		switch (updateMap.get("status").toString()) {
		case "success":
			System.out.println("修改用户信息成功！");
			printJson(request, response, updateMap);
			break;
		case "failed":
			System.out.println("修改用户信息失败！");
			printJson(request, response, updateMap);
			break;
		default:
			break;
		}
	}
	/**
	 * 人脸登陆根据faceid查询用户信息显示到页面上，便于修改！
	 * 
	 * @param req
	 * @param resp
	 */
	public void queryUserById(HttpServletRequest req, HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		Map<String, Object> map = loginService.queryUserById(id);
		printJson(req, resp, map);
	}
	/**
	 * 人脸登陆根据faceid查询用户信息显示到页面上，便于修改！
	 * 
	 * @param req
	 * @param resp
	 */
	public void queryUserByFaceId(HttpServletRequest req, HttpServletResponse resp) {
		String faceId = req.getParameter("faceId");
		Map<String, Object> map = loginService.queryUserByFaceId(faceId);
		printJson(req, resp, map);
	}

	/**
	 * 首页查询包包
	 * 
	 * @param req
	 * @param resp
	 */
	public void queryAllProducts(HttpServletRequest req, HttpServletResponse resp) {
		Map<String, Object> map = new HashMap<String, Object>();
		int cid = Integer.parseInt(req.getParameter("cid"));
		map.put("cid", cid);
		Map<String, Object> info = loginService.queryAllProducts(map);
		printJson(req, resp, info);
	}

	/**
	 * 查询所有订单
	 * 
	 * @param req
	 * @param resp
	 */
	public void queryOrdersByUser_id(HttpServletRequest req, HttpServletResponse resp) {
		int user_id = Integer.parseInt(req.getParameter("user_id"));
		System.out.println("查询当前用户的订单方法走到了，当前用户是:" + user_id);
		Map<String, Object> info = loginService.queryOrdersByUser_id(user_id);
		List<Orders> list = (List<Orders>) info.get("data");
		if (list != null && list.size() > 0) {
			// 如果该用户有订单，则写回去
			printJson(req, resp, info);
		} else {
			//
			printJson(req, resp, "空空如也");
			System.out.println("该用户还没有任何订单");
		}
	}
	/**
	 * 修改订单状态，买家收货
	 * @param req
	 * @param resp
	 */
	public void updateOrderStateByOrder_id3(HttpServletRequest req, HttpServletResponse resp) {
		String order_id = req.getParameter("order_id");
		Map<String, Object> info = loginService.updateOrderStateByOrder_id3(order_id);
		switch (info.get("status").toString()) {
		case "success":
			printJson(req, resp, info);
			break;
		default:
			printJson(req, resp, info);
			break;
		}
	}
	/**
	 * 查询当前订单的所有订单项
	 * 
	 * @param req
	 * @param resp
	 */
	public void queryAllOrderitemByOrder_id(HttpServletRequest req, HttpServletResponse resp) {
		String order_id = req.getParameter("order_id");
		System.out.println(order_id);
		Map<String, Object> info = loginService.queryAllOrderitemByOrder_id(order_id);
		List<Orderitem> list = (List<Orderitem>) info.get("data");
		if (list != null && list.size() > 0) {
			// 如果该用户有订单，则写回去
			printJson(req, resp, info);
		} else {
			//
			printJson(req, resp, "空空如也");
			System.out.println("这种情况不可能发生");
		}
	}

	/**
	 * 查询商品简略信息
	 */
	public void queryBriefInformationOfGoods(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("我过来啦");
		// 当前页码
		int pageNum = (StringUtils.isEmpty(request.getParameter("pageNum")) ? 1
				: Math.abs(Integer.parseInt(request.getParameter("pageNum"))));
		int pageSize = (StringUtils.isEmpty(request.getParameter("pageSize")) ? 9
				: Math.abs(Integer.parseInt(request.getParameter("pageSize"))));
		System.out.println("当前页码" + pageNum);
		System.out.println("每页条数" + pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pr", new PageRang(pageNum, pageSize));
		// 返回商品信息
		Map<String, Object> productMap = loginService.queryBriefInformationOfGoods(map);
		switch (productMap.get("status").toString()) {
		case "success":
			printJson(request, response, productMap);
			break;
		default:
			System.out.println("查询失败");
			break;
		}

	}

	/**
	 * 用户提交用户反馈
	 * 
	 * @param customerfeedback
	 * @return
	 */
	public void addCustomerFeedback(HttpServletRequest req, HttpServletResponse resp) {
		String name = req.getParameter("first_name");
		String phone = req.getParameter("phone");
		String mailbox = req.getParameter("email_address");
		String theme = req.getParameter("contact_subject");
		String feedback = req.getParameter("message");
		CustomerFeedBack customerfeedback = new CustomerFeedBack(name, phone, mailbox, theme, feedback);
		Map<String, Object> map = loginService.addCustomerFeedback(customerfeedback);
		switch (map.get("status").toString()) {
		case "success":
			printJson(req, resp, map);
			break;
		default:
			printJson(req, resp, map);
			break;
		}
	}

	/**
	 * 查询心愿单
	 */
	public void queryWishList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> wishListMap = loginService.queryWishList();
		switch (wishListMap.get("status").toString()) {
		case "success":
			printJson(request, response, wishListMap);
			break;
		default:
			break;
		}
	}

	/**
	 * 删除心愿单中的商品
	 */
	public void removeWishList(HttpServletRequest request, HttpServletResponse response) {
		String wids = request.getParameter("wid");
		System.out.println(wids);
		int wid = Integer.parseInt(wids);
		Map<String, Object> removeMap = loginService.removeWishList(wid);
		switch (removeMap.get("status").toString()) {
		case "success":
			System.out.println("删除成功");
			printJson(request, response, removeMap);
			break;
		default:
			System.out.println("删除失败");
			break;
		}
	}

	/**
	 * 将商品添加到心愿单
	 * 
	 * @param wishList心愿单的编号
	 */
	public void addWishList(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("添加心愿单的方法走到了");
		int pid = Integer.parseInt(request.getParameter("pid"));
		int userid = Integer.parseInt(request.getParameter("userid"));
		WishList wishList = new WishList(pid, userid);
		Map<String, Object> addMap = loginService.addWishList(wishList);
		switch (addMap.get("status").toString()) {
		case "success":
			System.out.println("添加成功");
			printJson(request, response, addMap);
			break;
		default:
			System.out.println("添加失败");
			break;
		}
	}

	/**
	 * 查询商品详情界面
	 * 
	 * @param request
	 * @param response
	 */
	public void queryProductsByPid(HttpServletRequest request, HttpServletResponse response) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		Map<String, Object> info = loginService.queryProductsByPid(pid);
		switch (info.get("status").toString()) {
		case "success":
			Products products = (Products) info.get("products");
			// 写回去一个商品对象
			printJson(request, response, products);
			break;

		default:
			printJson(request, response, "没有该id的商品");
			System.out.println("没有该id的商品");
			break;
		}
	}

	/**
	 * 查询购物车
	 */
	public void queryShoppingCarts(HttpServletRequest request, HttpServletResponse response) {
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		Map<String, Object> shoppingCartMap = loginService.queryShoppingCarts(user_id);
		switch (shoppingCartMap.get("status").toString()) {
		case "success":
			printJson(request, response, shoppingCartMap);
			break;

		default:
			break;
		}
	}

	/**
	 * 查询购物车选中购物车的商品，生成订单，跳到支付界面进行支付
	 */
	public void optionProductsAddOrderAndCheckOut(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("查询购物车选中购物车的商品，生成订单，跳到支付界面进行支付");
		double money = Double.parseDouble(request.getParameter("money"));
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		String ordersItem = request.getParameter("ordersItem");
		System.out.println(ordersItem);
		Map<String, Object> map = loginService.optionProductsAddOrderAndCheckOut(
				new Orders(RandomNumber.orderRandom(), money, new Date(), 0, user_id));
		switch (map.get("status").toString()) {
		case "success":
			// 生成订单成功，添加订单项
			Map<String, Object> ordersItemMap = loginService
					.AddOrdersItemAndCheckOut(((Orders) map.get("data")).getOrder_id(), ordersItem);
			if (ordersItemMap.get("status").toString().equals("success")) {
				printJson(request, response, map);
			} else {
				System.out.println("订单项添加失败");
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 移除购物车中的商品
	 */
	public void removeShoppingCart(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("我来删除啦");
		String sids = request.getParameter("sid");
		System.out.println(sids);
		int sid = Integer.parseInt(sids);
		Map<String, Object> delectShoppingCartMap = loginService.removeShoppingCart(sid);
		switch (delectShoppingCartMap.get("status").toString()) {
		case "success":
			System.out.println("删除成功");
			printJson(request, response, delectShoppingCartMap);
			break;
		default:
			System.out.println("删除失败");
			break;
		}
	}

	/**
	 * 添加购物车
	 */
	public void addShoopingCart(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("添加购物车的方法走到了");
		// 商品的id
		int pid = Integer.parseInt(request.getParameter("pid"));
		System.out.println(pid);
		// id用户的
		int userid = Integer.parseInt(request.getParameter("userid"));
		// 默认sum为一件sum=1;
		// 这个商品的几件的小计
		Double total = Double.parseDouble(request.getParameter("total"));
		Map<String, Object> addMap = loginService.addShoopingCart(new ShoppingCart(pid, userid, 1, total));
		switch (addMap.get("status").toString()) {
		case "success":
			System.out.println("添加成功");
			printJson(request, response, addMap);
			break;

		default:
			System.out.println("添加失败");
			break;
		}
	}

	/**
	 * 修改商品数量
	 */
	public void modifyShoppingSnumBySid(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("我过来修改了");
		String snums = request.getParameter("snum");
		System.out.println(snums);
		int snum = Integer.parseInt(snums);
		String sids = request.getParameter("sid");
		System.out.println(sids);
		int sid = Integer.parseInt(sids);
		String totals = request.getParameter("total");
		System.out.println(totals);
		Double total = Double.parseDouble(totals);
		ShoppingCart shoppingCart = new ShoppingCart(sid, snum, total);
		Map<String, Object> modifyMap = loginService.modifyShoppingSnumBySid(shoppingCart);
		switch (modifyMap.get("status").toString()) {
		case "success":
			System.out.println("修改成功");
			printJson(request, response, modifyMap);
			break;
		default:
			System.out.println("修改失败");
			break;
		}
	}

	/**
	 * 用户添加收货地址信息
	 * @param req
	 * @param resp
	 */
	public void addDeliveryInformation(HttpServletRequest req, HttpServletResponse resp) {
		int user_id = Integer.parseInt(req.getParameter("user_id"));
		String nickname = req.getParameter("nickname");
		String phone = req.getParameter("phone");
		String address = req.getParameter("address");
		DeliveryInformation deliveryinformation = new DeliveryInformation(user_id, nickname, phone, address);
		Map<String, Object> map = loginService.addDeliveryInformation(deliveryinformation);
		switch (map.get("status").toString()) {
		case "success":
			System.out.println("cg");
			printJson(req, resp, map);
			break;

		default:
			System.out.println("sb");
			printJson(req, resp, map);
			break;
		}
	}

	/**
	 *  按用户user_id去查找收货地址信息
	 * @param req
	 * @param resp
	 */
	public void selectDeliveryInformation(HttpServletRequest req, HttpServletResponse resp) {
		int user_id = Integer.parseInt(req.getParameter("user_id"));
		Map<String, Object> map = loginService.selectDeliveryInformation(user_id);
		switch (map.get("status").toString()) {
		case "success":
			System.out.println("查询成功！！！");
			printJson(req, resp, map);
			break;

		default:
			System.out.println("查询失败！！！");
			printJson(req, resp, map);
			break;
		}
	}

	/**
	 * 按收货ID去修改收货地址信息
	 * @param req
	 * @param resp
	 */
	public void updateDeliveryInformation(HttpServletRequest req, HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("id"));
		String user_name = req.getParameter("nickname2");
		String user_address = req.getParameter("address2");
		String user_phone = req.getParameter("phone2");
		DeliveryInformation deliveryinformation = new DeliveryInformation(user_name, user_phone, user_address, id);
		Map<String, Object> map = loginService.updateDeliveryInformation(deliveryinformation);
		switch (map.get("status").toString()) {
		case "success":
			System.out.println("修改成功！！！");
			printJson(req, resp, map);
			break;

		default:
			System.out.println("修改失败！！！");
			printJson(req, resp, map);
			break;
		}
	}

	/**
	 *  按ID去删除收货地址信息
	 * @param req
	 * @param resp
	 */
	public void deleteDeliveryInformation(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println(req.getParameter("index"));
		int id = Integer.parseInt(req.getParameter("index"));
		Map<String, Object> map = loginService.deleteDeliveryInformation(id);
		switch (map.get("status").toString()) {
		case "success":
			System.out.println("删除成功！！！");
			printJson(req, resp, map);
			break;

		default:
			System.out.println("删除失败！！！");
			printJson(req, resp, map);
			break;
		}
	}

	/**
	 * 按照ID查找收货地址，用来修改信息
	 * @param req
	 * @param resp
	 */
	public void selectDeliveryInformationByid(HttpServletRequest req, HttpServletResponse resp) {
		int id = Integer.parseInt(req.getParameter("index"));
		Map<String, Object> map = loginService.selectDeliveryInformationByid(id);
		switch (map.get("status").toString()) {
		case "success":
			List<DeliveryInformation> list = (List<DeliveryInformation>) map.get("data");
			System.out.println("找到了！！！");
			printJson(req, resp, map);
			break;

		default:
			System.out.println("失败了！！！");
			printJson(req, resp, map);
			break;
		}
	}
	/**
	 *查询主页面商品简略信息 (index01-04)通用的方法（12条数据）
	 */
	public void queryIndexQueryInformation(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("我过来啦");
		//返回商品信息
		String cids=request.getParameter("cid");
		int cid=Integer.parseInt(cids);
		Map<String, Object> productMap=loginService.selectQueryInformation(cid);
		switch (productMap.get("status").toString()) {
		case "success":
			printJson(request, response,productMap);
			break;
		default:
			System.out.println("查询失败");
			break;
		}
	}
	//标题栏购物车商品
	public void queryUnpaidShoppingCartByUserid(HttpServletRequest request,HttpServletResponse response) {
		int userid = Integer.parseInt(request.getParameter("userid"));
		Map<String, Object> info = loginService.queryUnpaidShoppingCartByUserid(userid);
		int count = (int) info.get("count");
		if (count > 0) {
			printJson(request, response, info);
		}else {
			printJson(request, response, "空空如也");
		}
	}
	/**
	 * echart生成消费图表
	 * @param request
	 * @param response
	 */
	public void queryDataByUser_id(HttpServletRequest request,HttpServletResponse response) {
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		//int year = Integer.parseInt(request.getParameter("year"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("year", 2019);
		Map<String, Object> info = loginService.queryDataByUser_id(map);
		printJson(request, response, info);
	}
}
