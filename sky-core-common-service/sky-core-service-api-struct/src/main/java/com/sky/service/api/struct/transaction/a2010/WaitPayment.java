package com.sky.service.api.struct.transaction.a2010;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@XStreamAlias("WaitPayment")
@Data
public class WaitPayment implements Serializable {

    private static final long serialVersionUID = -662856231649768632L;

    @XStreamAlias("PayerCode")
    private String payerCode;
    @XStreamAlias("ExtPayerCode")
    private String extPayerCode;
    @XStreamAlias("PayerName")
    private String payerName;
    @XStreamAlias("VoucherNo")
    private String voucherNo;
    @XStreamAlias("ExtVoucherNo")
    private String extVoucherNo;
    @XStreamAlias("BeginDate")
    private Date beginDate;
    @XStreamAlias("EndDate")
    private Date endDate;
    @XStreamAlias("PayAmount")
    private String payAmount;
    @XStreamAlias("LimitDate")
    private String limitDate;
    @XStreamAlias("TaxItem")
    private String taxItem;
    @XStreamAlias("TaxSubject")
    private String taxSubject;
    @XStreamAlias("TaxTypeCode")
    private String taxTypeCode;
    @XStreamAlias("PersonCnt")
    private String personCnt;

}
