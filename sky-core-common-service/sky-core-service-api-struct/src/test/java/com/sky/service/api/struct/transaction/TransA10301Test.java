package com.sky.service.api.struct.transaction;

import com.sky.base.serialize.xml.XmlUtils;
import com.sky.service.api.struct.domain.SkyMsg;
import com.sky.service.api.struct.domain.SkyMsgHead;
import com.sky.service.api.struct.transaction.a1030.TaxInfo;
import com.sky.service.api.struct.transaction.a1030.TaxSubject;
import com.sky.service.api.struct.transaction.a1030.TaxType;
import com.sky.service.api.struct.transaction.a1030.TransA10301;

import java.util.ArrayList;
import java.util.List;

public class TransA10301Test {
	public static void main(String[] args) {
		TaxSubject tsj = new TaxSubject();
		tsj.setSeqNo("sj12312");
		tsj.setTaxSubjectCode("sjtsccode");
		tsj.setTaxSubjectName("sj税种名称");
		tsj.setBudgetType("2");
		tsj.setAdjustFlag("0");
		tsj.setTaxType("A");
		tsj.setDescFisc("描述内容");
		tsj.setOrgVicesign("AA9987");
		tsj.setTaxPropCode("098y");
		tsj.setBlevel("8");
		tsj.setVicesign("BB9987");
		tsj.setTaxSubjectAmt("12396");
		
		TaxSubject tsj1 = new TaxSubject();
		tsj1.setSeqNo("sj12312");
		tsj1.setTaxSubjectCode("sjtsccode");
		tsj1.setTaxSubjectName("sj税种名称");
		tsj1.setBudgetType("2");
		tsj1.setAdjustFlag("0");
		tsj1.setTaxType("A");
		tsj1.setDescFisc("描述内容");
		tsj1.setOrgVicesign("AA9987");
		tsj1.setTaxPropCode("098y");
		tsj1.setBlevel("8");
		tsj1.setVicesign("BB9987");
		tsj1.setTaxSubjectAmt("12396");
		
		List<TaxSubject> tsjList = new ArrayList<TaxSubject>();
		tsjList.add(tsj);
		tsjList.add(tsj1);
		
		TaxType tt = new TaxType();
		tt.setSeqNo("tt11111");
		tt.setTaxCode("tttaxcode");
		tt.setTaxName("tttaxname");
		tt.setItem("商品1234");
		tt.setTaxTypeAmt("12398");
		tt.setTaxStartDate("20190101");
		tt.setTaxEndDate("20190131");
		tt.setDetailCnt("12396");
		tt.setTaxSubjectList(tsjList);
		
		TaxType tt1 = new TaxType();
		tt1.setSeqNo("tt11111");
		tt1.setTaxCode("tttaxcode");
		tt1.setTaxName("tttaxname");
		tt1.setItem("商品1234");
		tt1.setTaxTypeAmt("12398");
		tt1.setTaxStartDate("20190101");
		tt1.setTaxEndDate("20190131");
		tt1.setDetailCnt("12396");
		tt1.setTaxSubjectList(tsjList);
		
		List<TaxType> ttList = new ArrayList<TaxType>();
		ttList.add(tt);
		ttList.add(tt1);
		
		TaxInfo ti = new TaxInfo();
		
		ti.setPrintFlag("A");
		ti.setPayerName("张三");
		ti.setPayerCode("PAYER10000");
		ti.setCorpRegCode("ALLINPAY0001");
		ti.setDocumentNo("4415221990080776554");
		ti.setBillDate("20190101");
		ti.setLimitDate("20190131");
		ti.setCommentary("备注内容HELLO");
		ti.setExtOrgId("12312312");
		ti.setExtPayerCode("EXPAYER10000");
		ti.setTxnTypeCnt("12396");
		ti.setTaxTypeList(ttList);
		
		TransA10301 t = new TransA10301();
		t.setOrgId("ORG1989");
		t.setOrgDate("20190131");
		t.setOrgMid("ORGMID001");
		t.setFiscId("A00001");
		t.setTxnType("1");
		t.setRefOrgMid("111");
		t.setGetAccNo("6225882018199765");
		t.setPayAccNo("6224556797655678");
		t.setAmount("12396");
		t.setPayType("EX");
		t.setTaxType("AE");
		t.setTaxInfo(ti);
		
		SkyMsg msg = new SkyMsg();
		SkyMsgHead head = new SkyMsgHead();
		head.setSrc("TAX");
		head.setDest("ETS");
		head.setOrigSrc("GZTAX");
		head.setMsgType("A10301");
		head.setWorkDate("20190201");
		head.setMsgId("UUID0000001");
		head.setRefMsgType("");
		head.setRefMsgId("");
		head.setSendDateTime("20190202120000");
		head.setMacFlag("");
		head.setRetCode("");
		head.setRetMsg("");
		
		List<TransA10301> body = new ArrayList<TransA10301>();
		body.add(t);
		msg.setHead(head);
		msg.setBody(body);
		
		String plainMsg = XmlUtils.toXml(msg);
		System.out.println(plainMsg);
		
		SkyMsg reSerializeMsg = XmlUtils.fromXml(plainMsg, SkyMsg.class, TransA10301.class);
		System.out.println(XmlUtils.toXml(reSerializeMsg));
	}
}
