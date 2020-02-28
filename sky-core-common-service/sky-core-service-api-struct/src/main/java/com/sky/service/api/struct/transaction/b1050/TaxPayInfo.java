package com.sky.service.api.struct.transaction.b1050;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@XStreamAlias("TaxPayInfo")
@Data
public class TaxPayInfo implements Serializable {


    private static final long serialVersionUID = -5221426540560626327L;
    @XStreamAlias("OrgDate")
    private String orgDate;
    @XStreamAlias("OrgMid")
    private String orgMid;
    @XStreamAlias("PayerCode")
    private String payerCode;
    @XStreamAlias("Amount")
    private String amount;
}
