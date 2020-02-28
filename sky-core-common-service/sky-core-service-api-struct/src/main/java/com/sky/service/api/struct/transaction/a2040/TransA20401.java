package com.sky.service.api.struct.transaction.a2040;

import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@ApiSerializeType("A20401")
@XStreamAlias("BillPaidInfo")
@Data
public class TransA20401 implements Serializable {

    private static final long serialVersionUID = 829351676090643185L;

    @XStreamAlias("BankId")
    private String bankId;
    @XStreamAlias("BankDate")
    private String bankDate;
    @XStreamAlias("BankMid")
    private String bankMid;
    @XStreamAlias("OrgId")
    private String orgId;
    @XStreamAlias("PayDate")
    private String payDate;
    @XStreamAlias("TxnDate")
    private String txnDate;
    @XStreamAlias("OrgMid")
    private String orgMid;
    @XStreamAlias("PayerCode")
    private String payerCode;
}
