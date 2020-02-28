package com.sky.service.api.struct.transaction.c1010;

import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@ApiSerializeType("C10102")
@Data
@XStreamAlias("ClearQueryReturn")
public class TransC10102 extends SkyMsgBody implements Serializable {

	private static final long serialVersionUID = 3187473741323013493L;
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
	@XStreamAlias("TxnCnt")
	private String txnCnt;
	@XStreamAlias("Amount")
	private String amount;
}
