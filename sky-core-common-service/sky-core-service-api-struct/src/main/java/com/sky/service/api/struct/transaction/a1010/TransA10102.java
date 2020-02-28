package com.sky.service.api.struct.transaction.a1010;


import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@ApiSerializeType("A10102")
@XStreamAlias("TxnPayReturn")
@Data
public class TransA10102 extends SkyMsgBody implements Serializable {

    private static final long serialVersionUID = -4066806250142715498L;

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
    @XStreamAlias("Amount")
    private String amount;


}
