package cn.kgc.tangcco.zwpl.common.xml;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public abstract class BaseXML {

	/**
	 * 读取默认xml文件的名字为ApplicationContext.xml
	 */
	private final static String config = "ApplicationContext.xml";

	/**
	 * 获取类路径下自定义默认的xml文件的InputStream
	 * 
	 * @return InputStream
	 */
	public static InputStream getInputStream() {
		return getInputStream(config);
	}

	/**
	 * 获取类路径下自定义xml文件的InputStream
	 * 
	 * @param config xml文件的名字
	 * @return InputStream
	 */
	public static InputStream getInputStream(String config) {
		return BaseXML.class.getClassLoader().getResourceAsStream(config);
	}

	/**
	 * 获取类路径下默认的xml文件的Document
	 * 
	 * @return 类路径下默认的xml文件的Document
	 * @throws DocumentException
	 */
	public static Document getDocument() throws DocumentException {
		return getDocument(config);
	}

	/**
	 * 获取类路径下自定义的xml文件的Document
	 * 
	 * @return 类路径下默自定义的xml文件的Document
	 * @throws DocumentException DocumentException
	 */
	public static Document getDocument(String config) throws DocumentException {
		return new SAXReader().read(getInputStream(config));
	}

	/**
	 * 获取类路径下默认的xml文件的Element
	 * 
	 * @return 类路径下默认的xml文件的Element
	 * @throws DocumentException DocumentException
	 */
	public static Element getRootElement() throws DocumentException {
		return getRootElement(config);
	}

	/**
	 * 获取类路径下自定义的xml文件的Element
	 * 
	 * @return 类路径下默自定义的xml文件的Element
	 * @throws DocumentException DocumentException
	 */
	public static Element getRootElement(String config) throws DocumentException {
		return getDocument(config).getRootElement();
	}

	public static void getNodes(Element rootElement) {
		System.out.println("获取当前名称:" + rootElement.getName());
		// 获取属性信息
		List<Attribute> attributes = rootElement.attributes();
		for (Attribute attribute : attributes) {
			System.out.println("属性:" + attribute.getName() + "---" + attribute.getText());
		}
		// 获取属性value
		String value = rootElement.getTextTrim();
		if (!StringUtils.isEmpty(value)) {
			System.out.println("value:" + value);
		}
		// 使用迭代器遍历,继续遍历子节点
		Iterator<Element> elementIterator = rootElement.elementIterator();
		while (elementIterator.hasNext()) {
			Element next = elementIterator.next();
			getNodes(next);
		}
	}
}
