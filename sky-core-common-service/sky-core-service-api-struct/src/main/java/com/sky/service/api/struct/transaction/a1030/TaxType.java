package com.sky.service.api.struct.transaction.a1030;

import java.io.Serializable;
import java.util.List;

import com.sky.service.api.struct.transaction.a1030.TaxSubject;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

@XStreamAlias("TaxType")
@Data
public class TaxType implements Serializable {


	private static final long serialVersionUID = 2821485084980031775L;
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
	
}
