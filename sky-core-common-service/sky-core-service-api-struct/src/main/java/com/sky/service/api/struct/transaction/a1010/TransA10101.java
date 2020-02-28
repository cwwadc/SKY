package com.sky.service.api.struct.transaction.a1010;

import java.io.Serializable;

import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@ApiSerializeType("A10101")
@XStreamAlias("TxnPayInfo")
@Data
public class TransA10101 extends SkyMsgBody implements Serializable {
	private static final long serialVersionUID = -6746566997025265347L;
	
	@XStreamAlias("OrgId")
	private String orgId;
	@XStreamAlias("OrgDate")
	private String orgDate;
	@XStreamAlias("OrgMid")
	private String orgMid;
	@XStreamAlias("BankId")
	private String bankId;
	@XStreamAlias("FiscId")
	private String fiscId;
	@XStreamAlias("TxnType")
	private String txnType;
	@XStreamAlias("GetAccNo")
	private String getAccNo;
	@XStreamAlias("PayAccNo")
	private String payAccNo;
	@XStreamAlias("PayAccName")
	private String payAccName;
	@XStreamAlias("Amount")
	private String amount;
	@XStreamAlias("PayType")
	private String payType;
	@XStreamAlias("TaxInfo")
	private TaxInfo taxInfo;
	
	
}
