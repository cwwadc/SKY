package com.sky.service.api.struct.transaction.a1040;


import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@ApiSerializeType("A10402")
@XStreamAlias("TxnReverseReturn")
@Data
public class TransA10402 extends SkyMsgBody implements Serializable {


    private static final long serialVersionUID = 5095795376531212717L;
    @XStreamAlias("OrgId")
    private String orgId;
    @XStreamAlias("OrgDate")
    private String orgDate;
    @XStreamAlias("OrgMid")
    private String orgMid;
    @XStreamAlias("TxnType")
    private String txnType;
    @XStreamAlias("RefOrgDate")
    private String refOrgDate;
    @XStreamAlias("RefOrgMid")
    private String refOrgMid;
    @XStreamAlias("TxnDate")
    private String txnDate;
    @XStreamAlias("Amount")
    private String amount;


}
