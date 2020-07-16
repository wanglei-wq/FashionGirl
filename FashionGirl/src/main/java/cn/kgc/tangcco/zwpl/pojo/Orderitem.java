package cn.kgc.tangcco.zwpl.pojo;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orderitem {
	/**
	 * 订单项id
	 */
	private int itemid;
	/**
	 * 订单id
	 */
	private String order_id;
	/**
	 * 商品id
	 */
	private int p_pid;
	/**
	 * 订单项商品数量
	 */
	private int count;
	/**
	 * 订单项小计
	 */
	private BigDecimal subtotal;
	//************************以下多添了一个字段,方便起见
	/**
	 * 商品名称
	 */
	private String pname;
	//添加订单项的构造方法
	public Orderitem(String order_id, int p_pid, BigDecimal subtotal, int count) {
		super();
		this.order_id = order_id;
		this.p_pid = p_pid;
		this.subtotal = subtotal;
		this.count = count;
	}
}
