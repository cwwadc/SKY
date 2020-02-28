package com.sky.service.api.struct.transaction.c1050;


import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@ApiSerializeType("C10502")
@Data
public class TransC10502 extends SkyMsgBody implements Serializable {

    private static final long serialVersionUID = -29458454649451396L;
}
