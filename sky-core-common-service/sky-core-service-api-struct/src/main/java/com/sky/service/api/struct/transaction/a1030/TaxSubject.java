package com.sky.service.api.struct.transaction.a1030;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@XStreamAlias("TaxSubject")
@Data
public class TaxSubject implements Serializable {


	private static final long serialVersionUID = 2407979971731231139L;
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
	
}
