package com.sky.service.api.struct.transaction.b1010;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@XStreamAlias("TxnPayReturn")
@Data
public class TxnPayReturn implements Serializable {


    private static final long serialVersionUID = -976097534456271964L;
    @XStreamAlias("BankId")
    private String bankId;
    @XStreamAlias("BankDate")
    private String bankDate;
    @XStreamAlias("BankMid")
    private String bankMid;
    @XStreamAlias("OrgId")
    private String orgId;
    @XStreamAlias("OrgDate")
    private String orgDate;
    @XStreamAlias("OrgMid")
    private String orgMid;
    @XStreamAlias("TxnType")
    private String txnType;
    @XStreamAlias("PayDate")
    private String payDate;
    @XStreamAlias("TxnRound")
    private String txnRound;
    @XStreamAlias("TxnDate")
    private String txnDate;
    @XStreamAlias("RetCode")
    private String retCode;
}
