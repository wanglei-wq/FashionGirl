package cn.kgc.tangcco.zwpl.common.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SingleBeanFactory {

	private static SingleBeanFactory instance = new SingleBeanFactory();

	private Map<String, Object> map = new ConcurrentHashMap<String, Object>();

	private SingleBeanFactory() {

	}

	/**
	 * @return the instance
	 */
	public static SingleBeanFactory getInstance() {
		return instance;
	}

	public Object newInstance(String beanId, String beanClass) {
		if (map.containsKey(beanId)) {
			return map.get(beanId);
		}
		try {
			Object value = Class.forName(beanClass).getDeclaredConstructor().newInstance();
			map.put(beanId, value);
			return value;
		} catch (ClassNotFoundException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
