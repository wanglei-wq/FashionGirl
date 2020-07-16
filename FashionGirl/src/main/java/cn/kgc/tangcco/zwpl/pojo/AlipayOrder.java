package cn.kgc.tangcco.zwpl.pojo;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlipayOrder {
	/**
	 * 订单id(主键)
	 */
	private int id;
	//订单号
	private String out_trade_no;
	//支付宝交易号
	private String trade_no;
	//付款金额
	private BigDecimal total_amount;
	//绑定用户的信息
	private int user_id;
	public AlipayOrder(String out_trade_no, String trade_no, BigDecimal total_amount, int user_id) {
		super();
		this.out_trade_no = out_trade_no;
		this.trade_no = trade_no;
		this.total_amount = total_amount;
		this.user_id = user_id;
	}
}
