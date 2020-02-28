package com.sky.service.api.struct.transaction;

import com.sky.base.serialize.xml.XmlUtils;
import com.sky.service.api.struct.domain.SkyMsg;
import com.sky.service.api.struct.domain.SkyMsgHead;
import com.sky.service.api.struct.transaction.a2010.TransA20101;

import java.util.ArrayList;
import java.util.List;

public class TransA20101Test {

    public static void main(String[] args) {

        TransA20101 t = new TransA20101();
        t.setBankId("1235454");
        t.setBankDate("20190822");
        t.setBankMid("ahfkjashfsdf75");
        t.setIdnId("2316546");
        t.setIdnName("87445");
        t.setExtPayerCode("kjashdf28490");
        t.setBeginDate("20190612");
        t.setEndDate("20190623");
        t.setTaxType("0156");

        SkyMsg msg = new SkyMsg();
        SkyMsgHead head = new SkyMsgHead();
        head.setSrc("TAX");
        head.setDest("ETS");
        head.setOrigSrc("GZTAX");
        head.setMsgType("A20101");
        head.setWorkDate("20190201");
        head.setMsgId("UUID0000001");
        head.setRefMsgType("");
        head.setRefMsgId("");
        head.setSendDateTime("20190202120000");
        head.setMacFlag("");
        head.setRetCode("");
        head.setRetMsg("");

        List<TransA20101> body = new ArrayList<TransA20101>();
        body.add(t);
        msg.setHead(head);
        msg.setBody(body);

        String plainMsg = XmlUtils.toXml(msg);
        System.out.println(plainMsg);

        SkyMsg reSerializeMsg = XmlUtils.fromXml(plainMsg, SkyMsg.class, TransA20101.class);
        System.out.println(XmlUtils.toXml(reSerializeMsg));
    }
}
