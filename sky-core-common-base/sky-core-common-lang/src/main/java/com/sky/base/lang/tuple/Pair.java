package com.sky.base.lang.tuple;

/**
 * @Title
 * @Description
 * @author lizp
 * @version 1.0.0
 * @date 2019-02-19
 */
public class Pair<L, R> {

	private L left;

	private R right;

	public static <L, R> Pair<L, R> valueOf(L left, R right) {
		return new Pair<L, R>(left, right);
	}

	private Pair(L left, R right) {
		super();
		this.left = left;
		this.right = right;
	}

	public L getLeft() {
		return left;
	}

	public void setLeft(L left) {
		this.left = left;
	}

	public R getRight() {
		return right;
	}

	public void setRight(R right) {
		this.right = right;
	}

}
