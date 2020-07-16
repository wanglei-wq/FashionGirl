package servicetest;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import cn.kgc.tangcco.zwpl.common.jdbc.PageRang;
import cn.kgc.tangcco.zwpl.pojo.Orders;
import cn.kgc.tangcco.zwpl.pojo.User;
import cn.kgc.tangcco.zwpl.service.LoginService;
import cn.kgc.tangcco.zwpl.service.impl.LoginServiceImp;
import cn.kgc.tangcco.zwpl.utils.RandomNumber;

public class LoginServiceTest {
	LoginService loginService = new LoginServiceImp();
	@Test
	public void test() throws SQLException {
		Long i = loginService.checkAccount(new User("admi", null));
		System.out.println(i);
	}
	@Test
	public void test02() throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pr", new PageRang(1,9));
		Map<String, Object> queryMap = loginService.queryBriefInformationOfGoods(map);
		System.out.println(queryMap.get("productList"));
	}
	@Test
	public void test03() throws SQLException {
		Map<String, Object> queryMap = loginService.queryOrdersByUser_id(1);
		System.out.println(queryMap.get("data"));
	}
//	@Test
//	public void test04() throws SQLException {
//		Map<String, Object> map = loginService.optionProductsAddOrderAndCheckOut(new Orders(RandomNumber.orderRandom(),66666,0,2));
//		switch (map.get("status").toString()) {
//		case "success":
//			System.out.println(map.get("data"));
//			break;
//			
//		default:
//			break;
//		}
//	}
}
