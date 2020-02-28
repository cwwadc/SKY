package com.sky.service.api.struct.transaction.c1060;


import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@ApiSerializeType("C10602")
@Data
public class TransC10602 extends SkyMsgBody implements Serializable {

    private static final long serialVersionUID = 8279474754434460399L;
}
