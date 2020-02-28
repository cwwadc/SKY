package com.sky.service.api.struct.transaction.c1030;

import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@ApiSerializeType("C10302")
@XStreamAlias("BatchCancelReturn")
@Data
public class TransC10302 extends SkyMsgBody implements Serializable {

	private static final long serialVersionUID = 4888891628845757987L;
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
	@XStreamAlias("CancelType")
	private String cancelType;
	@XStreamAlias("Reserver")
	private String reserver;

}
