package com.sky.service.api.struct.transaction.t1010;

import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.support.serialize.ApiSerializeType;

import java.io.Serializable;

/**
 * 通用应答t10101只有报文头，无body
 */
@ApiSerializeType("T10101")
public class TransT10101 extends SkyMsgBody implements Serializable {
}
