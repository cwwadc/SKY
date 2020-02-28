package com.sky.service.api.struct.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@XStreamAlias("BatchInfo")
@Data
public class BatchInfo implements Serializable {
    private static final long serialVersionUID = 6679878140404758275L;

    @XStreamAlias("OrgId")
    private String orgId;
    @XStreamAlias("OrgDate")
    private String orgDate;
    @XStreamAlias("BankId")
    private String bankId;
    @XStreamAlias("PackNo")
    private String packNo;
    @XStreamAlias("AllNum")
    private String allNum;
    @XStreamAlias("AllAmt")
    private String allAmt;
    @XStreamAlias("TxnDate")
    private String txnDate;
}
