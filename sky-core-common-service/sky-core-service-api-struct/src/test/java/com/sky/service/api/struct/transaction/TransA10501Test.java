package com.sky.service.api.struct.transaction;

import com.sky.base.serialize.xml.XmlUtils;
import com.sky.service.api.struct.domain.SkyMsg;
import com.sky.service.api.struct.domain.SkyMsgHead;
import com.sky.service.api.struct.transaction.a1050.TransA10501;

import java.util.ArrayList;
import java.util.List;

public class TransA10501Test {

    public static void main(String[] args) {

        TransA10501 t = new TransA10501();
        t.setOrgId("ORG1989");
        t.setOrgDate("20190624");
        t.setOrgMid("123");
        t.setBankId("123456");
        t.setTranType("9114");
        t.setRealOrgId("Tx001");
        t.setSignDate("20190628");
        t.setChkCnlSeq("111222369852");
        t.setChkCnlFlag("0");
        t.setSignOrgId("123654789111");
        t.setPayerCode("CS001");
        t.setPayerName("test");
        t.setPayBankId("6213988544");
        t.setSignBankId("362427199487");
        t.setPayAccNo("123356654646");
        t.setPayAccName("缴款单位");
        t.setDocumenNo("DO9545313131");

        SkyMsg msg = new SkyMsg();
        SkyMsgHead head = new SkyMsgHead();
        head.setSrc("TAX");
        head.setDest("ETS");
        head.setOrigSrc("GZTAX");
        head.setMsgType("A10501");
        head.setWorkDate("20190201");
        head.setMsgId("UUID0000001");
        head.setRefMsgType("");
        head.setRefMsgId("");
        head.setSendDateTime("20190202120000");
        head.setMacFlag("");
        head.setRetCode("");
        head.setRetMsg("");

        List<TransA10501> body = new ArrayList<TransA10501>();
        body.add(t);
        msg.setHead(head);
        msg.setBody(body);

        String plainMsg = XmlUtils.toXml(msg);
        System.out.println(plainMsg);

        SkyMsg reSerializeMsg = XmlUtils.fromXml(plainMsg, SkyMsg.class, TransA10501.class);
        System.out.println(XmlUtils.toXml(reSerializeMsg));
    }
}
