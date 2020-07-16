package cn.kgc.tangcco.zwpl.common.spring;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import cn.kgc.tangcco.zwpl.common.factory.MultipleBeanFactory;
import cn.kgc.tangcco.zwpl.common.factory.SingleBeanFactory;
import cn.kgc.tangcco.zwpl.common.xml.BaseXML;


public class ClassPathXmlApplicationContext extends BaseXML {
	/**
	 * 默认配置文件
	 */
	private static final String applicationContextConfig = "ApplicationContext.xml";
	/**
	 * 自定义配置文件类路径位置
	 */
	private String config;
	/**
	 * 是否单利模式初始值
	 */
	private boolean multiple;

	/**
	 * 读取默认配置文件，默认为单例模式
	 */
	public ClassPathXmlApplicationContext() {
		this(applicationContextConfig);
	}

	/**
	 * 读取自定义类路径下的配置文件，默认为单例模式
	 * 
	 * @param config 自定义类路径下的配置文件
	 */
	public ClassPathXmlApplicationContext(String config) {
		this.config = config;
	}

	/**
	 * 读取默认配置文件，是否为单例模式可配置
	 * 
	 * @param multiple true为多例模式 false为单例模式
	 */
	public ClassPathXmlApplicationContext(boolean multiple) {
		this(applicationContextConfig, multiple);
	}

	/**
	 * 读取自定义类路径下的配置文件，是否为单例模式可配置
	 * 
	 * @param config   自定义类路径下的配置文件
	 * @param multiple true为多例模式 false为单例模式
	 */
	public ClassPathXmlApplicationContext(String config, boolean multiple) {
		this.config = config;
		this.multiple = multiple;
	}

	/**
	 * 
	 * @param beanId
	 * @return 返回单例对象
	 * @throws Exception
	 */
	public Object getBean(String beanId) throws Exception {
		// 1. 读取配置文件
		List<Element> elements = readerXml();
		if (elements == null) {
			throw new Exception("该配置文件没有子元素");
		}
		// 2. 使用beanId查找对应的class地址
		String beanClass = findXmlByIDClass(elements, beanId);
		if (StringUtils.isEmpty(beanClass)) {
			throw new Exception("未找到对应的class地址");
		}
		// 3. 使用反射机制初始化，对象
		// multiple为true实例化对象为多例，
		// multiple为false实例化对象为单例，默认为false
		if (multiple) {
			return MultipleBeanFactory.getInstance().newInstance(beanClass);
		}
		return SingleBeanFactory.getInstance().newInstance(beanId, beanClass);
	}
	/**
	 * 读取配置文件信息
	 * 
	 * @return
	 * @throws DocumentException
	 */
	public List<Element> readerXml() throws DocumentException {
		if (StringUtils.isEmpty(config)) {
			new Exception("xml路径为空...");
		}
		// 获取根节点信息
		Element rootElement = getRootElement(config);
		// 获取子节点
		List<Element> elements = rootElement.elements();
		if (elements == null || elements.isEmpty()) {
			return null;
		}
		return elements;
	}

	/**
	 * 使用beanid查找该Class地址
	 * 
	 * @param elements
	 * @param beanId
	 * @return
	 * @throws Exception
	 */
	public String findXmlByIDClass(List<Element> elements, String beanId) throws Exception {
		for (Element element : elements) {
			// 读取节点上是否有value
			String beanIdValue = element.attributeValue("id");
			if (beanIdValue == null) {
				throw new Exception("使用该beanId为查找到元素");
			}
			if (!beanIdValue.equals(beanId)) {
				continue;
			}
			// 获取Class地址属性
			String classPath = element.attributeValue("class");
			if (!StringUtils.isEmpty(classPath)) {
				return classPath;
			}
		}
		return null;
	}

}
