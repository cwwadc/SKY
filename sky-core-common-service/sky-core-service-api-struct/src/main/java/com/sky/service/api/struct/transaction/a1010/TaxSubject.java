package com.sky.service.api.struct.transaction.a1010;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("TaxSubject")
public class TaxSubject implements Serializable {
	private static final long serialVersionUID = 744022395589992881L;
	
	@XStreamAlias("SeqNo")
	private String seqNo;
	@XStreamAlias("TaxSubjectCode")
	private String taxSubjectCode;
	@XStreamAlias("TaxSubjectName")
	private String taxSubjectName;
	@XStreamAlias("BudgetType")
	private String budgetType;
	@XStreamAlias("AdjustFlag")
	private String adjustFlag;
	@XStreamAlias("TaxType")
	private String taxType;
	@XStreamAlias("DescFisc")
	private String descFisc;
	@XStreamAlias("OrgVicesign")
	private String orgVicesign;
	@XStreamAlias("TaxPropCode")
	private String taxPropCode;
	@XStreamAlias("ExtPropCode")
	private String extPropCode;
	@XStreamAlias("Blevel")
	private String blevel;
	@XStreamAlias("Vicesign")
	private String vicesign;
	@XStreamAlias("TaxSubjectAmt")
	private String taxSubjectAmt;
	
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getTaxSubjectCode() {
		return taxSubjectCode;
	}
	public void setTaxSubjectCode(String taxSubjectCode) {
		this.taxSubjectCode = taxSubjectCode;
	}
	public String getTaxSubjectName() {
		return taxSubjectName;
	}
	public void setTaxSubjectName(String taxSubjectName) {
		this.taxSubjectName = taxSubjectName;
	}
	public String getBudgetType() {
		return budgetType;
	}
	public void setBudgetType(String budgetType) {
		this.budgetType = budgetType;
	}
	public String getAdjustFlag() {
		return adjustFlag;
	}
	public void setAdjustFlag(String adjustFlag) {
		this.adjustFlag = adjustFlag;
	}
	public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
	public String getDescFisc() {
		return descFisc;
	}
	public void setDescFisc(String descFisc) {
		this.descFisc = descFisc;
	}
	public String getOrgVicesign() {
		return orgVicesign;
	}
	public void setOrgVicesign(String orgVicesign) {
		this.orgVicesign = orgVicesign;
	}
	public String getTaxPropCode() {
		return taxPropCode;
	}
	public void setTaxPropCode(String taxPropCode) {
		this.taxPropCode = taxPropCode;
	}
	public String getExtPropCode() {
		return extPropCode;
	}
	public void setExtPropCode(String extPropCode) {
		this.extPropCode = extPropCode;
	}
	public String getBlevel() {
		return blevel;
	}
	public void setBlevel(String blevel) {
		this.blevel = blevel;
	}
	public String getVicesign() {
		return vicesign;
	}
	public void setVicesign(String vicesign) {
		this.vicesign = vicesign;
	}
	public String getTaxSubjectAmt() {
		return taxSubjectAmt;
	}
	public void setTaxSubjectAmt(String taxSubjectAmt) {
		this.taxSubjectAmt = taxSubjectAmt;
	}
	
	
	
	
	
}
