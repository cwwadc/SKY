package com.sky.service.api.struct.transaction;

import com.sky.base.serialize.xml.XmlUtils;
import com.sky.service.api.struct.domain.SkyMsg;
import com.sky.service.api.struct.domain.SkyMsgHead;
import com.sky.service.api.struct.transaction.a1040.TransA10402;

import java.util.ArrayList;
import java.util.List;

public class TransA10402Test {
	public static void main(String[] args) {

		TransA10402 t = new TransA10402();
		t.setOrgId("ORG1989");
		t.setOrgDate("20190624");
		t.setOrgMid("123");
		t.setTxnType("1");
		t.setRefOrgDate("20190621");
		t.setRefOrgMid("ORGMID001");
		t.setTxnDate("20190629");
		t.setAmount("111");

		SkyMsg msg = new SkyMsg();
		SkyMsgHead head = new SkyMsgHead();
		head.setSrc("TAX");
		head.setDest("ETS");
		head.setOrigSrc("GZTAX");
		head.setMsgType("A10402");
		head.setWorkDate("20190624");
		head.setMsgId("UUID00000201");
		head.setRefMsgType("");
		head.setRefMsgId("");
		head.setSendDateTime("20190624120000");
		head.setMacFlag("");
		head.setRetCode("");
		head.setRetMsg("");

		
		List<TransA10402> body = new ArrayList<TransA10402>();
		body.add(t);
		msg.setHead(head);
		msg.setBody(body);
		
		String plainMsg = XmlUtils.toXml(msg);
		System.out.println(plainMsg);
		
		SkyMsg reSerializeMsg = XmlUtils.fromXml(plainMsg, SkyMsg.class, TransA10402.class);
		System.out.println(XmlUtils.toXml(reSerializeMsg));
	}
}
