package com.sky.service.api.struct.transaction.c1060;


import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@ApiSerializeType("C10601")
@XStreamAlias("BatchReqInfo")
@Data
public class TransC10601 extends SkyMsgBody implements Serializable {

    private static final long serialVersionUID = 765009582217838703L;
    @XStreamAlias("OrgId")
    private String orgId;
    @XStreamAlias("OrgDate")
    private String orgDate;
    @XStreamAlias("OrgMid")
    private String OrgMid;
    @XStreamAlias("TxnDate")
    private String txnDate;
}
