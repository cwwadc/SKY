package com.sky.service.api.struct.transaction.a1020;

import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.struct.transaction.a1010.TaxInfo;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@ApiSerializeType("A10201")
@XStreamAlias("TxnReverseInfo")
@Data
public class TransA10201 extends SkyMsgBody implements Serializable {

	private static final long serialVersionUID = -1206949740011118873L;

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
	@XStreamAlias("RefOrgDate")
	private String refOrgDate;
	@XStreamAlias("RefOrgMid")
	private String refOrgMid;
	@XStreamAlias("GetAccNo")
	private String getAccNo;
	@XStreamAlias("PayAccNo")
	private String payAccNo;
	@XStreamAlias("Amount")
	private String Amount;
	@XStreamAlias("PayType")
	private String payType;
	
}
