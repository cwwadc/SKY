package com.sky.service.api.struct.transaction.a2040;

import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@ApiSerializeType("A20402")
@XStreamAlias("BillPaidReturn")
@Data
public class TransA20402 implements Serializable {


    private static final long serialVersionUID = 8264237930691322725L;
    @XStreamAlias("OrgId")
    private String orgId;
    @XStreamAlias("OrgDate")
    private String orgDate;
    @XStreamAlias("OrgMid")
    private String orgMid;
    @XStreamAlias("BankId")
    private String bankId;
    @XStreamAlias("BankDate")
    private String bankDate;
    @XStreamAlias("BankMid")
    private String bankMid;
}
