package com.sky.service.api.struct.transaction.a1040;

import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@ApiSerializeType("A10401")
@XStreamAlias("TxnReverseInfo")
@Data
public class TransA10401 extends SkyMsgBody implements Serializable {


	private static final long serialVersionUID = -2531739702790609792L;
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
