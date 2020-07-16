package cn.kgc.tangcco.zwpl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 后台用户登录实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUser {
	private int id;
	private String username;
	private String password;
	private int power;
	public AdminUser(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
}
