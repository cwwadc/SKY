package com.sky.service.api.support.chain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.service.api.support.ApiContext;

public class TransactionCallbackHandlerChain
		implements TransactionCallback, TransactionCallbackBeforeReturn, TransactionFinishNotify {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionCallbackHandlerChain.class);

	@Override
	public void onTransactionCallbackBeforeReturn(ApiContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTransactionCallback(ApiContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTransactionFinishNotify(ApiContext context) {
		// TODO Auto-generated method stub
		
	}

}
