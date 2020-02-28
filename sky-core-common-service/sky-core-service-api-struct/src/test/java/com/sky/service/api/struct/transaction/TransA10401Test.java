package com.sky.service.api.struct.transaction;

import com.sky.base.serialize.xml.XmlUtils;
import com.sky.service.api.struct.domain.SkyMsg;
import com.sky.service.api.struct.domain.SkyMsgHead;
import com.sky.service.api.struct.transaction.a1040.TransA10401;

import java.util.ArrayList;
import java.util.List;

public class TransA10401Test {
	public static void main(String[] args) {

		TransA10401 t = new TransA10401();
		t.setOrgId("ORG1989");
		t.setOrgDate("20190131");
		t.setOrgMid("ORGMID001");
		t.setFiscId("A00001");
		t.setTxnType("1");
		t.setRefOrgDate("20190624");
		t.setRefOrgMid("111");
		t.setGetAccNo("3624271998685424");
		t.setPayAccNo("6224556797655678");
		t.setAmount("12396");
		t.setPayType("EX");

		SkyMsg msg = new SkyMsg();
		SkyMsgHead head = new SkyMsgHead();
		head.setSrc("TAX");
		head.setDest("ETS");
		head.setOrigSrc("GZTAX");
		head.setMsgType("A10401");
		head.setWorkDate("20190201");
		head.setMsgId("UUID0000001");
		head.setRefMsgType("");
		head.setRefMsgId("");
		head.setSendDateTime("20190202120000");
		head.setMacFlag("");
		head.setRetCode("");
		head.setRetMsg("");
		
		List<TransA10401> body = new ArrayList<TransA10401>();
		body.add(t);
		msg.setHead(head);
		msg.setBody(body);
		
		String plainMsg = XmlUtils.toXml(msg);
		System.out.println(plainMsg);
		
		SkyMsg reSerializeMsg = XmlUtils.fromXml(plainMsg, SkyMsg.class, TransA10401.class);
		System.out.println(XmlUtils.toXml(reSerializeMsg));
	}
}
