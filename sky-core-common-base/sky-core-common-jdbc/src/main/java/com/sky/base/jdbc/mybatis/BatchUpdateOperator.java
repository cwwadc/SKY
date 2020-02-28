package com.sky.base.jdbc.mybatis;

import org.apache.ibatis.executor.BatchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

import java.util.List;

/**
 * @author lizp
 * @version 1.0.0
 * @Title
 * @Description
 * @date 2019/4/25
 */
public class BatchUpdateOperator extends BatchSqlSessionTemplate {

	private static final Logger LOGGER = LoggerFactory.getLogger(BatchUpdateOperator.class);

	private boolean assertUpdates = true;

	public void setAssertUpdates(boolean assertUpdates) {
		this.assertUpdates = assertUpdates;
	}

	public <T> void update(final String statementId, final List<T> items) {

		if (!items.isEmpty()) {

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Executing batch with " + items.size() + " items.");
			}

			for (T item : items) {
				getSqlSessionTemplate().update(statementId, item);
			}

			List<BatchResult> results = getSqlSessionTemplate().flushStatements();

			if (assertUpdates) {
				if (results.size() != 1) {
					throw new InvalidDataAccessResourceUsageException("Batch execution returned invalid results. "
					        + "Expected 1 but number of BatchResult objects returned was " + results.size());
				}

				int[] updateCounts = results.get(0).getUpdateCounts();

				for (int i = 0; i < updateCounts.length; i++) {
					int value = updateCounts[i];
					if (value == 0) {
						throw new EmptyResultDataAccessException("Item " + i + " of " + updateCounts.length
						        + " did not update any rows: [" + items.get(i) + "]", 1);
					}
				}
			}
		}
	}

}
