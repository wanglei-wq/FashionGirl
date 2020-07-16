package cn.kgc.tangcco.zwpl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryInformation {
	
	/**
	 * 用户收货地址所对应的ID
	 */
	private int id;
	
	/**
	 * 当前用户的id
	 */
	private int user_id;
	
	/**
	 * 收货人姓名
	 */
	private String nickname;
	
	/**
	 * 收货人电话
	 */
	private String phone;
	
	/**
	 * 收货人地址
	 */
	private String address;

	public DeliveryInformation(int user_id, String nickname, String phone, String address) {
		super();
		this.user_id = user_id;
		this.nickname = nickname;
		this.phone = phone;
		this.address = address;
	}

	
	public DeliveryInformation(String nickname, String phone, String address,int id) {
		super();
		this.nickname = nickname;
		this.phone = phone;
		this.address = address;
		this.id = id;
	}


	
}
