package com.sky.core.utils;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.*;

public class sendTest {
    private final static String QUEUE_NAME = "rabbit.q.offline.recording";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("120.77.179.74");
        factory.setPort(5672); //默认端口号
        factory.setUsername("rabbit");//默认用户名
        factory.setPassword("admin");//默认密码
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String filePath = "E:/A10101.txt";
//        String message = readTxt(filePath);
        String message = "<Message>\n" +
                "  <Head>\n" +
                "    <Src>TAX</Src>\n" +
                "    <Dest>ETS</Dest>\n" +
                "    <OrigSrc>GZTAX</OrigSrc>\n" +
                "    <MsgType>A10101</MsgType>\n" +
                "    <WorkDate>20190201</WorkDate>\n" +
                "    <MsgId>UUID0000001</MsgId>\n" +
                "    <RefMsgType></RefMsgType>\n" +
                "    <RefMsgId></RefMsgId>\n" +
                "    <SendDateTime>20190202120000</SendDateTime>\n" +
                "    <MacFlag></MacFlag>\n" +
                "  </Head>\n" +
                "  <Body>\n" +
                "    <TxnPayInfo>\n" +
                "      <OrgId>ORG1989</OrgId>\n" +
                "      <OrgDate>20190131</OrgDate>\n" +
                "      <OrgMid>ORGMID001</OrgMid>\n" +
                "      <BankId>441522</BankId>\n" +
                "      <FiscId>A00001</FiscId>\n" +
                "      <TxnType>1</TxnType>\n" +
                "      <GetAccNo>6225882018199765</GetAccNo>\n" +
                "      <PayAccNo>6224556797655678</PayAccNo>\n" +
                "      <Amount>12396</Amount>\n" +
                "      <PayType>EX</PayType>\n" +
                "      <TaxInfo>\n" +
                "        <PrintFlag>A</PrintFlag>\n" +
                "        <PayerName>张三</PayerName>\n" +
                "        <PayerCode>PAYER10000</PayerCode>\n" +
                "        <CorpRegCode>ALLINPAY0001</CorpRegCode>\n" +
                "        <DocumentNo>4415221990080776554</DocumentNo>\n" +
                "        <VisaCheck>1</VisaCheck>\n" +
                "        <BillDate>20190101</BillDate>\n" +
                "        <LimitDate>20190131</LimitDate>\n" +
                "        <Commentary>备注内容HELLO</Commentary>\n" +
                "        <ExtOrgId>12312312</ExtOrgId>\n" +
                "        <ExtPayerCode>EXPAYER10000</ExtPayerCode>\n" +
                "        <TxnTypeCnt>12396</TxnTypeCnt>\n" +
                "        <TaxType>\n" +
                "          <SeqNo>tt11111</SeqNo>\n" +
                "          <TaxCode>tttaxcode</TaxCode>\n" +
                "          <TaxName>tttaxname</TaxName>\n" +
                "          <Item>商品1234</Item>\n" +
                "          <TaxTypeAmt>12398</TaxTypeAmt>\n" +
                "          <TaxStartDate>20190101</TaxStartDate>\n" +
                "          <TaxEndDate>20190131</TaxEndDate>\n" +
                "          <DetailCnt>12396</DetailCnt>\n" +
                "          <TaxSubject>\n" +
                "            <SeqNo>sj12312</SeqNo>\n" +
                "            <TaxSubjectCode>sjtsccode</TaxSubjectCode>\n" +
                "            <TaxSubjectName>sj税种名称</TaxSubjectName>\n" +
                "            <BudgetType>2</BudgetType>\n" +
                "            <AdjustFlag>0</AdjustFlag>\n" +
                "            <TaxType>A</TaxType>\n" +
                "            <DescFisc>描述内容</DescFisc>\n" +
                "            <OrgVicesign>AA9987</OrgVicesign>\n" +
                "            <TaxPropCode>098y</TaxPropCode>\n" +
                "            <Blevel>8</Blevel>\n" +
                "            <Vicesign>BB9987</Vicesign>\n" +
                "            <TaxSubjectAmt>12396</TaxSubjectAmt>\n" +
                "          </TaxSubject>\n" +
                "          <TaxSubject>\n" +
                "            <SeqNo>sj12312</SeqNo>\n" +
                "            <TaxSubjectCode>sjtsccode</TaxSubjectCode>\n" +
                "            <TaxSubjectName>sj税种名称</TaxSubjectName>\n" +
                "            <BudgetType>2</BudgetType>\n" +
                "            <AdjustFlag>0</AdjustFlag>\n" +
                "            <TaxType>A</TaxType>\n" +
                "            <DescFisc>描述内容</DescFisc>\n" +
                "            <OrgVicesign>AA9987</OrgVicesign>\n" +
                "            <TaxPropCode>098y</TaxPropCode>\n" +
                "            <Blevel>8</Blevel>\n" +
                "            <Vicesign>BB9987</Vicesign>\n" +
                "            <TaxSubjectAmt>12396</TaxSubjectAmt>\n" +
                "          </TaxSubject>\n" +
                "        </TaxType>\n" +
                "        <TaxType>\n" +
                "          <SeqNo>tt11111</SeqNo>\n" +
                "          <TaxCode>tttaxcode</TaxCode>\n" +
                "          <TaxName>tttaxname</TaxName>\n" +
                "          <Item>商品1234</Item>\n" +
                "          <TaxTypeAmt>12398</TaxTypeAmt>\n" +
                "          <TaxStartDate>20190101</TaxStartDate>\n" +
                "          <TaxEndDate>20190131</TaxEndDate>\n" +
                "          <DetailCnt>12396</DetailCnt>\n" +
                "          <TaxSubject>\n" +
                "            <SeqNo>sj12312</SeqNo>\n" +
                "            <TaxSubjectCode>sjtsccode</TaxSubjectCode>\n" +
                "            <TaxSubjectName>sj税种名称</TaxSubjectName>\n" +
                "            <BudgetType>2</BudgetType>\n" +
                "            <AdjustFlag>0</AdjustFlag>\n" +
                "            <TaxType>A</TaxType>\n" +
                "            <DescFisc>描述内容</DescFisc>\n" +
                "            <OrgVicesign>AA9987</OrgVicesign>\n" +
                "            <TaxPropCode>098y</TaxPropCode>\n" +
                "            <Blevel>8</Blevel>\n" +
                "            <Vicesign>BB9987</Vicesign>\n" +
                "            <TaxSubjectAmt>12396</TaxSubjectAmt>\n" +
                "          </TaxSubject>\n" +
                "          <TaxSubject>\n" +
                "            <SeqNo>sj12312</SeqNo>\n" +
                "            <TaxSubjectCode>sjtsccode</TaxSubjectCode>\n" +
                "            <TaxSubjectName>sj税种名称</TaxSubjectName>\n" +
                "            <BudgetType>2</BudgetType>\n" +
                "            <AdjustFlag>0</AdjustFlag>\n" +
                "            <TaxType>A</TaxType>\n" +
                "            <DescFisc>描述内容</DescFisc>\n" +
                "            <OrgVicesign>AA9987</OrgVicesign>\n" +
                "            <TaxPropCode>098y</TaxPropCode>\n" +
                "            <Blevel>8</Blevel>\n" +
                "            <Vicesign>BB9987</Vicesign>\n" +
                "            <TaxSubjectAmt>12396</TaxSubjectAmt>\n" +
                "          </TaxSubject>\n" +
                "        </TaxType>\n" +
                "      </TaxInfo>\n" +
                "    </TxnPayInfo>\n" +
                "  </Body>\n" +
                "</Message>";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }


    public static String  readTxt(String filePath) {
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String lineTxt = null;
                String msg = null;
                int num = 0;
                long time1 = System.currentTimeMillis();
                while ((lineTxt = br.readLine()) != null) {
                    System.out.println(lineTxt);
                    num++;
                    msg = msg+lineTxt;
                }

                System.out.println("总共"+num+"条数据！");
                long time2 = System.currentTimeMillis();
                long time = time1 - time2;
                System.out.println("共花费" + time + "秒");
                br.close();
                return msg;
            } else {
                System.out.println("文件不存在!");
            }
        } catch (Exception e) {
            System.out.println("文件读取错误!");
        }
        return null;
    }
}
