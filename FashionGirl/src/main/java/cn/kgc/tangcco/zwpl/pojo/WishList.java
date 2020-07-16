package cn.kgc.tangcco.zwpl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishList {
	/**
	 *心愿单编号 
	 */
	private int wid;
	/**
	 *商品id 
	 */
	private int pid;
	/**
	 *用户id 
	 */
	private int userid;
	public WishList(int pid, int userid) {
		super();
		this.pid = pid;
		this.userid = userid;
	}
	
}
