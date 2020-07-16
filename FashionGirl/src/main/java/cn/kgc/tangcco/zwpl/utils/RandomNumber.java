package cn.kgc.tangcco.zwpl.utils;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class RandomNumber {
	/**
	 * 短信验证码随机数
	 * @return
	 */
	public static String smsRandom() {
		Random random = new Random();
		StringBuffer buffer= new  StringBuffer();
		buffer.append(random.nextInt(899999)+100000);
		return buffer.toString();
	}
	/**
	 * 人脸识别唯一标识符
	 * 
	 * @return 唯一标识符
	 */
	public static String faceRandom() {
		return Integer.toHexString(ThreadLocalRandom.current().nextInt(11111111, 99999999))
				+ UUID.randomUUID().toString().replaceAll("-", "")
				+ Integer.toHexString(ThreadLocalRandom.current().nextInt(11111111, 99999999));
	}
	/**
	 * 生成12位订单号
	 */
	public static String orderRandom() {
		//随机生成一位整数
        int random = (int) (Math.random()*9+1);
        String valueOf = String.valueOf(random);
        //生成uuid的hashCode值
        int hashCode = UUID.randomUUID().toString().hashCode();
        //可能为负数
        if(hashCode<0){
            hashCode = -hashCode;
        }
        String value = valueOf + String.format("%015d", hashCode);
        return value;
	}
}
