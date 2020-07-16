package cn.kgc.tangcco.zwpl.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.kgc.tangcco.zwpl.common.servelt.BaseServlet;
import cn.kgc.tangcco.zwpl.common.spring.ClassPathXmlApplicationContext;
import cn.kgc.tangcco.zwpl.pojo.AdminUser;
import cn.kgc.tangcco.zwpl.pojo.Orders;
import cn.kgc.tangcco.zwpl.service.DoService;

@WebServlet("/serveltActionBank.action")
public class ServeltAction extends BaseServlet {
	private static final long serialVersionUID = -8973340609075200073L;
	static DoService doService;
	static {
		try {
			ClassPathXmlApplicationContext cpx = new ClassPathXmlApplicationContext(
					"spring/ApplicationContext-service.xml");
			doService = (DoService) cpx.getBean(DoService.class.getSimpleName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 后台登录的执行的方法
	public void login(HttpServletRequest req, HttpServletResponse resp) {
		String username = req.getParameter("username").trim();
		String password = req.getParameter("password").trim();
		Map<String, Object> map;
		try {
			map = doService.login(new AdminUser(username, password));
			switch (map.get("status").toString()) {
			case "success":
				req.setAttribute("adminUser", map.get("queryUser"));
				forword(req, resp, subfix("index"));
				break;
			case "failed":
				break;
			default:
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询所有订单
	 */
	public void queryAllOrders(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("查询");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("information", Optional.ofNullable(request.getParameter("information")).orElse(""));
		System.out.println(map.get("information").toString());
		Map<String, Object> allMap = doService.queryAllOrders(map);
		switch (allMap.get("status").toString()) {
		case "success":
			System.out.println("查询成功");
			request.setAttribute("allMap", allMap);
			forword(request, response, subfix("order-list"));
			break;
		default:
			System.out.println("查询失败");
			break;
		}
	}

	/**
	 * 修改订单状态
	 */
	public void modifyState(HttpServletRequest request, HttpServletResponse response) {
		String order_id = request.getParameter("order_id");
		System.out.println(order_id);
		String states = request.getParameter("state");
		System.out.println(states);
		int state = Integer.parseInt(states);
		Orders orders = new Orders(order_id, state);
		Map<String, Object> map = doService.modifyState(orders);
		switch (map.get("status").toString()) {
		case "success":
			System.out.println("修改成功");
			break;
		default:
			System.out.println("修改失败");
			break;
		}
		System.out.println("过来");
		// forword(request, response,
		// "/serveltActionBank.action?methodName=queryAllOrders");
		queryAllOrders(request, response);
	}

	/**
	 * 查看订单详情
	 */
	public void viewOrderDetails(HttpServletRequest request, HttpServletResponse response) {
		String order_id = request.getParameter("order_id");
		Map<String, Object> map = doService.viewOrderDetails(order_id);
		switch (map.get("status").toString()) {
		case "success":
			System.out.println("查询成功");
			request.setAttribute("orders", map.get("orders"));
			forword(request, response, subfix("order-details"));
			break;

		default:
			System.out.println("查询失败");
			break;
		}
	}
}
