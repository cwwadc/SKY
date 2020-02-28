/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sky.base.lang.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.commons.collections4.ComparatorUtils;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.junit.Before;
import org.junit.Test;

import com.sky.base.lang.collection.CollectionUtils;

/**
 * @title Tests for CollectionUtils.
 * @description 复用CollectionUtils的单元测试保证装饰后的CollectionUtils工作正确
 * @author lizp
 * @version 1.0.0
 * @date 2019-06-12
 */
public class CollectionUtilsTest extends MockTestCase {

	/**
	 * Collection of {@link Integer}s
	 */
	private List<Integer> collectionA = null;

	/**
	 * Collection of {@link Long}s
	 */
	private List<Long> collectionB = null;

	/**
	 * Collection of {@link Integer}s that are equivalent to the Longs in
	 * collectionB.
	 */
	private Collection<Integer> collectionC = null;

	/**
	 * Sorted Collection of {@link Integer}s
	 */
	private Collection<Integer> collectionD = null;

	/**
	 * Sorted Collection of {@link Integer}s
	 */
	private Collection<Integer> collectionE = null;

	/**
	 * Collection of {@link Integer}s (cast as {@link Number}s) that are equivalent
	 * to the Longs in collectionB.
	 */
	private Collection<Number> collectionC2 = null;

	private Iterable<Integer> iterableA = null;

	private Iterable<Long> iterableB = null;

	private Iterable<Integer> iterableC = null;

	private final Collection<Integer> emptyCollection = new ArrayList<>(1);

	@Before
	public void setUp() {
		collectionA = new ArrayList<>();
		collectionA.add(1);
		collectionA.add(2);
		collectionA.add(2);
		collectionA.add(3);
		collectionA.add(3);
		collectionA.add(3);
		collectionA.add(4);
		collectionA.add(4);
		collectionA.add(4);
		collectionA.add(4);
		collectionB = new LinkedList<>();
		collectionB.add(5L);
		collectionB.add(4L);
		collectionB.add(4L);
		collectionB.add(3L);
		collectionB.add(3L);
		collectionB.add(3L);
		collectionB.add(2L);
		collectionB.add(2L);
		collectionB.add(2L);
		collectionB.add(2L);

		collectionC = new ArrayList<>();
		for (final Long l : collectionB) {
			collectionC.add(l.intValue());
		}

		iterableA = collectionA;
		iterableB = collectionB;
		iterableC = collectionC;
		new ArrayList<>(collectionA);
		new LinkedList<>(collectionB);
		collectionC2 = new LinkedList<>(collectionC);
		collectionD = new ArrayList<>();
		collectionD.add(1);
		collectionD.add(3);
		collectionD.add(3);
		collectionD.add(3);
		collectionD.add(5);
		collectionD.add(7);
		collectionD.add(7);
		collectionD.add(10);

		collectionE = new ArrayList<>();
		collectionE.add(2);
		collectionE.add(4);
		collectionE.add(4);
		collectionE.add(5);
		collectionE.add(6);
		collectionE.add(6);
		collectionE.add(9);
	}

	@Test
	public void getCardinalityMap() {
		final Map<Number, Integer> freqA = CollectionUtils.<Number>getCardinalityMap(iterableA);
		assertEquals(1, (int) freqA.get(1));
		assertEquals(2, (int) freqA.get(2));
		assertEquals(3, (int) freqA.get(3));
		assertEquals(4, (int) freqA.get(4));
		assertNull(freqA.get(5));

		final Map<Long, Integer> freqB = CollectionUtils.getCardinalityMap(iterableB);
		assertNull(freqB.get(1L));
		assertEquals(4, (int) freqB.get(2L));
		assertEquals(3, (int) freqB.get(3L));
		assertEquals(2, (int) freqB.get(4L));
		assertEquals(1, (int) freqB.get(5L));
	}

	@Test
	public void containsAll() {
		final Collection<String> empty = new ArrayList<>(0);
		final Collection<String> one = new ArrayList<>(1);
		one.add("1");
		final Collection<String> two = new ArrayList<>(1);
		two.add("2");
		final Collection<String> three = new ArrayList<>(1);
		three.add("3");
		final Collection<String> odds = new ArrayList<>(2);
		odds.add("1");
		odds.add("3");
		final Collection<String> multiples = new ArrayList<>(3);
		multiples.add("1");
		multiples.add("3");
		multiples.add("1");

		assertTrue("containsAll({1},{1,3}) should return false.", !CollectionUtils.containsAll(one, odds));
		assertTrue("containsAll({1,3},{1}) should return true.", CollectionUtils.containsAll(odds, one));
		assertTrue("containsAll({3},{1,3}) should return false.", !CollectionUtils.containsAll(three, odds));
		assertTrue("containsAll({1,3},{3}) should return true.", CollectionUtils.containsAll(odds, three));
		assertTrue("containsAll({2},{2}) should return true.", CollectionUtils.containsAll(two, two));
		assertTrue("containsAll({1,3},{1,3}) should return true.", CollectionUtils.containsAll(odds, odds));

		assertTrue("containsAll({2},{1,3}) should return false.", !CollectionUtils.containsAll(two, odds));
		assertTrue("containsAll({1,3},{2}) should return false.", !CollectionUtils.containsAll(odds, two));
		assertTrue("containsAll({1},{3}) should return false.", !CollectionUtils.containsAll(one, three));
		assertTrue("containsAll({3},{1}) should return false.", !CollectionUtils.containsAll(three, one));
		assertTrue("containsAll({1,3},{}) should return true.", CollectionUtils.containsAll(odds, empty));
		assertTrue("containsAll({},{1,3}) should return false.", !CollectionUtils.containsAll(empty, odds));
		assertTrue("containsAll({},{}) should return true.", CollectionUtils.containsAll(empty, empty));

		assertTrue("containsAll({1,3},{1,3,1}) should return true.", CollectionUtils.containsAll(odds, multiples));
		assertTrue("containsAll({1,3,1},{1,3,1}) should return true.", CollectionUtils.containsAll(odds, odds));
	}

	@Test
	public void containsAnyInCollection() {
		final Collection<String> empty = new ArrayList<>(0);
		final Collection<String> one = new ArrayList<>(1);
		one.add("1");
		final Collection<String> two = new ArrayList<>(1);
		two.add("2");
		final Collection<String> three = new ArrayList<>(1);
		three.add("3");
		final Collection<String> odds = new ArrayList<>(2);
		odds.add("1");
		odds.add("3");

		assertTrue("containsAny({1},{1,3}) should return true.", CollectionUtils.containsAny(one, odds));
		assertTrue("containsAny({1,3},{1}) should return true.", CollectionUtils.containsAny(odds, one));
		assertTrue("containsAny({3},{1,3}) should return true.", CollectionUtils.containsAny(three, odds));
		assertTrue("containsAny({1,3},{3}) should return true.", CollectionUtils.containsAny(odds, three));
		assertTrue("containsAny({2},{2}) should return true.", CollectionUtils.containsAny(two, two));
		assertTrue("containsAny({1,3},{1,3}) should return true.", CollectionUtils.containsAny(odds, odds));

		assertTrue("containsAny({2},{1,3}) should return false.", !CollectionUtils.containsAny(two, odds));
		assertTrue("containsAny({1,3},{2}) should return false.", !CollectionUtils.containsAny(odds, two));
		assertTrue("containsAny({1},{3}) should return false.", !CollectionUtils.containsAny(one, three));
		assertTrue("containsAny({3},{1}) should return false.", !CollectionUtils.containsAny(three, one));
		assertTrue("containsAny({1,3},{}) should return false.", !CollectionUtils.containsAny(odds, empty));
		assertTrue("containsAny({},{1,3}) should return false.", !CollectionUtils.containsAny(empty, odds));
		assertTrue("containsAny({},{}) should return false.", !CollectionUtils.containsAny(empty, empty));
	}

	@Test
	public void containsAnyInArray() {
		final Collection<String> empty = new ArrayList<>(0);
		final String[] emptyArr = {};
		final Collection<String> one = new ArrayList<>(1);
		one.add("1");
		final String[] oneArr = { "1" };
		final Collection<String> two = new ArrayList<>(1);
		two.add("2");
		final String[] twoArr = { "2" };
		final Collection<String> three = new ArrayList<>(1);
		three.add("3");
		final String[] threeArr = { "3" };
		final Collection<String> odds = new ArrayList<>(2);
		odds.add("1");
		odds.add("3");
		final String[] oddsArr = { "1", "3" };

		assertTrue("containsAny({1},{1,3}) should return true.", CollectionUtils.containsAny(one, oddsArr));
		assertTrue("containsAny({1,3},{1}) should return true.", CollectionUtils.containsAny(odds, oneArr));
		assertTrue("containsAny({3},{1,3}) should return true.", CollectionUtils.containsAny(three, oddsArr));
		assertTrue("containsAny({1,3},{3}) should return true.", CollectionUtils.containsAny(odds, threeArr));
		assertTrue("containsAny({2},{2}) should return true.", CollectionUtils.containsAny(two, twoArr));
		assertTrue("containsAny({1,3},{1,3}) should return true.", CollectionUtils.containsAny(odds, oddsArr));

		assertTrue("containsAny({2},{1,3}) should return false.", !CollectionUtils.containsAny(two, oddsArr));
		assertTrue("containsAny({1,3},{2}) should return false.", !CollectionUtils.containsAny(odds, twoArr));
		assertTrue("containsAny({1},{3}) should return false.", !CollectionUtils.containsAny(one, threeArr));
		assertTrue("containsAny({3},{1}) should return false.", !CollectionUtils.containsAny(three, oneArr));
		assertTrue("containsAny({1,3},{}) should return false.", !CollectionUtils.containsAny(odds, emptyArr));
		assertTrue("containsAny({},{1,3}) should return false.", !CollectionUtils.containsAny(empty, oddsArr));
		assertTrue("containsAny({},{}) should return false.", !CollectionUtils.containsAny(empty, emptyArr));
	}

	@Test
	public void union() {
		final Collection<Integer> col = CollectionUtils.union(iterableA, iterableC);
		final Map<Integer, Integer> freq = CollectionUtils.getCardinalityMap(col);
		assertEquals(Integer.valueOf(1), freq.get(1));
		assertEquals(Integer.valueOf(4), freq.get(2));
		assertEquals(Integer.valueOf(3), freq.get(3));
		assertEquals(Integer.valueOf(4), freq.get(4));
		assertEquals(Integer.valueOf(1), freq.get(5));

		final Collection<Number> col2 = CollectionUtils.union(collectionC2, iterableA);
		final Map<Number, Integer> freq2 = CollectionUtils.getCardinalityMap(col2);
		assertEquals(Integer.valueOf(1), freq2.get(1));
		assertEquals(Integer.valueOf(4), freq2.get(2));
		assertEquals(Integer.valueOf(3), freq2.get(3));
		assertEquals(Integer.valueOf(4), freq2.get(4));
		assertEquals(Integer.valueOf(1), freq2.get(5));
	}

	@Test
	public void intersection() {
		final Collection<Integer> col = CollectionUtils.intersection(iterableA, iterableC);
		final Map<Integer, Integer> freq = CollectionUtils.getCardinalityMap(col);
		assertNull(freq.get(1));
		assertEquals(Integer.valueOf(2), freq.get(2));
		assertEquals(Integer.valueOf(3), freq.get(3));
		assertEquals(Integer.valueOf(2), freq.get(4));
		assertNull(freq.get(5));

		final Collection<Number> col2 = CollectionUtils.intersection(collectionC2, collectionA);
		final Map<Number, Integer> freq2 = CollectionUtils.getCardinalityMap(col2);
		assertNull(freq2.get(1));
		assertEquals(Integer.valueOf(2), freq2.get(2));
		assertEquals(Integer.valueOf(3), freq2.get(3));
		assertEquals(Integer.valueOf(2), freq2.get(4));
		assertNull(freq2.get(5));
	}

	@Test
	public void disjunction() {
		final Collection<Integer> col = CollectionUtils.disjunction(iterableA, iterableC);
		final Map<Integer, Integer> freq = CollectionUtils.getCardinalityMap(col);
		assertEquals(Integer.valueOf(1), freq.get(1));
		assertEquals(Integer.valueOf(2), freq.get(2));
		assertNull(freq.get(3));
		assertEquals(Integer.valueOf(2), freq.get(4));
		assertEquals(Integer.valueOf(1), freq.get(5));

		final Collection<Number> col2 = CollectionUtils.disjunction(collectionC2, collectionA);
		final Map<Number, Integer> freq2 = CollectionUtils.getCardinalityMap(col2);
		assertEquals(Integer.valueOf(1), freq2.get(1));
		assertEquals(Integer.valueOf(2), freq2.get(2));
		assertNull(freq2.get(3));
		assertEquals(Integer.valueOf(2), freq2.get(4));
		assertEquals(Integer.valueOf(1), freq2.get(5));
	}

	@Test
	public void testDisjunctionAsUnionMinusIntersection() {
		final Collection<Number> dis = CollectionUtils.<Number>disjunction(collectionA, collectionC);
		final Collection<Number> un = CollectionUtils.<Number>union(collectionA, collectionC);
		final Collection<Number> inter = CollectionUtils.<Number>intersection(collectionA, collectionC);
		assertTrue(CollectionUtils.isEqualCollection(dis, CollectionUtils.subtract(un, inter)));
	}

	@Test
	public void testDisjunctionAsSymmetricDifference() {
		final Collection<Number> dis = CollectionUtils.<Number>disjunction(collectionA, collectionC);
		final Collection<Number> amb = CollectionUtils.<Number>subtract(collectionA, collectionC);
		final Collection<Number> bma = CollectionUtils.<Number>subtract(collectionC, collectionA);
		assertTrue(CollectionUtils.isEqualCollection(dis, CollectionUtils.union(amb, bma)));
	}

	@Test
	public void testSubtract() {
		final Collection<Integer> col = CollectionUtils.subtract(iterableA, iterableC);
		final Map<Integer, Integer> freq = CollectionUtils.getCardinalityMap(col);
		assertEquals(Integer.valueOf(1), freq.get(1));
		assertNull(freq.get(2));
		assertNull(freq.get(3));
		assertEquals(Integer.valueOf(2), freq.get(4));
		assertNull(freq.get(5));

		final Collection<Number> col2 = CollectionUtils.subtract(collectionC2, collectionA);
		final Map<Number, Integer> freq2 = CollectionUtils.getCardinalityMap(col2);
		assertEquals(Integer.valueOf(1), freq2.get(5));
		assertNull(freq2.get(4));
		assertNull(freq2.get(3));
		assertEquals(Integer.valueOf(2), freq2.get(2));
		assertNull(freq2.get(1));
	}

	@Test
	public void testIsSubCollectionOfSelf() {
		assertTrue(CollectionUtils.isSubCollection(collectionA, collectionA));
		assertTrue(CollectionUtils.isSubCollection(collectionB, collectionB));
	}

	@Test
	public void testIsSubCollection() {
		assertTrue(!CollectionUtils.isSubCollection(collectionA, collectionC));
		assertTrue(!CollectionUtils.isSubCollection(collectionC, collectionA));
	}

	@Test
	public void testIsSubCollection2() {
		final Collection<Integer> c = new ArrayList<>();
		assertTrue(CollectionUtils.isSubCollection(c, collectionA));
		assertTrue(!CollectionUtils.isSubCollection(collectionA, c));
		c.add(1);
		assertTrue(CollectionUtils.isSubCollection(c, collectionA));
		assertTrue(!CollectionUtils.isSubCollection(collectionA, c));
		c.add(2);
		assertTrue(CollectionUtils.isSubCollection(c, collectionA));
		assertTrue(!CollectionUtils.isSubCollection(collectionA, c));
		c.add(2);
		assertTrue(CollectionUtils.isSubCollection(c, collectionA));
		assertTrue(!CollectionUtils.isSubCollection(collectionA, c));
		c.add(3);
		assertTrue(CollectionUtils.isSubCollection(c, collectionA));
		assertTrue(!CollectionUtils.isSubCollection(collectionA, c));
		c.add(3);
		assertTrue(CollectionUtils.isSubCollection(c, collectionA));
		assertTrue(!CollectionUtils.isSubCollection(collectionA, c));
		c.add(3);
		assertTrue(CollectionUtils.isSubCollection(c, collectionA));
		assertTrue(!CollectionUtils.isSubCollection(collectionA, c));
		c.add(4);
		assertTrue(CollectionUtils.isSubCollection(c, collectionA));
		assertTrue(!CollectionUtils.isSubCollection(collectionA, c));
		c.add(4);
		assertTrue(CollectionUtils.isSubCollection(c, collectionA));
		assertTrue(!CollectionUtils.isSubCollection(collectionA, c));
		c.add(4);
		assertTrue(CollectionUtils.isSubCollection(c, collectionA));
		assertTrue(!CollectionUtils.isSubCollection(collectionA, c));
		c.add(4);
		assertTrue(CollectionUtils.isSubCollection(c, collectionA));
		assertTrue(CollectionUtils.isSubCollection(collectionA, c));
		c.add(5);
		assertTrue(!CollectionUtils.isSubCollection(c, collectionA));
		assertTrue(CollectionUtils.isSubCollection(collectionA, c));
	}

	@Test
	public void testIsEqualCollectionToSelf() {
		assertTrue(CollectionUtils.isEqualCollection(collectionA, collectionA));
		assertTrue(CollectionUtils.isEqualCollection(collectionB, collectionB));
	}

	@Test
	public void testIsEqualCollection() {
		assertTrue(!CollectionUtils.isEqualCollection(collectionA, collectionC));
		assertTrue(!CollectionUtils.isEqualCollection(collectionC, collectionA));
	}

	@Test
	public void testIsEqualCollectionReturnsFalse() {
		final List<Integer> b = new ArrayList<>(collectionA);
		// remove an extra '2', and add a 5. This will increase the size of the
		// cardinality
		b.remove(1);
		b.add(5);
		assertFalse(CollectionUtils.isEqualCollection(collectionA, b));
		assertFalse(CollectionUtils.isEqualCollection(b, collectionA));
	}

	@Test
	public void testIsEqualCollection2() {
		final Collection<String> a = new ArrayList<>();
		final Collection<String> b = new ArrayList<>();
		assertTrue(CollectionUtils.isEqualCollection(a, b));
		assertTrue(CollectionUtils.isEqualCollection(b, a));
		a.add("1");
		assertTrue(!CollectionUtils.isEqualCollection(a, b));
		assertTrue(!CollectionUtils.isEqualCollection(b, a));
		b.add("1");
		assertTrue(CollectionUtils.isEqualCollection(a, b));
		assertTrue(CollectionUtils.isEqualCollection(b, a));
		a.add("2");
		assertTrue(!CollectionUtils.isEqualCollection(a, b));
		assertTrue(!CollectionUtils.isEqualCollection(b, a));
		b.add("2");
		assertTrue(CollectionUtils.isEqualCollection(a, b));
		assertTrue(CollectionUtils.isEqualCollection(b, a));
		a.add("1");
		assertTrue(!CollectionUtils.isEqualCollection(a, b));
		assertTrue(!CollectionUtils.isEqualCollection(b, a));
		b.add("1");
		assertTrue(CollectionUtils.isEqualCollection(a, b));
		assertTrue(CollectionUtils.isEqualCollection(b, a));
	}

	@Test
	public void testIsProperSubCollection() {
		final Collection<String> a = new ArrayList<>();
		final Collection<String> b = new ArrayList<>();
		assertTrue(!CollectionUtils.isProperSubCollection(a, b));
		b.add("1");
		assertTrue(CollectionUtils.isProperSubCollection(a, b));
		assertTrue(!CollectionUtils.isProperSubCollection(b, a));
		assertTrue(!CollectionUtils.isProperSubCollection(b, b));
		assertTrue(!CollectionUtils.isProperSubCollection(a, a));
		a.add("1");
		a.add("2");
		b.add("2");
		assertTrue(!CollectionUtils.isProperSubCollection(b, a));
		assertTrue(!CollectionUtils.isProperSubCollection(a, b));
		a.add("1");
		assertTrue(CollectionUtils.isProperSubCollection(b, a));
		assertTrue(CollectionUtils.isProperSubCollection(CollectionUtils.intersection(collectionA, collectionC),
		        collectionA));
		assertTrue(CollectionUtils.isProperSubCollection(CollectionUtils.subtract(a, b), a));
		assertTrue(!CollectionUtils.isProperSubCollection(a, CollectionUtils.subtract(a, b)));
	}

	@Test
	public void getFromMap() {
		// Unordered map, entries exist
		final Map<String, String> expected = new HashMap<>();
		expected.put("zeroKey", "zero");
		expected.put("oneKey", "one");

		final Map<String, String> found = new HashMap<>();
		Map.Entry<String, String> entry = CollectionUtils.get(expected, 0);
		found.put(entry.getKey(), entry.getValue());
		entry = CollectionUtils.get(expected, 1);
		found.put(entry.getKey(), entry.getValue());
		assertEquals(expected, found);

		// Map index out of range
		try {
			CollectionUtils.get(expected, 2);
			fail("Expecting IndexOutOfBoundsException.");
		} catch (final IndexOutOfBoundsException e) {
			// expected
		}
		try {
			CollectionUtils.get(expected, -2);
			fail("Expecting IndexOutOfBoundsException.");
		} catch (final IndexOutOfBoundsException e) {
			// expected
		}

		// Sorted map, entries exist, should respect order
		final SortedMap<String, String> map = new TreeMap<>();
		map.put("zeroKey", "zero");
		map.put("oneKey", "one");
		Map.Entry<String, String> test = CollectionUtils.get(map, 1);
		assertEquals("zeroKey", test.getKey());
		assertEquals("zero", test.getValue());
		test = CollectionUtils.get(map, 0);
		assertEquals("oneKey", test.getKey());
		assertEquals("one", test.getValue());
	}

	/**
	 * Tests that {@link List}s are handled correctly - e.g. using
	 * {@link List#get(int)}.
	 */
	@Test(expected = IndexOutOfBoundsException.class)
	public void getFromList() throws Exception {
		// List, entry exists
		final List<String> list = createMock(List.class);
		expect(list.get(0)).andReturn("zero");
		expect(list.get(1)).andReturn("one");
		replay();
		final String string = (String) CollectionUtils.get(list, 0);
		assertEquals("zero", string);
		assertEquals("one", CollectionUtils.get(list, 1));
		// list, non-existent entry -- IndexOutOfBoundsException
		CollectionUtils.get(new ArrayList<>(), 2);
	}

	@Test
	@Deprecated
	public void getFromIterator() throws Exception {
		// Iterator, entry exists
		Iterator<Integer> iterator = iterableA.iterator();
		assertEquals(1, (int) CollectionUtils.get(iterator, 0));
		iterator = iterableA.iterator();
		assertEquals(2, (int) CollectionUtils.get(iterator, 1));

		// Iterator, non-existent entry
		try {
			CollectionUtils.get(iterator, 10);
			fail("Expecting IndexOutOfBoundsException.");
		} catch (final IndexOutOfBoundsException e) {
			// expected
		}
		assertTrue(!iterator.hasNext());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void getFromObjectArray() throws Exception {
		// Object array, entry exists
		final Object[] objArray = new Object[2];
		objArray[0] = "zero";
		objArray[1] = "one";
		assertEquals("zero", CollectionUtils.get(objArray, 0));
		assertEquals("one", CollectionUtils.get(objArray, 1));

		// Object array, non-existent entry --
		// ArrayIndexOutOfBoundsException
		CollectionUtils.get(objArray, 2);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void getFromPrimitiveArray() throws Exception {
		// Primitive array, entry exists
		final int[] array = new int[2];
		array[0] = 10;
		array[1] = 20;
		assertEquals(10, CollectionUtils.get(array, 0));
		assertEquals(20, CollectionUtils.get(array, 1));

		// Object array, non-existent entry --
		// ArrayIndexOutOfBoundsException
		CollectionUtils.get(array, 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getFromObject() throws Exception {
		// Invalid object
		final Object obj = new Object();
		CollectionUtils.get(obj, 0);
	}

	// -----------------------------------------------------------------------
	@Test
	public void testSize_List() {
		List<String> list = null;
		assertEquals(0, CollectionUtils.size(list));
		list = new ArrayList<>();
		assertEquals(0, CollectionUtils.size(list));
		list.add("a");
		assertEquals(1, CollectionUtils.size(list));
		list.add("b");
		assertEquals(2, CollectionUtils.size(list));
	}

	@Test
	public void testSize_Map() {
		final Map<String, String> map = new HashMap<>();
		assertEquals(0, CollectionUtils.size(map));
		map.put("1", "a");
		assertEquals(1, CollectionUtils.size(map));
		map.put("2", "b");
		assertEquals(2, CollectionUtils.size(map));
	}

	@Test
	public void testSize_Array() {
		final Object[] objectArray = new Object[0];
		assertEquals(0, CollectionUtils.size(objectArray));

		final String[] stringArray = new String[3];
		assertEquals(3, CollectionUtils.size(stringArray));
		stringArray[0] = "a";
		stringArray[1] = "b";
		stringArray[2] = "c";
		assertEquals(3, CollectionUtils.size(stringArray));
	}

	@Test
	public void testSize_PrimitiveArray() {
		final int[] intArray = new int[0];
		assertEquals(0, CollectionUtils.size(intArray));

		final double[] doubleArray = new double[3];
		assertEquals(3, CollectionUtils.size(doubleArray));
		doubleArray[0] = 0.0d;
		doubleArray[1] = 1.0d;
		doubleArray[2] = 2.5d;
		assertEquals(3, CollectionUtils.size(doubleArray));
	}

	@Test
	public void testSize_Enumeration() {
		final Vector<String> list = new Vector<>();
		assertEquals(0, CollectionUtils.size(list.elements()));
		list.add("a");
		assertEquals(1, CollectionUtils.size(list.elements()));
		list.add("b");
		assertEquals(2, CollectionUtils.size(list.elements()));
	}

	@Test
	public void testSize_Iterator() {
		final List<String> list = new ArrayList<>();
		assertEquals(0, CollectionUtils.size(list.iterator()));
		list.add("a");
		assertEquals(1, CollectionUtils.size(list.iterator()));
		list.add("b");
		assertEquals(2, CollectionUtils.size(list.iterator()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSize_Other() {
		CollectionUtils.size("not a list");
	}

	// -----------------------------------------------------------------------
	@Test
	public void testSizeIsEmpty_Null() {
		assertEquals(true, CollectionUtils.sizeIsEmpty(null));
	}

	@Test
	public void testSizeIsEmpty_List() {
		final List<String> list = new ArrayList<>();
		assertEquals(true, CollectionUtils.sizeIsEmpty(list));
		list.add("a");
		assertEquals(false, CollectionUtils.sizeIsEmpty(list));
	}

	@Test
	public void testSizeIsEmpty_Map() {
		final Map<String, String> map = new HashMap<>();
		assertEquals(true, CollectionUtils.sizeIsEmpty(map));
		map.put("1", "a");
		assertEquals(false, CollectionUtils.sizeIsEmpty(map));
	}

	@Test
	public void testSizeIsEmpty_Array() {
		final Object[] objectArray = new Object[0];
		assertEquals(true, CollectionUtils.sizeIsEmpty(objectArray));

		final String[] stringArray = new String[3];
		assertEquals(false, CollectionUtils.sizeIsEmpty(stringArray));
		stringArray[0] = "a";
		stringArray[1] = "b";
		stringArray[2] = "c";
		assertEquals(false, CollectionUtils.sizeIsEmpty(stringArray));
	}

	@Test
	public void testSizeIsEmpty_PrimitiveArray() {
		final int[] intArray = new int[0];
		assertEquals(true, CollectionUtils.sizeIsEmpty(intArray));

		final double[] doubleArray = new double[3];
		assertEquals(false, CollectionUtils.sizeIsEmpty(doubleArray));
		doubleArray[0] = 0.0d;
		doubleArray[1] = 1.0d;
		doubleArray[2] = 2.5d;
		assertEquals(false, CollectionUtils.sizeIsEmpty(doubleArray));
	}

	@Test
	public void testSizeIsEmpty_Enumeration() {
		final Vector<String> list = new Vector<>();
		assertEquals(true, CollectionUtils.sizeIsEmpty(list.elements()));
		list.add("a");
		assertEquals(false, CollectionUtils.sizeIsEmpty(list.elements()));
		final Enumeration<String> en = list.elements();
		en.nextElement();
		assertEquals(true, CollectionUtils.sizeIsEmpty(en));
	}

	@Test
	public void testSizeIsEmpty_Iterator() {
		final List<String> list = new ArrayList<>();
		assertEquals(true, CollectionUtils.sizeIsEmpty(list.iterator()));
		list.add("a");
		assertEquals(false, CollectionUtils.sizeIsEmpty(list.iterator()));
		final Iterator<String> it = list.iterator();
		it.next();
		assertEquals(true, CollectionUtils.sizeIsEmpty(it));
	}

	@Test
	public void testSizeIsEmpty_Other() {
		try {
			CollectionUtils.sizeIsEmpty("not a list");
			fail("Expecting IllegalArgumentException");
		} catch (final IllegalArgumentException ex) {
		}
	}

	// -----------------------------------------------------------------------
	@Test
	public void testIsEmptyWithEmptyCollection() {
		final Collection<Object> coll = new ArrayList<>();
		assertEquals(true, CollectionUtils.isEmpty(coll));
	}

	@Test
	public void testIsEmptyWithNonEmptyCollection() {
		final Collection<String> coll = new ArrayList<>();
		coll.add("item");
		assertEquals(false, CollectionUtils.isEmpty(coll));
	}

	@Test
	public void testIsEmptyWithNull() {
		final Collection<?> coll = null;
		assertEquals(true, CollectionUtils.isEmpty(coll));
	}

	@Test
	public void testIsNotEmptyWithEmptyCollection() {
		final Collection<Object> coll = new ArrayList<>();
		assertEquals(false, CollectionUtils.isNotEmpty(coll));
	}

	@Test
	public void testIsNotEmptyWithNonEmptyCollection() {
		final Collection<String> coll = new ArrayList<>();
		coll.add("item");
		assertEquals(true, CollectionUtils.isNotEmpty(coll));
	}

	@Test
	public void testIsNotEmptyWithNull() {
		final Collection<?> coll = null;
		assertEquals(false, CollectionUtils.isNotEmpty(coll));
	}

	// -----------------------------------------------------------------------
	@Test
	public void addIgnoreNull() {
		final Set<String> set = new HashSet<>();
		set.add("1");
		set.add("2");
		set.add("3");
		assertFalse(CollectionUtils.addIgnoreNull(set, null));
		assertEquals(3, set.size());
		assertFalse(CollectionUtils.addIgnoreNull(set, "1"));
		assertEquals(3, set.size());
		assertEquals(true, CollectionUtils.addIgnoreNull(set, "4"));
		assertEquals(4, set.size());
		assertEquals(true, set.contains("4"));
	}

	// -----------------------------------------------------------------------

	@Test
	public void isFull() {
		final Set<String> set = new HashSet<>();
		set.add("1");
		set.add("2");
		set.add("3");
		try {
			CollectionUtils.isFull(null);
			fail();
		} catch (final NullPointerException ex) {
		}
		assertFalse(CollectionUtils.isFull(set));

		final CircularFifoQueue<String> buf = new CircularFifoQueue<>(set);
		assertEquals(false, CollectionUtils.isFull(buf));
		buf.remove("2");
		assertFalse(CollectionUtils.isFull(buf));
		buf.add("2");
		assertEquals(false, CollectionUtils.isFull(buf));
	}

	@Test
	public void isEmpty() {
		assertFalse(CollectionUtils.isNotEmpty(null));
		assertTrue(CollectionUtils.isNotEmpty(collectionA));
	}

	@Test
	public void maxSize() {
		final Set<String> set = new HashSet<>();
		set.add("1");
		set.add("2");
		set.add("3");
		try {
			CollectionUtils.maxSize(null);
			fail();
		} catch (final NullPointerException ex) {
		}
		assertEquals(-1, CollectionUtils.maxSize(set));

		final Queue<String> buf = new CircularFifoQueue<>(set);
		assertEquals(3, CollectionUtils.maxSize(buf));
		buf.remove("2");
		assertEquals(3, CollectionUtils.maxSize(buf));
		buf.add("2");
		assertEquals(3, CollectionUtils.maxSize(buf));
	}

	@Test
	public void intersectionUsesMethodEquals() {
		// Let elta and eltb be objects...
		final Integer elta = new Integer(17); // Cannot use valueOf here
		final Integer eltb = new Integer(17);

		// ...which are equal...
		assertEquals(elta, eltb);
		assertEquals(eltb, elta);

		// ...but not the same (==).
		assertTrue(elta != eltb);

		// Let cola and colb be collections...
		final Collection<Number> cola = new ArrayList<>();
		final Collection<Integer> colb = new ArrayList<>();

		// ...which contain elta and eltb,
		// respectively.
		cola.add(elta);
		colb.add(eltb);

		// Then the intersection of the two
		// should contain one element.
		final Collection<Number> intersection = CollectionUtils.intersection(cola, colb);
		assertEquals(1, intersection.size());

		// In practice, this element will be the same (==) as elta
		// or eltb, although this isn't strictly part of the
		// contract.
		final Object eltc = intersection.iterator().next();
		assertTrue(eltc == elta && eltc != eltb || eltc != elta && eltc == eltb);

		// In any event, this element remains equal,
		// to both elta and eltb.
		assertEquals(elta, eltc);
		assertEquals(eltc, elta);
		assertEquals(eltb, eltc);
		assertEquals(eltc, eltb);
	}

	// -----------------------------------------------------------------------
	// Up to here
	@Test
	public void testRetainAll() {
		final List<String> base = new ArrayList<>();
		base.add("A");
		base.add("B");
		base.add("C");
		final List<Object> sub = new ArrayList<>();
		sub.add("A");
		sub.add("C");
		sub.add("X");

		final Collection<String> result = CollectionUtils.retainAll(base, sub);
		assertEquals(2, result.size());
		assertEquals(true, result.contains("A"));
		assertFalse(result.contains("B"));
		assertEquals(true, result.contains("C"));
		assertEquals(3, base.size());
		assertEquals(true, base.contains("A"));
		assertEquals(true, base.contains("B"));
		assertEquals(true, base.contains("C"));
		assertEquals(3, sub.size());
		assertEquals(true, sub.contains("A"));
		assertEquals(true, sub.contains("C"));
		assertEquals(true, sub.contains("X"));

		try {
			CollectionUtils.retainAll(null, null);
			fail("expecting NullPointerException");
		} catch (final NullPointerException npe) {
		} // this is what we want
	}

	@Test
	public void testRemoveAll() {
		final List<String> base = new ArrayList<>();
		base.add("A");
		base.add("B");
		base.add("C");
		final List<String> sub = new ArrayList<>();
		sub.add("A");
		sub.add("C");
		sub.add("X");

		final Collection<String> result = CollectionUtils.removeAll(base, sub);
		assertEquals(1, result.size());
		assertFalse(result.contains("A"));
		assertEquals(true, result.contains("B"));
		assertFalse(result.contains("C"));
		assertEquals(3, base.size());
		assertEquals(true, base.contains("A"));
		assertEquals(true, base.contains("B"));
		assertEquals(true, base.contains("C"));
		assertEquals(3, sub.size());
		assertEquals(true, sub.contains("A"));
		assertEquals(true, sub.contains("C"));
		assertEquals(true, sub.contains("X"));

		try {
			CollectionUtils.removeAll(null, null);
			fail("expecting NullPointerException");
		} catch (final NullPointerException npe) {
		} // this is what we want
	}

	// -----------------------------------------------------------------------

	@Test
	public void emptyCollection() throws Exception {
		final Collection<Number> coll = CollectionUtils.emptyCollection();
		assertEquals(CollectionUtils.EMPTY_COLLECTION, coll);
	}

	@Test
	public void emptyIfNull() {
		assertTrue(CollectionUtils.emptyIfNull(null).isEmpty());
		final Collection<Object> collection = new ArrayList<>();
		assertSame(collection, CollectionUtils.emptyIfNull(collection));
	}

	/**
	 * This test ensures that {@link Iterable}s are supported by
	 * {@link CollectionUtils}. Specifically, it uses mocks to ensure that if the
	 * passed in {@link Iterable} is a {@link Collection} then
	 * {@link Collection#addAll(Collection)} is called instead of iterating.
	 */
	@Test
	public void addAllForIterable() {
		final Collection<Integer> inputCollection = createMock(Collection.class);
		final Iterable<Integer> inputIterable = inputCollection;
		final Iterable<Long> iterable = createMock(Iterable.class);
		final Iterator<Long> iterator = createMock(Iterator.class);
		final Collection<Number> c = createMock(Collection.class);

		expect(iterable.iterator()).andReturn(iterator);
		next(iterator, 1L);
		next(iterator, 2L);
		next(iterator, 3L);
		expect(iterator.hasNext()).andReturn(false);
		expect(c.add(1L)).andReturn(true);
		expect(c.add(2L)).andReturn(true);
		expect(c.add(3L)).andReturn(true);
		// Check that the collection is added using
		// Collection.addAll(Collection)
		expect(c.addAll(inputCollection)).andReturn(true);

		// Ensure the method returns false if nothing is added
		expect(iterable.iterator()).andReturn(iterator);
		next(iterator, 1L);
		expect(iterator.hasNext()).andReturn(false);
		expect(c.add(1L)).andReturn(false);
		expect(c.addAll(inputCollection)).andReturn(false);

		replay();
		assertTrue(CollectionUtils.addAll(c, iterable));
		assertTrue(CollectionUtils.addAll(c, inputIterable));

		assertFalse(CollectionUtils.addAll(c, iterable));
		assertFalse(CollectionUtils.addAll(c, inputIterable));
		verify();
	}

	@Test
	public void addAllForEnumeration() {
		final Hashtable<Integer, Integer> h = new Hashtable<>();
		h.put(5, 5);
		final Enumeration<? extends Integer> enumeration = h.keys();
		CollectionUtils.addAll(collectionA, enumeration);
		assertTrue(collectionA.contains(5));
	}

	@Test
	public void addAllForElements() {
		CollectionUtils.addAll(collectionA, new Integer[] { 5 });
		assertTrue(collectionA.contains(5));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void getNegative() {
		CollectionUtils.get((Object) collectionA, -3);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void getPositiveOutOfBounds() {
		CollectionUtils.get((Object) collectionA.iterator(), 30);
	}

	@Test(expected = IllegalArgumentException.class)
	public void get1() {
		CollectionUtils.get((Object) null, 0);
	}

	@Test
	public void get() {
		assertEquals(2, CollectionUtils.get((Object) collectionA, 2));
		assertEquals(2, CollectionUtils.get((Object) collectionA.iterator(), 2));
		final Map<Integer, Integer> map = CollectionUtils.getCardinalityMap(collectionA);
		assertEquals(map.entrySet().iterator().next(), CollectionUtils.get((Object) map, 0));
	}

	@Test
	public void getIterator() {
		final Iterator<Integer> it = collectionA.iterator();
		assertEquals(Integer.valueOf(2), CollectionUtils.get((Object) it, 2));
		assertTrue(it.hasNext());
		assertEquals(Integer.valueOf(4), CollectionUtils.get((Object) it, 6));
		assertFalse(it.hasNext());
	}

	@Test
	public void getEnumeration() {
		final Vector<Integer> vectorA = new Vector<>(collectionA);
		final Enumeration<Integer> e = vectorA.elements();
		assertEquals(Integer.valueOf(2), CollectionUtils.get(e, 2));
		assertTrue(e.hasMoreElements());
		assertEquals(Integer.valueOf(4), CollectionUtils.get(e, 6));
		assertFalse(e.hasMoreElements());
	}

	@Test
	public void reverse() {
		CollectionUtils.reverseArray(new Object[] {});
		final Integer[] a = collectionA.toArray(new Integer[collectionA.size()]);
		CollectionUtils.reverseArray(a);
		// assume our implementation is correct if it returns the same order as the Java
		// function
		Collections.reverse(collectionA);
		assertEquals(collectionA, Arrays.asList(a));
	}

	@Test
	public void extractSingleton() {
		ArrayList<String> coll = null;
		try {
			CollectionUtils.extractSingleton(coll);
			fail("expected NullPointerException from extractSingleton(null)");
		} catch (final NullPointerException e) {
		}
		coll = new ArrayList<>();
		try {
			CollectionUtils.extractSingleton(coll);
			fail("expected IllegalArgumentException from extractSingleton(empty)");
		} catch (final IllegalArgumentException e) {
		}
		coll.add("foo");
		assertEquals("foo", CollectionUtils.extractSingleton(coll));
		coll.add("bar");
		try {
			CollectionUtils.extractSingleton(coll);
			fail("expected IllegalArgumentException from extractSingleton(size == 2)");
		} catch (final IllegalArgumentException e) {
		}
	}

	/**
	 * Records the next object returned for a mock iterator
	 */
	private <T> void next(final Iterator<T> iterator, final T t) {
		expect(iterator.hasNext()).andReturn(true);
		expect(iterator.next()).andReturn(t);
	}

	@Test(expected = NullPointerException.class)
	public void collateException1() {
		CollectionUtils.collate(collectionA, null);
	}

	@Test(expected = NullPointerException.class)
	public void collateException2() {
		CollectionUtils.collate(collectionA, collectionC, null);
	}

	@Test
	public void testCollate() {
		List<Integer> result = CollectionUtils.collate(emptyCollection, emptyCollection);
		assertEquals("Merge empty with empty", 0, result.size());

		result = CollectionUtils.collate(collectionA, emptyCollection);
		assertEquals("Merge empty with non-empty", collectionA, result);

		List<Integer> result1 = CollectionUtils.collate(collectionD, collectionE);
		List<Integer> result2 = CollectionUtils.collate(collectionE, collectionD);
		assertEquals("Merge two lists 1", result1, result2);

		final List<Integer> combinedList = new ArrayList<>();
		combinedList.addAll(collectionD);
		combinedList.addAll(collectionE);
		Collections.sort(combinedList);

		assertEquals("Merge two lists 2", combinedList, result2);

		final Comparator<Integer> reverseComparator = ComparatorUtils
		        .reversedComparator(ComparatorUtils.<Integer>naturalComparator());

		result = CollectionUtils.collate(emptyCollection, emptyCollection, reverseComparator);
		assertEquals("Comparator Merge empty with empty", 0, result.size());

		Collections.reverse((List<Integer>) collectionD);
		Collections.reverse((List<Integer>) collectionE);
		Collections.reverse(combinedList);

		result1 = CollectionUtils.collate(collectionD, collectionE, reverseComparator);
		result2 = CollectionUtils.collate(collectionE, collectionD, reverseComparator);
		assertEquals("Comparator Merge two lists 1", result1, result2);
		assertEquals("Comparator Merge two lists 2", combinedList, result2);
	}

	@Test
	public void testCollateIgnoreDuplicates() {
		final List<Integer> result1 = CollectionUtils.collate(collectionD, collectionE, false);
		final List<Integer> result2 = CollectionUtils.collate(collectionE, collectionD, false);
		assertEquals("Merge two lists 1 - ignore duplicates", result1, result2);

		final Set<Integer> combinedSet = new HashSet<>();
		combinedSet.addAll(collectionD);
		combinedSet.addAll(collectionE);
		final List<Integer> combinedList = new ArrayList<>(combinedSet);
		Collections.sort(combinedList);

		assertEquals("Merge two lists 2 - ignore duplicates", combinedList, result2);
	}

	@Test(expected = NullPointerException.class)
	public void testPermutationsWithNullCollection() {
		CollectionUtils.permutations(null);
	}

	@Test
	public void testPermutations() {
		final List<Integer> sample = collectionA.subList(0, 5);
		final Collection<List<Integer>> permutations = CollectionUtils.permutations(sample);

		// result size = n!
		final int collSize = sample.size();
		int factorial = 1;
		for (int i = 1; i <= collSize; i++) {
			factorial *= i;
		}
		assertEquals(factorial, permutations.size());
	}
}
