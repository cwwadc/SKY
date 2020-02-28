package com.sky.service.api.struct.transaction.a1030;


import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@ApiSerializeType("A10302")
@XStreamAlias("TxnPayReturn")
@Data
public class TransA10302 extends SkyMsgBody implements Serializable {


    private static final long serialVersionUID = -6947005977048264992L;
    @XStreamAlias("OrgId")
    private String orgId;
    @XStreamAlias("OrgDate")
    private String orgDate;
    @XStreamAlias("OrgMid")
    private String orgMid;
    @XStreamAlias("TxnType")
    private String txnType;
    @XStreamAlias("RefOrgMid")
    private String refOrgMid;
    @XStreamAlias("Amount")
    private String amount;


}
