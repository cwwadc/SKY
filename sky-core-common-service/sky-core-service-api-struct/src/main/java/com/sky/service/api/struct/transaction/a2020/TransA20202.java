package com.sky.service.api.struct.transaction.a2020;


import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiSerializeType("A20202")
@XStreamAlias("BillCreateReturn")
@Data
public class TransA20202 implements Serializable {


    private static final long serialVersionUID = 192289705245866791L;
    @XStreamAlias("OrgId")
    private String orgId;
    @XStreamAlias("OrgDate")
    private Date orgDate;
    @XStreamAlias("OrgMid")
    private String orgMid;
    @XStreamAlias("BankId")
    private String bankId;
    @XStreamAlias("BankDate")
    private String bankDate;
    @XStreamAlias("BankMid")
    private String bankMid;
    @XStreamAlias("PayMid")
    private Date payMid;
    @XStreamAlias("PayerCode")
    private Date payerCode;
    @XStreamAlias("PayerName")
    private String payerName;
    @XStreamAlias("PayOrgId")
    private String payOrgId;
    @XStreamAlias("OrgName")
    private String orgName;
    @XStreamAlias("LimitDate")
    private String limitDate;
    @XStreamAlias("TotAmount")
    private String totAmount;
    @XStreamAlias("TaxSubjectCnt")
    private String taxSubjectCnt;
    @XStreamAlias("TaxSubject")
    private TaxSubject TaxSubject;

}
