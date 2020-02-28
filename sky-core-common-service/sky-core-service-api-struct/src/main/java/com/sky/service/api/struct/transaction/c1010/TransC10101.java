package com.sky.service.api.struct.transaction.c1010;

import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiSerializeType("C10101")
@Data
@XStreamAlias("ClearQueryInfo")
public class TransC10101 extends SkyMsgBody implements Serializable {

	private static final long serialVersionUID = 7793357285833241497L;
	@XStreamAlias("OrgId")
	private String orgId;
	@XStreamAlias("OrgDate")
	private String orgDate;
	@XStreamAlias("OrgMid")
	private String orgMid;
	@XStreamAlias("QueryType")
	private String queryType;
	@XStreamAlias("TxnType")
	private String txnType;
	@XStreamAlias("TxnDate")
	private String txnDate;

}
