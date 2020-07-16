package cn.kgc.tangcco.zwpl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 吴成卓
 * @version 1.0
 * 创建时间：	2019年8月27日 下午8:03:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserALP {
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 姓名
	 */
	private String nickname;
	/**
	 * 金额
	 */
	private String money;
}
