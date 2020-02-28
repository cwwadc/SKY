package com.sky.base.jdbc.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.base.lang.collection.CollectionUtils;

/**
 * @Title 基础批量操作员
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-03-16
 */
public abstract class BaseBatchOperater<T> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected List<T> waitingCommitObjects = newArrayList();

	public int execute(List<T> objects) {
		if (CollectionUtils.isEmpty(objects)) {
			return 0;
		}
		waitingCommitObjects.addAll(objects);
		if (waitingCommitObjects.size() >= maximumBatchQuantity()) {
			return commitAndReset();
		}
		return 0;
	}

	protected int commitAndReset() {
		int count = commit();
		waitingCommitObjects = newArrayList();
		return count;
	}

	private ArrayList<T> newArrayList() {
		return new ArrayList<>(256);
	}

	public int forceCommit() {
		if (!isEmptyObjectList()) {
			return commitAndReset();
		}
		return 0;
	}

	protected abstract int commit();

	protected int maximumBatchQuantity() {
		return 10000;
	}

	private boolean isEmptyObjectList() {
		return CollectionUtils.isEmpty(waitingCommitObjects);
	}

	public List<T> getWaitingCommitObjects() {
		return Collections.unmodifiableList(waitingCommitObjects);
	}

}
