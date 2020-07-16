package cn.kgc.tangcco.zwpl.common.factory;

import java.lang.reflect.InvocationTargetException;

public class MultipleBeanFactory {
	private static MultipleBeanFactory instance = new MultipleBeanFactory();

	private MultipleBeanFactory() {

	}

	/**
	 * @return the instance
	 */
	public static MultipleBeanFactory getInstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	public <T> T newInstance(String className) {
		try {
			return (T) Class.forName(className).getDeclaredConstructor().newInstance();
		} catch (ClassNotFoundException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
}
