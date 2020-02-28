package com.sky.base.serialize.xml.driver;

import static org.junit.Assert.assertEquals;

import com.sky.base.serialize.xml.AbstractXmlTest;
import com.sky.base.serialize.xml.MockAnnotationsObject;
import com.sky.base.serialize.xml.driver.ConfigurableXppDriver;
import com.sky.base.serialize.xml.factory.DefaultXstreamFactoryImplTest;
import com.sky.base.serialize.xml.factory.XstreamFactory;
import com.sky.base.serialize.xml.strategy.DefaultXstreamStrategy;
import com.thoughtworks.xstream.XStream;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2018-12-12
 */
public abstract class AbstractXppDriverTest extends AbstractXmlTest {

	protected ConfigurableXppDriver driver;

	protected XStream getXStream(ConfigurableXppDriver driver) {
		XstreamFactory xStreamFactory = DefaultXstreamFactoryImplTest.getDefaultXStreamFactory();
		return xStreamFactory.getInstance(new DefaultXstreamStrategy(driver));
    }

	protected void assertXmlFormat(XStream xStream, MockAnnotationsObject object, String expectedXml) {
		String xml = xStream.toXML(object);
		assertEquals(expectedXml, xml);
	}

}
