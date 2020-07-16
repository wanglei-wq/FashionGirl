package cn.kgc.tangcco.zwpl.pojo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
	/**
	 *订单编号 
	 */
	private int oid;
	/**
	 *订单id 
	 */
	private String order_id;
	/**
	 *用户姓名 
	 */
	private String user_name;
	/**
	 *用户电话 
	 */
	private String user_phone;
	/**
	 *支付金额 
	 */
	private double money;
	/**
	 *用户地址 
	 */
	private String address;
	/**
	 * 订单时间
	 */
	private Date ordertime;
	/**
	 *订单状态 
	 */
	private int state;
	/**
	 *用户id 
	 */
	private int user_id;
	public Orders(String order_id, int state) {
		super();
		this.order_id = order_id;
		this.state = state;
	}
	public Orders(String order_id, String user_name, String user_phone, double money, String address, Date ordertime,int state) {
		super();
		this.order_id = order_id;
		this.user_name = user_name;
		this.user_phone = user_phone;
		this.money = money;
		this.address = address;
		this.ordertime = ordertime;
		this.state = state;
	}
	
}
