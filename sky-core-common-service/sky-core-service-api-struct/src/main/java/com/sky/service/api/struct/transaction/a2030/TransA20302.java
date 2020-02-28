package com.sky.service.api.struct.transaction.a2030;


import com.sky.service.api.struct.transaction.a2010.WaitPayment;
import com.sky.service.api.support.serialize.ApiSerializeType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiSerializeType("A20302")
@XStreamAlias("BillQueryInfo")
@Data
public class TransA20302 implements Serializable {


    private static final long serialVersionUID = 2440118899150704561L;
    @XStreamAlias("OrgId")
    private String orgId;
    @XStreamAlias("OrgDate")
    private String orgDate;
    @XStreamAlias("OrgMid")
    private String orgMid;
    @XStreamAlias("BankId")
    private String bankId;
    @XStreamAlias("BankDate")
    private Date bankDate;
    @XStreamAlias("BankMid")
    private String bankMid;
    @XStreamAlias("Amount")
    private String amount;
    @XStreamAlias("FiscId")
    private String fiscId;
    @XStreamAlias("FiscName")
    private String fiscName;
    @XStreamAlias("TaxInfo")
    private TaxInfo taxInfo;

}
