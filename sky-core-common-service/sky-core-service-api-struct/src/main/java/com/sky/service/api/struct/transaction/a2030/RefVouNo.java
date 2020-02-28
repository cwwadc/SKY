package com.sky.service.api.struct.transaction.a2030;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;

@XStreamAlias("RefVouNo")
@Data
public class RefVouNo implements Serializable {


    private static final long serialVersionUID = 9008341784427106314L;
    @XStreamAlias("VoucherNo")
    private String voucherNo;
}
