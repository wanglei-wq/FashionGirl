package daotest;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import cn.kgc.tangcco.zwpl.common.jdbc.BaseDBUtils;
import cn.kgc.tangcco.zwpl.common.jdbc.PageRang;
import cn.kgc.tangcco.zwpl.dao.AlipayDao;
import cn.kgc.tangcco.zwpl.dao.LoginDao;
import cn.kgc.tangcco.zwpl.dao.impl.AlipayDaoImpl;
import cn.kgc.tangcco.zwpl.dao.impl.LoginDaoImp;
import cn.kgc.tangcco.zwpl.pojo.AlipayOrder;
import cn.kgc.tangcco.zwpl.pojo.Orderitem;
import cn.kgc.tangcco.zwpl.pojo.Orders;
import cn.kgc.tangcco.zwpl.pojo.Products;
import cn.kgc.tangcco.zwpl.pojo.ShoppingCart;
import cn.kgc.tangcco.zwpl.pojo.User;
import cn.kgc.tangcco.zwpl.utils.RandomNumber;

public class LoginDaoTest {
	LoginDao logindao = new LoginDaoImp();
	AlipayDao alipaydao = new AlipayDaoImpl();
//	@Test
//	public void test() {
//		try {
//			int i = logindao.regist(new User(3, "aa", "aa", "aa", "aa", "aa",1,"aa", "aa"));
//			System.out.println(i);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	@Test
	public void test01() {
		for(int i=0 ;i<1000;i++) {
			String random = RandomNumber.smsRandom();
			System.out.println(random);
		}
	}
	@Test
	public void test02() throws SQLException {
		Long i = logindao.checkAccount(new User("admin", null));
		System.out.println(i);
	}
	@Test
	public void test05() throws SQLException {
		int i = alipaydao.addAlipayOrder(new AlipayOrder(1, "157234589531908056437", "2019102922001475671000233174", new BigDecimal("19998.00"), 1));
		System.out.println(i);
	}
//	@Test
//	public void test03() throws SQLException {
//		int i = logindao.addFaceId("1234657879");
//		System.out.println(i);
//	}
	@Test
	public void test04() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pr", new PageRang(1,9));
//			Map<String, Object> map=new HashMap<String, Object>();
//			map.put("pageNum",1);
			List<Products> list=logindao.selectBriefInformationOfGoods(map);
			if (list!=null) {
				for (Products products : list) {
					System.out.println(products);
				}
			}
		} catch (SQLException e) {
			System.out.println("查询失败");
			e.printStackTrace();
		}
	}
	@Test
	public void test06() throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		long rowCount = logindao.selectCountOfProducts(map);
		System.out.println(rowCount);
	}
	@Test
	public void test07() throws SQLException {
		List<Orders> rowCount = logindao.selectOrdersByUser_id(1);
		System.out.println(rowCount);
	}
//	@Test
//	public void test08() throws SQLException {
//		List<ShoppingCart> rowCount = logindao.selectShoppingCarts();
//		System.out.println(rowCount);
//	}
	@Test
	public void test09() throws SQLException {
		int i = logindao.optionProductsAddOrderAndCheckOut(new Orders("6699787664642799",66666,new Date(),0,2));
		System.out.println(i);
	}
	@Test
	public void test10() throws SQLException {
		Orders order = logindao.selectOrderByOrderId("66997876646427");
		System.out.println(order);
	}
	@Test
	public void test11() {
		String orderRandom = RandomNumber.orderRandom();
		System.out.println(orderRandom);
	}
	@Test
	public void test12() {
		//增加订单项的表  数据类型6X68886X1,8X13899X1,
		String[] split = StringUtils.split("6X68886X1,8X13899X1,",",");
		for (int i = 0; i < split.length; i++) {
			String string = split[i];
			String[] split2 = StringUtils.split(string, "X");
			//String order_id, int p_pid，int count, int subtotal
			Orderitem orderItemObject = new Orderitem("8000001919964971", Integer.parseInt(split2[0]),new BigDecimal(split2[1]),Integer.parseInt(split2[2]));
			try {
				logindao.addOrderItemByOrderId(orderItemObject);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (i>0) {
				System.out.println("订单添加成功！");
			}
		}
	}
	
}
