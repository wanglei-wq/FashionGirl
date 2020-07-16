package cn.kgc.tangcco.zwpl.common.alipay;
/**
 * @author 吴成卓
 * @version 1.0
 * 创建时间：	2019年9月18日 上午9:26:35
 */
public class IDUtils {
	private static byte[] lock = new byte[0];
	 
	// 位数，默认是8位
	private final static long w = 100000000;
 
	public static String createID() {
		long r = 0;
		synchronized (lock) {
			r = (long) ((Math.random() + 1) * w);
		}
 
		return System.currentTimeMillis() + String.valueOf(r).substring(1);
	}
	
}
