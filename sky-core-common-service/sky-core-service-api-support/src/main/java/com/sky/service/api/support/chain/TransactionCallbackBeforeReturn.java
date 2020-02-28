package com.sky.service.api.support.chain;

import com.sky.service.api.support.ApiContext;

public interface TransactionCallbackBeforeReturn {
	
	public void onTransactionCallbackBeforeReturn(ApiContext context);
}
