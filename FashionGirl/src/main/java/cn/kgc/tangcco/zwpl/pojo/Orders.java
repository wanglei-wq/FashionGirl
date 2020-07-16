package cn.kgc.tangcco.zwpl.pojo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
	/**
	 * 订单id(主键)
	 */
	private int oid;
	/**
	 * 订单id(主键)
	 */
	private String order_id;
	/**
	 * 订单用户名
	 */
	private String user_name;
	/**
	 * 订单用户手机号
	 */
	private String user_phone;
	/**
	 * 订单总价
	 */
	private double money;
	/**
	 * 订单地址
	 */
	private String address;
	/**
	 * 下订单时间
	 */
	private Date ordertime;
	/**
	 * 订单支付状态
	 */
	private int state;
	/**
	 * 订单用户id
	 */
	private int user_id;
	
	//购物车生成订单的方法
	public Orders(String order_id, double money, Date ordertime, int state, int user_id) {
		super();
		this.order_id = order_id;
		this.money = money;
		this.ordertime = ordertime;
		this.state = state;
		this.user_id = user_id;
	}
	//支付的时候把地址、收货人、电话修改！
	public Orders(String order_id, String user_name, String user_phone, String address) {
		super();
		this.order_id = order_id;
		this.user_name = user_name;
		this.user_phone = user_phone;
		this.address = address;
	}
	
}
