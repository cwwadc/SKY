package com.sky.base.jdbc.support;

import static com.sky.base.test.util.MatcherUtils.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * 
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-03-20
 */
public class BaseBatchOperaterTest {

	@Test
	public void testCommit() {
		MockBatchOperater operater = new MockBatchOperater();
		List<String> objects = new ArrayList<>();
		for (int i = 0; i < 10000; i++) {
			objects.add(String.valueOf(i));
		}
		operater.execute(objects);
		assertTrue(operater.isProcessed());
		assertTrue(operater.getWaitingCommitObjects().isEmpty());
	}

	@Test
	public void testCommit2() {
		MockBatchOperater operater = new MockBatchOperater();
		List<String> objects = new ArrayList<>();
		for (int i = 0; i < 12000; i++) {
			objects.add(String.valueOf(i));
		}
		operater.execute(objects);
		assertTrue(operater.isProcessed());
		assertTrue(operater.getWaitingCommitObjects().isEmpty());
	}

	@Test
	public void testCommitNull() {
		MockBatchOperater operater = new MockBatchOperater();
		operater.execute(null);
		assertFalse(operater.isProcessed());
		assertTrue(operater.getWaitingCommitObjects().isEmpty());
	}

	@Test
	public void testCommitEmpty() {
		MockBatchOperater operater = new MockBatchOperater();
		operater.forceCommit();
		assertFalse(operater.isProcessed());
		assertTrue(operater.getWaitingCommitObjects().isEmpty());
	}

	@Test
	public void testUnhappenCommit() {
		MockBatchOperater operater = new MockBatchOperater();
		List<String> objects = new ArrayList<>();
		for (int i = 0; i < 9999; i++) {
			objects.add(String.valueOf(i));
		}
		operater.execute(objects);
		assertFalse(operater.isProcessed());
		assertThat(operater.getWaitingCommitObjects().size(), is(9999));
	}

	@Test
	public void testForceCommit() {
		MockBatchOperater operater = new MockBatchOperater();
		List<String> objects = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			objects.add(String.valueOf(i));
		}
		operater.execute(objects);
		operater.forceCommit();
		assertTrue(operater.isProcessed());
		assertTrue(operater.getWaitingCommitObjects().isEmpty());
	}

}
