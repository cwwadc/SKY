package com.sky.service.api.struct.transaction.b1050;

import com.sky.service.api.struct.domain.BatchInfo;
import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiSerializeType("B10501")
@Data
public class TransB10501 extends SkyMsgBody implements Serializable {

	private static final long serialVersionUID = -448226783080163764L;
	@XStreamAlias("BatchInfo")
	private BatchInfo batchInfo;
	@XStreamImplicit
	private List<TaxPayInfo> txnPayInfoList;

}
