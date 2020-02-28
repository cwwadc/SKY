package com.sky.service.api.struct.transaction.a1010;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("TaxInfo")
public class TaxInfo implements Serializable {
	private static final long serialVersionUID = 8809101086980716246L;
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
	
	public String getPrintFlag() {
		return printFlag;
	}
	public void setPrintFlag(String printFlag) {
		this.printFlag = printFlag;
	}
	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	public String getPayerCode() {
		return payerCode;
	}
	public void setPayerCode(String payerCode) {
		this.payerCode = payerCode;
	}
	public String getCorpRegCode() {
		return corpRegCode;
	}
	public void setCorpRegCode(String corpRegCode) {
		this.corpRegCode = corpRegCode;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}
	public String getVisaCheck() {
		return visaCheck;
	}
	public void setVisaCheck(String visaCheck) {
		this.visaCheck = visaCheck;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public String getLimitDate() {
		return limitDate;
	}
	public void setLimitDate(String limitDate) {
		this.limitDate = limitDate;
	}
	public String getCommentary() {
		return commentary;
	}
	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}
	public String getExtOrgId() {
		return extOrgId;
	}
	public void setExtOrgId(String extOrgId) {
		this.extOrgId = extOrgId;
	}
	public String getExtPayerCode() {
		return extPayerCode;
	}
	public void setExtPayerCode(String extPayerCode) {
		this.extPayerCode = extPayerCode;
	}
	public String getTxnTypeCnt() {
		return txnTypeCnt;
	}
	public void setTxnTypeCnt(String txnTypeCnt) {
		this.txnTypeCnt = txnTypeCnt;
	}
	public List<TaxType> getTaxTypeList() {
		return taxTypeList;
	}
	public void setTaxTypeList(List<TaxType> taxTypeList) {
		this.taxTypeList = taxTypeList;
	}

}
