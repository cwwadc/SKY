package com.sky.service.api.struct.transaction.a2030;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@XStreamAlias("TaxSubject")
@Data
public class TaxSubject implements Serializable {


	private static final long serialVersionUID = 6918210155033138948L;
	@XStreamAlias("SeqNo")
	private String seqNo;
	@XStreamAlias("TaxSubjectCode")
	private String taxSubjectCode;
	@XStreamAlias("TaxSubjectName")
	private String taxSubjectName;
	@XStreamAlias("TaxPropCode")
	private String taxPropCode;
	@XStreamAlias("ExtPropCode")
	private String extPropCode;
	@XStreamAlias("Blevel")
	private String blevel;
	@XStreamAlias("Vicesign")
	private String vicesign;
	@XStreamAlias("TaxCnt")
	private String taxCnt;
	@XStreamAlias("TaxableAmt")
	private String taxableAmt;
	@XStreamAlias("TaxSubjectAmt")
	private String taxSubjectAmt;

}
