package com.sky.service.api.struct.transaction.c1020;

import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@ApiSerializeType("C10201")
@Data
@XStreamAlias("SingleQueryInfo")
public class TransC10201 extends SkyMsgBody implements Serializable {

	private static final long serialVersionUID = 8881105416371963045L;
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
	@XStreamAlias("RefOrgDate")
	private String refOrgDate;
	@XStreamAlias("RefOrgMid")
	private String refOrgMid;

}
