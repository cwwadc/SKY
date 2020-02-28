package com.sky.service.api.struct.transaction.a2050;

import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@ApiSerializeType("A20502")
@XStreamAlias("BankPayReturn")
@Data
public class TransA20502 implements Serializable {


    private static final long serialVersionUID = -7184916418268223979L;
    @XStreamAlias("OrgId")
    private String orgId;
    @XStreamAlias("OrgDate")
    private String orgDate;
    @XStreamAlias("OrgMid")
    private String orgMid;
    @XStreamAlias("BankId")
    private String bankId;
    @XStreamAlias("BankgMid")
    private String bankgMid;
    @XStreamAlias("BankDate")
    private String bankDate;
    @XStreamAlias("RefOrgMid")
    private String refOrgMid;
}
