package com.sky.service.api.struct.transaction.b1010;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@XStreamAlias("TxnPayInfo")
@Data
public class TxnPayInfo implements Serializable {


    private static final long serialVersionUID = -4452640632794382546L;

    @XStreamAlias("BankId")
    private String bankId;
    @XStreamAlias("OrgMid")
    private String orgMid;
    @XStreamAlias("FiscId")
    private String fiscId;
    @XStreamAlias("TxnType")
    private String txnType;
    @XStreamAlias("GetAccNo")
    private String getAccNo;
    @XStreamAlias("PayAccNo")
    private String payAccNo;
    @XStreamAlias("PayAccName")
    private String payAccName;
    @XStreamAlias("Amount")
    private String amount;
    @XStreamAlias("PayType")
    private String payType;
    @XStreamAlias("TaxInfo")
    private TaxInfo taxInfo;
}
