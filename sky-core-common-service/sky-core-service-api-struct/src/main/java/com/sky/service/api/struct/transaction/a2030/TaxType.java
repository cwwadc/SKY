package com.sky.service.api.struct.transaction.a2030;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@XStreamAlias("TaxType")
@Data
public class TaxType implements Serializable {


	private static final long serialVersionUID = 4469346940859201134L;
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
	@XStreamAlias("LimitDate")
	private String limitDate;
	@XStreamAlias("OrgVicesign")
	private String orgVicesign;
	@XStreamAlias("DetailCnt")
	private String detailCnt;
	@XStreamImplicit
	private List<TaxSubject> taxSubjectList;

}
