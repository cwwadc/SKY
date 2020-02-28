package com.sky.service.api.struct.transaction.b1010;

import com.sky.service.api.struct.domain.BatchInfo;
import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiSerializeType("B10101")
@Data
public class TransB10101 extends SkyMsgBody implements Serializable {

	private static final long serialVersionUID = -5657377384693036082L;
	@XStreamAlias("BatchInfo")
	BatchInfo batchInfo;
	@XStreamImplicit
	private List<TxnPayInfo> txnPayInfoList;





}
