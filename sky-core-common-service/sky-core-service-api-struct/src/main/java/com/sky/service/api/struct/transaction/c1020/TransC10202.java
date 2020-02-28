package com.sky.service.api.struct.transaction.c1020;

import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@ApiSerializeType("C10202")
@Data
@XStreamAlias("SingleQueryReturn")
public class TransC10202 extends SkyMsgBody implements Serializable {

	private static final long serialVersionUID = -3796028020087741491L;
	@XStreamAlias("OrgId")
	private String orgId;
	@XStreamAlias("OrgDate")
	private String orgDate;
	@XStreamAlias("OrgMid")
	private String orgMid;
	@XStreamAlias("QueryType")
	private String queryType;
	@XStreamAlias("TxnDate")
	private String txnDate;
	@XStreamAlias("TxnType")
	private String txnType;
	@XStreamAlias("TxnRound")
	private String txnRound;
	@XStreamAlias("PayDate")
	private String payDate;
	@XStreamAlias("Amount")
	private String amount;
}
