package com.sky.service.api.struct.transaction.b1060;

import com.sky.service.api.struct.domain.BatchInfo;
import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiSerializeType("B10601")
@Data
public class TransB10601 extends SkyMsgBody implements Serializable {

	private static final long serialVersionUID = 7797915623589299030L;
	@XStreamAlias("BatchInfo")
	private BatchInfo batchInfo;
	@XStreamImplicit
	private List<TaxReportInfo> taxReportInfoList;

}
