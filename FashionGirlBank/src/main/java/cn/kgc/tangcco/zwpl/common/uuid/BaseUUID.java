package cn.kgc.tangcco.zwpl.common.uuid;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public abstract class BaseUUID {
	/**
	 * 生成唯一标识符
	 * 
	 * @return 唯一标识符
	 */
	public static String random() {
		return Integer.toHexString(ThreadLocalRandom.current().nextInt(11111111, 99999999))
				+ UUID.randomUUID().toString().replaceAll("-", "")
				+ Integer.toHexString(ThreadLocalRandom.current().nextInt(11111111, 99999999));
	}
}
