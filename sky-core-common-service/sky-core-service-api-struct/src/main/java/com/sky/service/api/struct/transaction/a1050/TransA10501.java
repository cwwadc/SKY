package com.sky.service.api.struct.transaction.a1050;

import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@ApiSerializeType("A10501")
@XStreamAlias("ProvenInfo")
@Data
public class TransA10501 extends SkyMsgBody implements Serializable {


	private static final long serialVersionUID = 5604594770456389723L;
	@XStreamAlias("OrgId")
	private String orgId;
	@XStreamAlias("OrgDate")
	private String orgDate;
	@XStreamAlias("OrgMid")
	private String orgMid;
	@XStreamAlias("BankId")
	private String bankId;
	@XStreamAlias("TranType")
	private String tranType;
	@XStreamAlias("RealOrgId")
	private String realOrgId;
	@XStreamAlias("SignDate")
	private String signDate;
	@XStreamAlias("ChkCnlSeq")
	private String chkCnlSeq;
	@XStreamAlias("ChkCnlFlag")
	private String chkCnlFlag;
	@XStreamAlias("SignOrgId")
	private String signOrgId;
	@XStreamAlias("PayerCode")
	private String payerCode;
	@XStreamAlias("PayerName")
	private String payerName;
	@XStreamAlias("PayBankId")
	private String payBankId;
	@XStreamAlias("SignBankId")
	private String signBankId;
	@XStreamAlias("PayAccNo")
	private String payAccNo;
	@XStreamAlias("PayAccName")
	private String payAccName;
	@XStreamAlias("DocumenNo")
	private String documenNo;

}
