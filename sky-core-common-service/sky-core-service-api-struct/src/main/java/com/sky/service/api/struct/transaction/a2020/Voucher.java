package com.sky.service.api.struct.transaction.a2020;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

@XStreamAlias("Voucher")
public class Voucher implements Serializable {

    private static final long serialVersionUID = 4284067289762955131L;
    @XStreamAlias("Voucher")
    private String voucherNo;
}
