package com.sky.service.api.struct.transaction.b1010;

import com.sky.service.api.struct.transaction.b1010.TaxType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@XStreamAlias("TaxInfo")
@Data
public class TaxInfo implements Serializable {
	private static final long serialVersionUID = -4535541754637414190L;
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
	@XStreamAlias("VisaCheck")
	private String visaCheck;
	@XStreamAlias("BillDate")
	private String billDate;
	@XStreamAlias("LimitDate")
	private String limitDate;
	@XStreamAlias("ExtOrgId")
	private String extOrgId;
	@XStreamAlias("ExtPayerCode")
	private String extPayerCode;
	@XStreamAlias("Commentary")
	private String commentary;
	@XStreamAlias("TxnTypeCnt")
	private String txnTypeCnt;
	@XStreamImplicit
	private List<TaxType> taxTypeList;


}
