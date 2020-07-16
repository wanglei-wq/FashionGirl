package cn.kgc.tangcco.zwpl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PWVO {
	/**
	 * 商品id
	 */
	private int pid;
	/**
	 * 商品名
	 */
	private String pname;
	/**
	 * 商品价格(柜台)
	 */
	private double shop_price;
	/**
	 * 商品图片路径
	 */
	private String image;
	/**
	 * 商品数量
	 */
	private int pnum;
	/**
	 * 第一分类id
	 */
	private int cid;
	/**
	 * 第二分类id
	 */
	private int csid;
	/**
	 * 是否热卖
	 */
	private int type;
	/**
	 *心愿单编号 
	 */
	private int wid;
	/**
	 *用户id 
	 */
	private int id;
	public PWVO(int pid, int id) {
		super();
		this.pid = pid;
		this.id = id;
	}
}
