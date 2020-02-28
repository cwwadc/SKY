package com.sky.core.service.api.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.sky.base.serialize.xml.XmlUtils;
import com.sky.core.service.api.contants.ApiConstants;
import com.sky.core.service.api.serialize.XmlBasedApiSerializer;
import com.sky.service.api.struct.domain.SkyMsg;
import com.sky.service.api.struct.transaction.a1010.TransA10101;
import com.sky.service.api.struct.transaction.t1010.TransT10101;

public class SkyApiMsgUtilsTest {
	
	@Test
	public void buildCommonReplyMsgTest() {
		try {
			XmlBasedApiSerializer serializer = new XmlBasedApiSerializer();
			serializer.initialize();
			
			String requestText = "<Message><Head><Src>TAX</Src><Dest>ETS</Dest><OrigSrc>GZTAX</OrigSrc><MsgType>A10101</MsgType><WorkDate>20190201</WorkDate><MsgId>UUID0000001</MsgId><RefMsgType></RefMsgType><RefMsgId></RefMsgId><SendDateTime>20190202120000</SendDateTime><MacFlag></MacFlag><RetCode></RetCode><RetMsg></RetMsg></Head><Body><TxnPayInfo><OrgId>ORG1989</OrgId><OrgDate>20190131</OrgDate><OrgMid>ORGMID001</OrgMid><BankId>441522</BankId><FiscId>A00001</FiscId><TxnType>1</TxnType><GetAccNo>6225882018199765</GetAccNo><PayAccNo>6224556797655678</PayAccNo><Amount>12396</Amount><PayType>EX</PayType><TaxInfo><PrintFlag>A</PrintFlag><PayerName>张三</PayerName><PayerCode>PAYER10000</PayerCode><CorpRegCode>ALLINPAY0001</CorpRegCode><DocumentNo>4415221990080776554</DocumentNo><VisaCheck>1</VisaCheck><BillDate>20190101</BillDate><LimitDate>20190131</LimitDate><Commentary>备注内容HELLO</Commentary><ExtOrgId>12312312</ExtOrgId><ExtPayerCode>EXPAYER10000</ExtPayerCode><TxnTypeCnt>12396</TxnTypeCnt><TaxType><SeqNo>tt11111</SeqNo><TaxCode>tttaxcode</TaxCode><TaxName>tttaxname</TaxName><Item>商品1234</Item><TaxTypeAmt>12398</TaxTypeAmt><TaxStartDate>20190101</TaxStartDate><TaxEndDate>20190131</TaxEndDate><DetailCnt>12396</DetailCnt><TaxSubject><SeqNo>sj12312</SeqNo><TaxSubjectCode>sjtsccode</TaxSubjectCode><TaxSubjectName>sj税种  名称</TaxSubjectName><BudgetType>2</BudgetType><AdjustFlag>0</AdjustFlag><TaxType>A</TaxType><DescFisc>描述内容</DescFisc><OrgVicesign>AA9987</OrgVicesign><TaxPropCode>098y</TaxPropCode><Blevel>8</Blevel><Vicesign>BB9987</Vicesign><TaxSubjectAmt>12396</TaxSubjectAmt></TaxSubject><TaxSubject><SeqNo>sj12312</SeqNo><TaxSubjectCode>sjtsccode</TaxSubjectCode><TaxSubjectName>sj税种名称</TaxSubjectName><BudgetType>2</BudgetType><AdjustFlag>0</AdjustFlag><TaxType>A</TaxType><DescFisc>描述内容</DescFisc><OrgVicesign>AA9987</OrgVicesign><TaxPropCode>098y</TaxPropCode><Blevel>8</Blevel><Vicesign>BB9987</Vicesign><TaxSubjectAmt>12396</TaxSubjectAmt></TaxSubject></TaxType><TaxType><SeqNo>tt11111</SeqNo><TaxCode>tttaxcode</TaxCode><TaxName>tttaxname</TaxName><Item>商品1234</Item><TaxTypeAmt>12398</TaxTypeAmt><TaxStartDate>20190101</TaxStartDate><TaxEndDate>20190131</TaxEndDate><DetailCnt>12396</DetailCnt><TaxSubject><SeqNo>sj12312</SeqNo><TaxSubjectCode>sjtsccode</TaxSubjectCode><TaxSubjectName>sj税种名称</TaxSubjectName><BudgetType>2</BudgetType><AdjustFlag>0</AdjustFlag><TaxType>A</TaxType><DescFisc>描述内容</DescFisc><OrgVicesign>AA9987</OrgVicesign><TaxPropCode>098y</TaxPropCode><Blevel>8</Blevel><Vicesign>BB9987</Vicesign><TaxSubjectAmt>12396</TaxSubjectAmt></TaxSubject><TaxSubject><SeqNo>sj12312</SeqNo><TaxSubjectCode>sjtsccode</TaxSubjectCode><TaxSubjectName>sj税种名称</TaxSubjectName><BudgetType>2</BudgetType><AdjustFlag>0</AdjustFlag><TaxType>A</TaxType><DescFisc>描述内容</DescFisc><OrgVicesign>AA9987</OrgVicesign><TaxPropCode>098y</TaxPropCode><Blevel>8</Blevel><Vicesign>BB9987</Vicesign><TaxSubjectAmt>12396</TaxSubjectAmt></TaxSubject></TaxType></TaxInfo></TxnPayInfo></Body></Message>";
			SkyMsg requestMsg = serializer.deserialize(requestText, TransA10101.class);
			SkyMsg replyMsg = SkyApiMsgUtils.buildReplyMsg(requestMsg, ApiConstants.STATUS_FEEDBACK_TIMEOUT, "channel(ets) return timeout");
			String replyText = serializer.serialize(replyMsg);
			System.out.println("replayMsg -> \n" + replyText);
			assertTrue(replyText.equals("<?xml version=\"1.0\" encoding=\"GBK\"?><Message><Head><Src>TAX</Src><Dest>ETS</Dest><OrigSrc>GZTAX</OrigSrc><MsgType>A10101</MsgType><WorkDate>20190201</WorkDate><MsgId>UUID0000001</MsgId><RefMsgType></RefMsgType><RefMsgId></RefMsgId><SendDateTime>20190202120000</SendDateTime><MacFlag></MacFlag><RetCode>SKY-9000</RetCode><RetMsg>channel(ets) return timeout</RetMsg></Head></Message>"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
