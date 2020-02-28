package com.sky.service.api.struct.transaction;

import com.sky.base.serialize.xml.XmlUtils;
import com.sky.service.api.struct.domain.SkyMsg;
import com.sky.service.api.struct.domain.SkyMsgHead;
import com.sky.service.api.struct.transaction.a1030.TransA10302;

import java.util.ArrayList;
import java.util.List;

public class TransA10302Test {
	public static void main(String[] args) {

		TransA10302 t = new TransA10302();
		t.setOrgId("ORG1989");
		t.setOrgDate("20190624");
		t.setOrgMid("123");
		t.setTxnType("1");
		t.setRefOrgMid("123");
		t.setAmount("111");

		SkyMsg msg = new SkyMsg();
		SkyMsgHead head = new SkyMsgHead();
		head.setSrc("TAX");
		head.setDest("ETS");
		head.setOrigSrc("GZTAX");
		head.setMsgType("A10302");
		head.setWorkDate("20190624");
		head.setMsgId("UUID00000201");
		head.setRefMsgType("");
		head.setRefMsgId("");
		head.setSendDateTime("20190624120000");
		head.setMacFlag("");
		head.setRetCode("");
		head.setRetMsg("");

		
		List<TransA10302> body = new ArrayList<TransA10302>();
		body.add(t);
		msg.setHead(head);
		msg.setBody(body);
		
		String plainMsg = XmlUtils.toXml(msg);
		System.out.println(plainMsg);
		
		SkyMsg reSerializeMsg = XmlUtils.fromXml(plainMsg, SkyMsg.class, TransA10302.class);
		System.out.println(XmlUtils.toXml(reSerializeMsg));
	}
}
