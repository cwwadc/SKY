package com.sky.base.jdbc.support;

import com.sky.base.jdbc.support.BaseBatchOperater;

/**
 * 
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-03-19
 */
public class MockBatchOperater extends BaseBatchOperater<String> {

	private boolean processed = false;

	@Override
	public int commit() {
		processed = true;
		return 0;
	}

	public boolean isProcessed() {
		return processed;
	}

}
