package com.sky.service.api.struct.transaction.a1010;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("TaxType")
public class TaxType implements Serializable {
	private static final long serialVersionUID = -1242019286992293846L;

	@XStreamAlias("SeqNo")
	private String seqNo;
	@XStreamAlias("TaxCode")
	private String taxCode;
	@XStreamAlias("TaxName")
	private String taxName;
	@XStreamAlias("Item")
	private String item;
	@XStreamAlias("TaxTypeAmt")
	private String taxTypeAmt;
	@XStreamAlias("TaxStartDate")
	private String taxStartDate;
	@XStreamAlias("TaxEndDate")
	private String taxEndDate;
	@XStreamAlias("DetailCnt")
	private String detailCnt;
	@XStreamImplicit
	private List<TaxSubject> taxSubjectList;
	
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getTaxCode() {
		return taxCode;
	}
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	public String getTaxName() {
		return taxName;
	}
	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getTaxTypeAmt() {
		return taxTypeAmt;
	}
	public void setTaxTypeAmt(String taxTypeAmt) {
		this.taxTypeAmt = taxTypeAmt;
	}
	public String getTaxStartDate() {
		return taxStartDate;
	}
	public void setTaxStartDate(String taxStartDate) {
		this.taxStartDate = taxStartDate;
	}
	public String getTaxEndDate() {
		return taxEndDate;
	}
	public void setTaxEndDate(String taxEndDate) {
		this.taxEndDate = taxEndDate;
	}
	public String getDetailCnt() {
		return detailCnt;
	}
	public void setDetailCnt(String detailCnt) {
		this.detailCnt = detailCnt;
	}
	public List<TaxSubject> getTaxSubjectList() {
		return taxSubjectList;
	}
	public void setTaxSubjectList(List<TaxSubject> taxSubjectList) {
		this.taxSubjectList = taxSubjectList;
	}
	
	
}
