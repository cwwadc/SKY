package com.sky.service.api.struct.transaction.b1010;

import com.sky.service.api.struct.domain.BatchInfo;
import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiSerializeType("B10102")
@Data
public class TransB10102 extends SkyMsgBody implements Serializable {

	private static final long serialVersionUID = 955273039092030936L;
	@XStreamAlias("BatchInfo")
	private BatchInfo batchInfo;
	@XStreamImplicit
	private List<TxnPayReturn> txnPayReturnList;


	
	
	
}
