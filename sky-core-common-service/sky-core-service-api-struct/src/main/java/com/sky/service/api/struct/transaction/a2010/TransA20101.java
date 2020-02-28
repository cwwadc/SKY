package com.sky.service.api.struct.transaction.a2010;

import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@ApiSerializeType("A20101")
@XStreamAlias("TaxQueryInfo")
@Data
public class TransA20101 extends SkyMsgBody implements Serializable {


	private static final long serialVersionUID = 2869162411909104385L;
	@XStreamAlias("BankId")
	private String bankId;
	@XStreamAlias("BankDate")
	private String bankDate;
	@XStreamAlias("BankMid")
	private String bankMid;
	@XStreamAlias("IdnId")
	private String idnId;
	@XStreamAlias("IdnName")
	private String idnName;
	@XStreamAlias("ExtPayerCode")
	private String extPayerCode;
	@XStreamAlias("BeginDate")
	private String beginDate;
	@XStreamAlias("EndDate")
	private String endDate;
	@XStreamAlias("TaxType")
	private String taxType;
	
}
