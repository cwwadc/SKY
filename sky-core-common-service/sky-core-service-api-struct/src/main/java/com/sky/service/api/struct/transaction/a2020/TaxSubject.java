package com.sky.service.api.struct.transaction.a2020;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@XStreamAlias("TaxSubject")
@Data
public class TaxSubject implements Serializable {


	private static final long serialVersionUID = 4921099990308100924L;
	@XStreamAlias("TaxSubjectCode")
	private String taxSubjectCode;
	@XStreamAlias("TaxSubjectName")
	private String taxSubjectName;
	@XStreamAlias("Amount")
	private String amount;

}
