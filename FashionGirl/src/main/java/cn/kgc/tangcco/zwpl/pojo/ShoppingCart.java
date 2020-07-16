package cn.kgc.tangcco.zwpl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {
/**
 *购物车编号 
 */
	private int sid;
	/**
	 *商品编号 
	 */
	private int pid;
	/**
	 *商品名称 
	 */
	private String pname;
	/**
	 *商品图片
	 */
	private String image;
	/**
	 * 商品价格(柜台)
	 */
	private double shop_price;
	/**
	 *用户编号 
	 */
	private int userid;
	/**
	 *商品数量 
	 */
	private int snum;
	/**
	 * 购物商品的状态
	 */
	private int state;
	/**
	 *商品总计 
	 */
	private double total;

	public ShoppingCart(int sid, int snum) {
		super();
		this.sid = sid;
		this.snum = snum;
	}
	//添加购物车的构造方法
	public ShoppingCart(int pid, int userid, int snum, double total) {
		super();
		this.pid = pid;
		this.userid = userid;
		this.snum = snum;
		this.total = total;
	}
	//修改商品数量的构造方法
	public ShoppingCart(int sid, int snum, double total) {
		super();
		this.sid = sid;
		this.snum = snum;
		this.total = total;
	}
}
