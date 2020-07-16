package cn.kgc.tangcco.zwpl.common.alipay;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {
	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016101200670650";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key ="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC/4/+a4hegUF2ST4/1SnPsDoLvdI7u+8BH03guhgU21VxRfmrZ+ULmJ2qwGG7oyOz7r0+u6ipEkh8nmswnU3GsBocPcbGuMXqRSKLeuQYcB+ArZ9wwhZkmUv8Ta11iOfC5xFBP6LZuhSa9tZ5bXFanIOe5UX14zG2JOe7P5V7h3sOYSSYEp+Aaao6dTeB+ZTfVbWnzToLJczRSJAwdNgl/cG7Q7IH4N8QnOnDqtuXYv17tEm4xqsgRwQeR44XzbvNeH//MJn2iu6YqmcU5RKJ6/4/WGcYA9f1Bcwhqe37p256+SG3qjuAbpNUvVWZKvAA9ZqmWSq50Vbco7ddqG7VbAgMBAAECggEAK4V6o5ZD5YhUsKp67BdG03M3HzhActOGeMPtrh+XbyhVF+akNWvjUHAAMpBYF1P669qHKBTUIVlC16i0RPcYAPw6Bbazpd5y/iCLUBiG2jSvEykrN5YVdiu8zpq7Q1wAzSFJVdZiCg8JCl323KHExYMhC1cALjWvYkBRebV8H54SDuooNremdHjpjVkaIoWWFvvaKGQJC/c0vqiUrDjidO17qRnJaGtCa014Muoeg4j+tRkMbB0HzrT4nIHSJ7dv1/6JQt+qhWQRXByYZXFQhzrefBXkXmYMcNq9VP7UHFg1BiVLDDd6xKL6y6mcMFc85gtD5qcr/k3KpE0e0xzCgQKBgQDibM4FM/ldLrO7aKqnkUAuutjhuwwYS3ZphqT07PRCOqegqhZK2Yzvqe3D0qn7MvJ52I0w4OS1hYF5P24M3r7EDzIRR3Z5foagr31xgpqcUpWTMe57PdQxaFh4BaZj7p2I9NLHUhWOWMMHMZIYMTePoV+Zkd2a33JdEqCMKJ5R/QKBgQDY9G7ha403e5Hu7gwVdC0neZ3htJIHkHIrsV0jgZc58QN9ksO1kPJKUXyQbbYWx0o9BK3/OXakZSxqeSJGfPZS3bM297hT992NDkwynFF2/g5mnTRgu8m0Tl7vd+mdrrLmsZVR+JpEU1EkisbAkDoVuEUrQ+QTNjNjJQx7ecf4NwKBgDQ3t4OHqviMbrLHvqOKYezk9mOIiY+Ix7QqcxxxERu8/y+USjPU3CXhYI4KpVzjMPxcrqMZGmWqdmOJ901MiV4ZywglHPOD9HRWRG3LGvD2MryRhVgyuB7024JCe08MlpQhMgL5sTL00XV1PyYJI7EiHDQEacDvyyYfx5rLO0fZAoGACFKIFlmb1y8EUBgNmp0URllh9xCIMYYW+xoYqo4EERLvwVdp7oMa6ELr9lZfD8yhSmntisbXiELhS0xORknw4QjkIQ2ronz1oL2VqrNYZKomP0V0hultOV9wQNtmuMfE0Riq07QnpstTZ4VzTlu5/q9M5/X4v8vqeLkpG2t2gk0CgYBc+XXWxX8XfpQ19VRVCD7xgdbh80YPGA9fZ+9gRTxluyZY8BF4ZhV21QtR5lRPbeb6QY6a8nTXEWkW7t9iBFKGNDL4kyu6A/73sc75C12lrs8pzt2605E5NCw3R1lprLPXsgP2Fh7OG7WwJCPYKwLbasE69pKmuzSyNI9iN0eHRw==";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxWXlpIy6WzhSn7d5fegfUw6fDcWyL4qQUiT59XrxWJNLLPNkK665jPslU3X35G+BpbTMmHp8hOSMh17oj7AxFvBDStzO8NJ6qfH7LlyFqDBU6LPnt+dV+YBUA5wTyDJp0qWJ3gFgOlkPvp8UZ+MnHu7jubFNBo9lj25/if4iFnOg+ANCSfDVuhVhoqq4xrTXd7qRvd81zEEzeocbFt67KidSK4+M92zWb+TdLp/UfjsjQ7APIe2NMgpP0SXggWi80sM/y0SRi3mKVRSWu+luVn41sX/y8t/ZxXdMxq3IcPEk/8AFWLbGgUIatyIXZ5ondWQbxRUlVHV5eYrednPeVQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://localhost:8080/FashionGirl/user.action?methodName=callBack";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:8080/FashionGirl/user.action?methodName=callBack";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	//https://openapi.alipaydev.com/gateway.do
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

