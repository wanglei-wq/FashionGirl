package cn.kgc.tangcco.zwpl.action.alipay;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import cn.kgc.tangcco.zwpl.common.alipay.AlipayConfig;
import cn.kgc.tangcco.zwpl.common.servelt.BaseServlet;
import cn.kgc.tangcco.zwpl.common.spring.ClassPathXmlApplicationContext;
import cn.kgc.tangcco.zwpl.pojo.AlipayOrder;
import cn.kgc.tangcco.zwpl.pojo.Orders;
import cn.kgc.tangcco.zwpl.service.AlipayService;

@WebServlet(urlPatterns = "/user.action")
public class AlipayAction extends BaseServlet{
	private static final long serialVersionUID = 1116436214066428953L;
	private static ClassPathXmlApplicationContext ca=new ClassPathXmlApplicationContext("spring/ApplicationContext-service.xml");
	private static AlipayService alipayService=null;
	static {
		try {
			alipayService=(AlipayService) ca.getBean("AlipayService");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void alipay(HttpServletRequest request, HttpServletResponse response) throws IOException {
			//获得初始化的AlipayClient
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
			
			//设置请求参数
			AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
			alipayRequest.setReturnUrl(AlipayConfig.return_url);
			alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
			
			String order_id = request.getParameter("alipayorder_id");
			String user_name = request.getParameter("user_name");
			String user_phone = request.getParameter("user_phone");
			String address = request.getParameter("address");
			try {
				int i  = alipayService.updateOrderNameAndPhoneAndAddressByOrderId(new Orders(order_id, user_name, user_phone, address));
				if(i>0) {
					System.out.println("订单地址--收货人--电话添加成功！");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//商户订单号，商户网站订单系统中唯一订单号，必填
			//商品描述，项目生成的订单号16位随机数
			String out_trade_no =order_id;
			//付款金额，必填
			String total_amount = request.getParameter("zongjihiden");
			//订单名称，必填
			String subject = "支付宝第三方测试";
			
			String body = "";
			alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
					+ "\"total_amount\":\""+ total_amount +"\"," 
					+ "\"subject\":\""+ subject +"\"," 
					+ "\"body\":\""+ body +"\","
					+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
			//请求
			String result=null;
			try {
				result = alipayClient.pageExecute(alipayRequest).getBody();
			} catch (AlipayApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.println(result);
		    response.getWriter().println(result);
	}
	
	public void callBack(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
		/**
		 * 支付宝的回调 告诉你 1.支付宝订单号 2.自己商城生成的订单号 3.付款金额
		 */
		// 商户订单号
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
		// 支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
		// 付款金额
		String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
		//设置相应的编码格式是 html
		response.setContentType("text/html;charset=utf-8");
		System.out.println("订单号:"+out_trade_no);
		System.out.println("支付宝交易号"+trade_no);
		System.out.println("付款金额"+total_amount);
		try {
			Map<String, Object> map = alipayService.addAlipayOrder(new AlipayOrder(out_trade_no, trade_no, new BigDecimal(total_amount), 1));
			switch (map.get("status").toString()) {
			case "success":
					// 修改订单的状态
					int i  = alipayService.updateOrderStateByOrderId(out_trade_no);
					if(i>0) {
						System.out.println("修改订单成功");
						response.sendRedirect("http://localhost:8080/FashionGirl/my-account.html");
					}else {
						response.sendRedirect("http://localhost:8080/FashionGirl/404.html");
					}
			default:
				response.sendRedirect("http://localhost:8080/FashionGirl/404.html");
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
