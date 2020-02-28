package com.sky.service.api.struct.transaction.b1060;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@XStreamAlias("TaxReportInfo")
@Data
public class TaxReportInfo implements Serializable {

    private static final long serialVersionUID = -6479514983221145439L;
    @XStreamAlias("OrgId")
    private String orgId;
    @XStreamAlias("ExtOrgId")
    private String extOrgId;
    @XStreamAlias("Blevel")
    private String blevel;
    @XStreamAlias("Item")
    private String item;
    @XStreamAlias("TaxSubjectCode")
    private String taxSubjectCode;
    @XStreamAlias("ExtPropCode")
    private String extPropCode;
    @XStreamAlias("TaxPropCode")
    private String taxPropCode;
    @XStreamAlias("AccMode")
    private String accMode;
    @XStreamAlias("GetAccNo")
    private String getAccNo;
    @XStreamAlias("GetAccName")
    private String getAccName;
    @XStreamAlias("BankId")
    private String bnkId;
    @XStreamAlias("Cnt")
    private String cnt;
    @XStreamAlias("DtlCnt")
    private String dtlCnt;
    @XStreamAlias("Amount")
    private String amount;
}
