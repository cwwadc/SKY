package com.sky.service.api.struct.transaction.c1030;

import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@ApiSerializeType("C10301")
@XStreamAlias("BatchCancelInfo")
@Data
public class TransC10301 extends SkyMsgBody implements Serializable {

	private static final long serialVersionUID = -8315694198075548960L;
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
