package com.sky.service.api.struct.transaction.a2030;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@XStreamAlias("TaxInfo")
@Data
public class TaxInfo implements Serializable {

	private static final long serialVersionUID = 7486686162285497029L;
	@XStreamAlias("PayerCode")
	private String payerCode;
	@XStreamAlias("PayerName")
	private String payerName;
	@XStreamAlias("ExtOrgId")
	private String extOrgId;
	@XStreamAlias("ExtPayerCode")
	private String extPayerCode;
	@XStreamAlias("PayBankId")
	private String payBankId;
	@XStreamAlias("VoucherNo")
	private String voucherNo;
	@XStreamAlias("OpenBillAmt")
	private String openBillAmt;
	@XStreamAlias("OpenBillDate")
	private String openBillDate;
	@XStreamAlias("OrgName")
	private String orgName;
	@XStreamAlias("BudgetType")
	private String budgetType;
	@XStreamAlias("AdjustFlag")
	private String adjustFlag;
	@XStreamAlias("CorpRegCode")
	private String corpRegCode;
	@XStreamAlias("TxnTypeCnt")
	private String txnTypeCnt;
	@XStreamImplicit
	private List<TaxType> taxTypeList;
	@XStreamImplicit
	private List<RefVouNo> refVouNoList;


}
