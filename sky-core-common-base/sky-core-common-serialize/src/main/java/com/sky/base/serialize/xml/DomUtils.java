package com.sky.base.serialize.xml;

import java.io.StringReader;
import java.util.ServiceLoader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sky.base.lang.string.StringUtils;

public class DomUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(DomUtils.class);
	static {
		try {
			Class.forName("org.dom4j.io.SAXReader", true, Thread.currentThread().getContextClassLoader());
			ServiceLoader.load(SAXParserFactory.class);
		} catch (Exception e) {
			LOGGER.error("DomUtils initialize error", e);
		}
		
	}
	
	public static String lookupSingleNode(String encoding, String plainXmlText, String xpath) throws DocumentException, SAXException, ParserConfigurationException {
		if(StringUtils.isEmpty(plainXmlText) || StringUtils.isEmpty(xpath)) {
			throw new NullPointerException("parameter plainXmlText or xpath cannot be null");
		}
//		Document doc = null;
//		if(encoding == null) {
//			doc = DocumentHelper.parseText(plainXmlText);
//		} else {
//			SAXReader reader = new SAXReader();
//	        InputSource source = new InputSource(new StringReader(plainXmlText));
//	        source.setEncoding(encoding);
//	        doc = reader.read(source);
//	        if (doc.getXMLEncoding() == null) {
//	        	doc.setXMLEncoding(encoding);
//	        }
//		}
		SAXParserFactory factory = new org.apache.xerces.jaxp.SAXParserFactoryImpl();
		factory.setValidating(false);
		factory.setNamespaceAware(false);
		SAXReader reader = new SAXReader();
		reader.setXMLReader(factory.newSAXParser().getXMLReader());
		Document document = reader.read(new InputSource(new StringReader(plainXmlText)));
		Node node = document.selectSingleNode(xpath);
		return node == null ? null : node.getText();
	}

}
