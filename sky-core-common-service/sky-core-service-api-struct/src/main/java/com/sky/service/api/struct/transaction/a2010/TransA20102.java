package com.sky.service.api.struct.transaction.a2010;


import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiSerializeType("A20102")
@XStreamAlias("TaxQueryReturn")
@Data
public class TransA20102 implements Serializable {


    private static final long serialVersionUID = 640655313247598024L;
    @XStreamAlias("BankId")
    private String bankId;
    @XStreamAlias("BankDate")
    private Date bankDate;
    @XStreamAlias("BankMid")
    private String bankMid;
    @XStreamAlias("IdnId")
    private String idnId;
    @XStreamAlias("IdnName")
    private String idnName;
    @XStreamAlias("ExtPayerCode")
    private String extPayerCode;
    @XStreamAlias("BeginDate")
    private Date beginDate;
    @XStreamAlias("EndDate")
    private Date endDate;
    @XStreamAlias("TaxType")
    private String taxType;
    @XStreamImplicit
    private List<WaitPayment> waitPaymentList;

}
