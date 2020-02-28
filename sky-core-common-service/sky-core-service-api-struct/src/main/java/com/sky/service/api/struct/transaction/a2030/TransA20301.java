package com.sky.service.api.struct.transaction.a2030;

import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@ApiSerializeType("A20301")
@XStreamAlias("BillQueryInfo")
@Data
public class TransA20301 extends SkyMsgBody implements Serializable {


	private static final long serialVersionUID = -634823908889801599L;
	@XStreamAlias("BankId")
	private String bankId;
	@XStreamAlias("BankDate")
	private String bankDate;
	@XStreamAlias("BankMid")
	private String bankMid;
	@XStreamAlias("OrgId")
	private String orgId;
	@XStreamAlias("TxnDate")
	private String txnDate;
	@XStreamAlias("PayerCode")
	private String payerCode;
	@XStreamAlias("OrgMid")
	private String orgMid;
	@XStreamAlias("LateFee")
	private String lateFee;
	@XStreamAlias("PayBankId")
	private String payBankId;
	
}
