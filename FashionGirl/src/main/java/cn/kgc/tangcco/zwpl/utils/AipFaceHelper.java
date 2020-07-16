package cn.kgc.tangcco.zwpl.utils;

import com.baidu.aip.face.AipFace;

public class AipFaceHelper {
	// 设置APPID/AK/SK
	private static final String APP_ID = "17295999";
	private static final String API_KEY = "at8hyTE8yYIcI6sNecYrx8Vd";
	private static final String SECRET_KEY = "4ntUhiaGP7uaA6ycmwhdUkjC7QOEMG5f";
	private static AipFace client = null;

	private AipFaceHelper() {
	}

	public static AipFace getInstance() {
		if (client == null) {
			client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
			//设置网络连接参数
			client.setConnectionTimeoutInMillis(3000);
			client.setSocketTimeoutInMillis(60000);
		}
		return client;
	}

}
