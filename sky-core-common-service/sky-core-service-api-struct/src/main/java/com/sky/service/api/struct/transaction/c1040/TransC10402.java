package com.sky.service.api.struct.transaction.c1040;

import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@ApiSerializeType("C10402")
@XStreamAlias("BatchStatusReturn")
@Data
public class TransC10402 extends SkyMsgBody implements Serializable {

	private static final long serialVersionUID = -2974267641481637454L;
	@XStreamAlias("OrgId")
	private String orgId;
	@XStreamAlias("OrgDate")
	private String orgDate;
	@XStreamAlias("OrgMid")
	private String OrgMid;
	@XStreamAlias("BankId")
	private String bankId;
	@XStreamAlias("PackNo")
	private String packNo;
	@XStreamAlias("AllNum")
	private String allNum;
	@XStreamAlias("AllAmt")
	private String allAmt;
	@XStreamAlias("SuccessNum")
	private String successNum;
	@XStreamAlias("SuccessAmt")
	private String successAmt;
	@XStreamAlias("FailNum")
	private String failNum;
	@XStreamAlias("FailAmt")
	private String failAmt;

}
