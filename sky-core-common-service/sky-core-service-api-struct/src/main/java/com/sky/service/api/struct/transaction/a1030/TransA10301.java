package com.sky.service.api.struct.transaction.a1030;

import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.struct.transaction.a1030.TaxInfo;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@ApiSerializeType("A10301")
@XStreamAlias("TxnPayInfo")
@Data
public class TransA10301 extends SkyMsgBody implements Serializable {


	private static final long serialVersionUID = -1575134084975368714L;
	@XStreamAlias("OrgId")
	private String orgId;
	@XStreamAlias("OrgDate")
	private String orgDate;
	@XStreamAlias("OrgMid")
	private String orgMid;
	@XStreamAlias("FiscId")
	private String fiscId;
	@XStreamAlias("TxnType")
	private String txnType;
	@XStreamAlias("RefOrgMid")
	private String refOrgMid;
	@XStreamAlias("GetAccNo")
	private String getAccNo;
	@XStreamAlias("PayAccNo")
	private String payAccNo;
	@XStreamAlias("Amount")
	private String amount;
	@XStreamAlias("PayType")
	private String payType;
	@XStreamAlias("TaxType")
	private String taxType;
	@XStreamAlias("TaxInfo")
	private TaxInfo taxInfo;
	
}
