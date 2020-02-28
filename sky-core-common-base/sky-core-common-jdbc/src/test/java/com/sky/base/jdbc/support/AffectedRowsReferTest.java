package com.sky.base.jdbc.support;

import static com.sky.base.test.util.MatcherUtils.is;
import static org.junit.Assert.*;

import org.junit.Test;

import com.sky.base.jdbc.support.AffectedRowsRefer;

/**
 * 
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-03-02
 */
public class AffectedRowsReferTest {

	@Test
	public void test() {
		assertTrue(AffectedRowsRefer.of(1).isSuccess());
		assertFalse(AffectedRowsRefer.of(2).isSuccess());
		assertFalse(AffectedRowsRefer.of(0).isSuccess());
		assertFalse(AffectedRowsRefer.of(-1).isSuccess());
		assertFalse(AffectedRowsRefer.of(Integer.MAX_VALUE).isSuccess());
		assertFalse(AffectedRowsRefer.of(Integer.MIN_VALUE).isSuccess());

		assertTrue(AffectedRowsRefer.of(2, 2).isSuccess());
		assertFalse(AffectedRowsRefer.of(3, 2).isSuccess());
		assertFalse(AffectedRowsRefer.of(1, 0).isSuccess());
		assertFalse(AffectedRowsRefer.of(-2, -1).isSuccess());
		assertFalse(AffectedRowsRefer.of(2, Integer.MAX_VALUE).isSuccess());
		assertFalse(AffectedRowsRefer.of(3, Integer.MIN_VALUE).isSuccess());
	}

	@Test
	public void testAffectedRowsCount() {
		assertThat(AffectedRowsRefer.of(1).affectedRowsCount(), is(1));
		assertThat(AffectedRowsRefer.of(-5).affectedRowsCount(), is(-5));
		assertThat(AffectedRowsRefer.of(-5, 0).affectedRowsCount(), is(0));
		assertThat(AffectedRowsRefer.of(1, 2).affectedRowsCount(), is(2));
		assertThat(AffectedRowsRefer.of(Integer.MAX_VALUE, 3).affectedRowsCount(), is(3));
		assertThat(AffectedRowsRefer.of(Integer.MIN_VALUE, 0).affectedRowsCount(), is(0));
	}

}
