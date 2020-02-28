package com.sky.base.jdbc.support;

/**
 * @Title 受影响行数参照
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-03-02
 */
public class AffectedRowsRefer {

	public static final int AFFECTED_COUNT_ONE = 1;

	private int expectedAffectedRowsCount;

	private int actualAffectedRowsCount;

	public static AffectedRowsRefer of(int actual) {
		return new AffectedRowsRefer(AFFECTED_COUNT_ONE, actual);
	}

	public static AffectedRowsRefer of(int expected, int actual) {
		return new AffectedRowsRefer(expected, actual);
	}

	private AffectedRowsRefer(int expectedAffectedRowsCount, int actualAffectedRowsCount) {
		super();
		this.expectedAffectedRowsCount = expectedAffectedRowsCount;
		this.actualAffectedRowsCount = actualAffectedRowsCount;
	}

	public boolean isSuccess() {
		return expectedAffectedRowsCount == actualAffectedRowsCount;
	}

	public int affectedRowsCount() {
		return actualAffectedRowsCount;
	}
}
