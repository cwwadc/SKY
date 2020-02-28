package com.sky.service.api.struct.transaction.a2050;

import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@ApiSerializeType("A20501")
@XStreamAlias("BankPayInfo")
@Data
public class TransA20501 implements Serializable {


    private static final long serialVersionUID = 4862413568744758764L;
    @XStreamAlias("BankId")
    private String bankId;
    @XStreamAlias("BankDate")
    private String bankDate;
    @XStreamAlias("BankMid")
    private String bankMid;
    @XStreamAlias("OrgId")
    private String orgId;
    @XStreamAlias("OrgMid")
    private String orgMid;
    @XStreamAlias("PayDate")
    private String payDate;
    @XStreamAlias("PayerCode")
    private String payerCode;
    @XStreamAlias("TxnDate")
    private String txnDate;
    @XStreamAlias("BankCode")
    private String bankCode;
    @XStreamAlias("ExtBankId")
    private String extBankId;

}
