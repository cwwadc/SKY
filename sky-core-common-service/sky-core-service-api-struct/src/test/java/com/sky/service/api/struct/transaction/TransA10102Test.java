package com.sky.service.api.struct.transaction;

import com.sky.base.serialize.xml.XmlUtils;
import com.sky.base.serialize.xml.strategy.DefaultXstreamStrategy;
import com.sky.service.api.struct.domain.SkyMsg;
import com.sky.service.api.struct.domain.SkyMsgHead;
import com.sky.service.api.struct.transaction.a1010.*;
import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.List;

public class TransA10102Test {
	public static void main(String[] args) {

		TransA10102 t = new TransA10102();
		t.setBankId("1");
		t.setBankDate("20190624");
		t.setOrgId("ORG1989");
		t.setOrgDate("20190624");
		t.setOrgMid("123");
		t.setTxnType("1");
		t.setPayDate("20190621");
		t.setTxnRound("20190629");
		t.setTxnDate("20190629");
		t.setAmount("111");

		SkyMsg msg = new SkyMsg();
		SkyMsgHead head = new SkyMsgHead();
		head.setSrc("TAX");
		head.setDest("ETS");
		head.setOrigSrc("GZTAX");
		head.setMsgType("A10102");
		head.setWorkDate("20190624");
		head.setMsgId("UUID00000201");
		head.setRefMsgType("");
		head.setRefMsgId("");
		head.setSendDateTime("20190624120000");
		head.setMacFlag("");
		head.setRetCode("");
		head.setRetMsg("");

		
		List<TransA10102> body = new ArrayList<TransA10102>();
		body.add(t);
		msg.setHead(head);
		msg.setBody(body);
		
		XStream xStream = XmlUtils.getXStream(new DefaultXstreamStrategy());
        xStream.processAnnotations(SkyMsg.class);
        xStream.processAnnotations(TransA10102.class);
        
        System.out.println(xStream.toXML(msg));
		
//		String plainMsg = XmlUtils.toXml(msg);
//		System.out.println(plainMsg);
		
//		SkyMsg reSerializeMsg = XmlUtils.fromXml(plainMsg, SkyMsg.class, TransA10102.class);
//		System.out.println(XmlUtils.toXml(reSerializeMsg));
	}
}
