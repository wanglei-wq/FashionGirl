package daotest;

import java.sql.SQLException;

import org.junit.Test;

import cn.kgc.tangcco.zwpl.dao.DoDao;
import cn.kgc.tangcco.zwpl.dao.impl.DoDaoImp;
import cn.kgc.tangcco.zwpl.pojo.AdminUser;

public class DoDaoTest {
	DoDao doDao = new DoDaoImp();
	@Test
	public void test01() throws SQLException {
		AdminUser user = doDao.login(new AdminUser("admin", "admin"));
		System.out.println(user);
	}
}
