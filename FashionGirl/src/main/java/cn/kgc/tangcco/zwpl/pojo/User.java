package cn.kgc.tangcco.zwpl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private int id;
	private String account;
	private String password;
	private String nickname;
	private String sex;
	private String phone;
	private int state;
	private String address;
	private String email;
	private String faceid;
	/**
	 * 登陆时用的构造方法
	 * @param account
	 * @param password
	 */
	public User(String account,String password) {
		this.account = account;
		this.password = password;
	}
	/**
	 * 更新时用的构造方法
	 * @param account
	 * @param password
	 * @param nickname
	 * @param sex
	 * @param email
	 * @param address
	 * @param phone
	 * @param id
	 */
	public User(String account, String password, String nickname, String sex, String email,String address,String phone,int id) {
		super();
		this.account = account;
		this.password = password;
		this.nickname = nickname;
		this.sex = sex;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.id=id;
	}
	/**
	 * 注册时候用的构造函数
	 * @param account
	 * @param password
	 * @param nickname
	 * @param phone
	 */
	public User(String account, String password, String nickname, String phone) {
		super();
		this.account = account;
		this.password = password;
		this.nickname = nickname;
		this.phone = phone;
	}
	
}
