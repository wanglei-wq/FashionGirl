package cn.kgc.tangcco.zwpl.pojo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products {
	/**
	 * 商品id
	 */
	private int pid;
	/**
	 * 商品名
	 */
	private String pname;
	/**
	 * 商品价格(市场)
	 */
	private double market_price;
	/**
	 * 商品价格(柜台)
	 */
	private double shop_price;
	/**
	 * 商品图片路径
	 */
	private String image;
	/**
	 * 商品描述
	 */
	private String pdesc;
	/**
	 * 商品更新日期
	 */
	private Date pdate;
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
	
	public Products(String pname, double market_price, double shop_price) {
		super();
		this.pname = pname;
		this.market_price = market_price;
		this.shop_price = shop_price;
	}

	public Products(int pid, String pname, double market_price, double shop_price, String image, String pdesc) {
		super();
		this.pid = pid;
		this.pname = pname;
		this.market_price = market_price;
		this.shop_price = shop_price;
		this.image = image;
		this.pdesc = pdesc;
	}
}
