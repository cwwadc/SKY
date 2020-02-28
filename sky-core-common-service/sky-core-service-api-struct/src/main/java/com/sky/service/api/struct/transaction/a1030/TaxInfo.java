package com.sky.service.api.struct.transaction.a1030;

import java.io.Serializable;
import java.util.List;

import com.sky.service.api.struct.transaction.a1030.TaxType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

@XStreamAlias("TaxInfo")
@Data
public class TaxInfo implements Serializable {


	private static final long serialVersionUID = 7973565531791154497L;
	@XStreamAlias("PrintFlag")
	private String printFlag;
	@XStreamAlias("PayerName")
	private String payerName;
	@XStreamAlias("PayerCode")
	private String payerCode;
	@XStreamAlias("CorpRegCode")
	private String corpRegCode;
	@XStreamAlias("DocumentNo")
	private String documentNo;
	@XStreamAlias("BillDate")
	private String billDate;
	@XStreamAlias("LimitDate")
	private String limitDate;
	@XStreamAlias("Commentary")
	private String commentary;
	@XStreamAlias("ExtOrgId")
	private String extOrgId;
	@XStreamAlias("ExtPayerCode")
	private String extPayerCode;
	@XStreamAlias("TxnTypeCnt")
	private String txnTypeCnt;
	@XStreamImplicit
	private List<TaxType> taxTypeList;
}
