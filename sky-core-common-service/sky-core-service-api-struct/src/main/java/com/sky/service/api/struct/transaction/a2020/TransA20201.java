package com.sky.service.api.struct.transaction.a2020;

import com.sky.service.api.struct.domain.SkyMsgBody;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiSerializeType("A20201")
@XStreamAlias("BillCreateInfo")
@Data
public class TransA20201 extends SkyMsgBody implements Serializable {


	private static final long serialVersionUID = -4552087797346859352L;
	@XStreamAlias("BankId")
	private String bankId;
	@XStreamAlias("BankDate")
	private String bankDate;
	@XStreamAlias("BankMid")
	private String bankMid;
	@XStreamAlias("VouNoCnt")
	private String vouNoCnt;
	@XStreamImplicit
	private List<Voucher> voucherList;
}
