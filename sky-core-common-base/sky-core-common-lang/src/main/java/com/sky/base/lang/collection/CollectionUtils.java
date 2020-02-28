package com.sky.base.lang.collection;

import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Title 集合工具类
 * @Description 该集合工具类为org.apache.commons.collections4.CollectionUtils的装饰器
 * @author dengny
 * @author lizp
 * @version 1.1.0
 * @date 2018-12-12
 */
public final class CollectionUtils {

	private CollectionUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * An empty unmodifiable collection. The JDK provides empty Set and List
	 * implementations which could be used for this purpose. However they could be
	 * cast to Set or List which might be undesirable. This implementation only
	 * implements Collection.
	 */
	@SuppressWarnings("rawtypes") // we deliberately use the raw type here
	public static final Collection EMPTY_COLLECTION = org.apache.commons.collections4.CollectionUtils.EMPTY_COLLECTION;

	/**
	 * Returns the immutable EMPTY_COLLECTION with generic type safety.
	 *
	 * @see #EMPTY_COLLECTION
	 * @param <T> the element type
	 * @return immutable empty collection
	 */
	public static <T> Collection<T> emptyCollection() {
		return org.apache.commons.collections4.CollectionUtils.emptyCollection();
	}

	/**
	 * Returns an immutable empty collection if the argument is <code>null</code>,
	 * or the argument itself otherwise.
	 *
	 * @param            <T> the element type
	 * @param collection the collection, possibly <code>null</code>
	 * @return an empty collection if the argument is <code>null</code>
	 */
	public static <T> Collection<T> emptyIfNull(final Collection<T> collection) {
		return org.apache.commons.collections4.CollectionUtils.emptyIfNull(collection);
	}

	/**
	 * Returns a {@link Collection} containing the union of the given
	 * {@link Iterable}s.
	 * <p>
	 * The cardinality of each element in the returned {@link Collection} will be
	 * equal to the maximum of the cardinality of that element in the two given
	 * {@link Iterable}s.
	 *
	 * @param a the first collection, must not be null
	 * @param b the second collection, must not be null
	 * @param   <O> the generic type that is able to represent the types contained
	 *          in both input collections.
	 * @return the union of the two collections
	 * @see Collection#addAll
	 */
	public static <O> Collection<O> union(final Iterable<? extends O> a, final Iterable<? extends O> b) {
		return org.apache.commons.collections4.CollectionUtils.union(a, b);
	}

	/**
	 * Returns a {@link Collection} containing the intersection of the given
	 * {@link Iterable}s.
	 * <p>
	 * The cardinality of each element in the returned {@link Collection} will be
	 * equal to the minimum of the cardinality of that element in the two given
	 * {@link Iterable}s.
	 *
	 * @param a the first collection, must not be null
	 * @param b the second collection, must not be null
	 * @param   <O> the generic type that is able to represent the types contained
	 *          in both input collections.
	 * @return the intersection of the two collections
	 * @see Collection#retainAll
	 * @see #containsAny
	 */
	public static <O> Collection<O> intersection(final Iterable<? extends O> a, final Iterable<? extends O> b) {
		return org.apache.commons.collections4.CollectionUtils.intersection(a, b);
	}

	/**
	 * Returns a {@link Collection} containing the exclusive disjunction (symmetric
	 * difference) of the given {@link Iterable}s.
	 * <p>
	 * The cardinality of each element <i>e</i> in the returned {@link Collection}
	 * will be equal to
	 * <code>max(cardinality(<i>e</i>,<i>a</i>),cardinality(<i>e</i>,<i>b</i>)) - min(cardinality(<i>e</i>,<i>a</i>),
	 * cardinality(<i>e</i>,<i>b</i>))</code>.
	 * <p>
	 * This is equivalent to {@code {@link #subtract subtract}({@link #union
	 * union(a,b)},{@link #intersection intersection(a,b)})} or {@code {@link #union
	 * union}({@link #subtract subtract(a,b)},{@link #subtract subtract(b,a)})}.
	 * 
	 * @param a the first collection, must not be null
	 * @param b the second collection, must not be null
	 * @param   <O> the generic type that is able to represent the types contained
	 *          in both input collections.
	 * @return the symmetric difference of the two collections
	 */
	public static <O> Collection<O> disjunction(final Iterable<? extends O> a, final Iterable<? extends O> b) {
		return org.apache.commons.collections4.CollectionUtils.disjunction(a, b);
	}

	/**
	 * Returns a new {@link Collection} containing {@code <i>a</i> - <i>b</i>}. The
	 * cardinality of each element <i>e</i> in the returned {@link Collection} will
	 * be the cardinality of <i>e</i> in <i>a</i> minus the cardinality of <i>e</i>
	 * in <i>b</i>, or zero, whichever is greater.
	 *
	 * @param a the collection to subtract from, must not be null
	 * @param b the collection to subtract, must not be null
	 * @param   <O> the generic type that is able to represent the types contained
	 *          in both input collections.
	 * @return a new collection with the results
	 * @see Collection#removeAll
	 */
	public static <O> Collection<O> subtract(final Iterable<? extends O> a, final Iterable<? extends O> b) {
		return org.apache.commons.collections4.CollectionUtils.subtract(a, b);
	}

	/**
	 * Returns <code>true</code> iff all elements of {@code coll2} are also
	 * contained in {@code coll1}. The cardinality of values in {@code coll2} is not
	 * taken into account, which is the same behavior as
	 * {@link Collection#containsAll(Collection)}.
	 * <p>
	 * In other words, this method returns <code>true</code> iff the
	 * {@link #intersection} of <i>coll1</i> and <i>coll2</i> has the same
	 * cardinality as the set of unique values from {@code coll2}. In case
	 * {@code coll2} is empty, {@code true} will be returned.
	 * <p>
	 * This method is intended as a replacement for
	 * {@link Collection#containsAll(Collection)} with a guaranteed runtime
	 * complexity of {@code O(n + m)}. Depending on the type of {@link Collection}
	 * provided, this method will be much faster than calling
	 * {@link Collection#containsAll(Collection)} instead, though this will come at
	 * the cost of an additional space complexity O(n).
	 *
	 * @param coll1 the first collection, must not be null
	 * @param coll2 the second collection, must not be null
	 * @return <code>true</code> iff the intersection of the collections has the
	 *         same cardinality as the set of unique elements from the second
	 *         collection
	 */
	public static boolean containsAll(final Collection<?> coll1, final Collection<?> coll2) {
		return org.apache.commons.collections4.CollectionUtils.containsAll(coll1, coll2);
	}

	/**
	 * Returns <code>true</code> iff at least one element is in both collections.
	 * <p>
	 * In other words, this method returns <code>true</code> iff the
	 * {@link #intersection} of <i>coll1</i> and <i>coll2</i> is not empty.
	 *
	 * @param       <T> the type of object to lookup in <code>coll1</code>.
	 * @param coll1 the first collection, must not be null
	 * @param coll2 the second collection, must not be null
	 * @return <code>true</code> iff the intersection of the collections is
	 *         non-empty
	 * @see #intersection
	 */
	public static <T> boolean containsAny(final Collection<?> coll1, @SuppressWarnings("unchecked") final T... coll2) {
		return org.apache.commons.collections4.CollectionUtils.containsAny(coll1, coll2);
	}

	/**
	 * Returns <code>true</code> iff at least one element is in both collections.
	 * <p>
	 * In other words, this method returns <code>true</code> iff the
	 * {@link #intersection} of <i>coll1</i> and <i>coll2</i> is not empty.
	 *
	 * @param coll1 the first collection, must not be null
	 * @param coll2 the second collection, must not be null
	 * @return <code>true</code> iff the intersection of the collections is
	 *         non-empty
	 * @see #intersection
	 */
	public static boolean containsAny(final Collection<?> coll1, final Collection<?> coll2) {
		return org.apache.commons.collections4.CollectionUtils.containsAny(coll1, coll2);
	}

	/**
	 * Returns a {@link Map} mapping each unique element in the given
	 * {@link Collection} to an {@link Integer} representing the number of
	 * occurrences of that element in the {@link Collection}.
	 * <p>
	 * Only those elements present in the collection will appear as keys in the map.
	 *
	 * @param      <O> the type of object in the returned {@link Map}. This is a
	 *             super type of &lt;I&gt;.
	 * @param coll the collection to get the cardinality map for, must not be null
	 * @return the populated cardinality map
	 */
	public static <O> Map<O, Integer> getCardinalityMap(final Iterable<? extends O> coll) {
		return org.apache.commons.collections4.CollectionUtils.getCardinalityMap(coll);
	}

	/**
	 * Returns {@code true} iff <i>a</i> is a sub-collection of <i>b</i>, that is,
	 * iff the cardinality of <i>e</i> in <i>a</i> is less than or equal to the
	 * cardinality of <i>e</i> in <i>b</i>, for each element <i>e</i> in <i>a</i>.
	 *
	 * @param a the first (sub?) collection, must not be null
	 * @param b the second (super?) collection, must not be null
	 * @return <code>true</code> iff <i>a</i> is a sub-collection of <i>b</i>
	 * @see #isProperSubCollection
	 * @see Collection#containsAll
	 */
	public static boolean isSubCollection(final Collection<?> a, final Collection<?> b) {
		return org.apache.commons.collections4.CollectionUtils.isSubCollection(a, b);
	}

	/**
	 * Returns {@code true} iff <i>a</i> is a <i>proper</i> sub-collection of
	 * <i>b</i>, that is, iff the cardinality of <i>e</i> in <i>a</i> is less than
	 * or equal to the cardinality of <i>e</i> in <i>b</i>, for each element
	 * <i>e</i> in <i>a</i>, and there is at least one element <i>f</i> such that
	 * the cardinality of <i>f</i> in <i>b</i> is strictly greater than the
	 * cardinality of <i>f</i> in <i>a</i>.
	 * <p>
	 * The implementation assumes
	 * <ul>
	 * <li><code>a.size()</code> and <code>b.size()</code> represent the total
	 * cardinality of <i>a</i> and <i>b</i>, resp.</li>
	 * <li><code>a.size() &lt; Integer.MAXVALUE</code></li>
	 * </ul>
	 *
	 * @param a the first (sub?) collection, must not be null
	 * @param b the second (super?) collection, must not be null
	 * @return <code>true</code> iff <i>a</i> is a <i>proper</i> sub-collection of
	 *         <i>b</i>
	 * @see #isSubCollection
	 * @see Collection#containsAll
	 */
	public static boolean isProperSubCollection(final Collection<?> a, final Collection<?> b) {
		return org.apache.commons.collections4.CollectionUtils.isProperSubCollection(a, b);
	}

	/**
	 * Returns {@code true} iff the given {@link Collection}s contain exactly the
	 * same elements with exactly the same cardinalities.
	 * <p>
	 * That is, iff the cardinality of <i>e</i> in <i>a</i> is equal to the
	 * cardinality of <i>e</i> in <i>b</i>, for each element <i>e</i> in <i>a</i> or
	 * <i>b</i>.
	 *
	 * @param a the first collection, must not be null
	 * @param b the second collection, must not be null
	 * @return <code>true</code> iff the collections contain the same elements with
	 *         the same cardinalities.
	 */
	public static boolean isEqualCollection(final Collection<?> a, final Collection<?> b) {
		return org.apache.commons.collections4.CollectionUtils.isEqualCollection(a, b);
	}

	// -----------------------------------------------------------------------
	/**
	 * Adds an element to the collection unless the element is null.
	 *
	 * @param            <T> the type of object the {@link Collection} contains
	 * @param collection the collection to add to, must not be null
	 * @param object     the object to add, if null it will not be added
	 * @return true if the collection changed
	 * @throws NullPointerException if the collection is null
	 */
	public static <T> boolean addIgnoreNull(final Collection<T> collection, final T object) {
		return org.apache.commons.collections4.CollectionUtils.addIgnoreNull(collection, object);
	}

	/**
	 * Adds all elements in the {@link Iterable} to the given collection. If the
	 * {@link Iterable} is a {@link Collection} then it is cast and will be added
	 * using {@link Collection#addAll(Collection)} instead of iterating.
	 *
	 * @param            <C> the type of object the {@link Collection} contains
	 * @param collection the collection to add to, must not be null
	 * @param iterable   the iterable of elements to add, must not be null
	 * @return a boolean indicating whether the collection has changed or not.
	 * @throws NullPointerException if the collection or iterator is null
	 */
	public static <C> boolean addAll(final Collection<C> collection, final Iterable<? extends C> iterable) {
		return org.apache.commons.collections4.CollectionUtils.addAll(collection, iterable);
	}

	/**
	 * Adds all elements in the iteration to the given collection.
	 *
	 * @param            <C> the type of object the {@link Collection} contains
	 * @param collection the collection to add to, must not be null
	 * @param iterator   the iterator of elements to add, must not be null
	 * @return a boolean indicating whether the collection has changed or not.
	 * @throws NullPointerException if the collection or iterator is null
	 */
	public static <C> boolean addAll(final Collection<C> collection, final Iterator<? extends C> iterator) {
		return org.apache.commons.collections4.CollectionUtils.addAll(collection, iterator);
	}

	/**
	 * Adds all elements in the enumeration to the given collection.
	 *
	 * @param             <C> the type of object the {@link Collection} contains
	 * @param collection  the collection to add to, must not be null
	 * @param enumeration the enumeration of elements to add, must not be null
	 * @return {@code true} if the collections was changed, {@code false} otherwise
	 * @throws NullPointerException if the collection or enumeration is null
	 */
	public static <C> boolean addAll(final Collection<C> collection, final Enumeration<? extends C> enumeration) {
		return org.apache.commons.collections4.CollectionUtils.addAll(collection, enumeration);
	}

	/**
	 * Adds all elements in the array to the given collection.
	 *
	 * @param            <C> the type of object the {@link Collection} contains
	 * @param collection the collection to add to, must not be null
	 * @param elements   the array of elements to add, must not be null
	 * @return {@code true} if the collection was changed, {@code false} otherwise
	 * @throws NullPointerException if the collection or array is null
	 */
	public static <C> boolean addAll(final Collection<C> collection,
	        @SuppressWarnings("unchecked") final C... elements) {
		return org.apache.commons.collections4.CollectionUtils.addAll(collection, elements);
	}

	/**
	 * Returns the <code>index</code>-th value in <code>object</code>, throwing
	 * <code>IndexOutOfBoundsException</code> if there is no such element or
	 * <code>IllegalArgumentException</code> if <code>object</code> is not an
	 * instance of one of the supported types.
	 * <p>
	 * The supported types, and associated semantics are:
	 * <ul>
	 * <li>Map -- the value returned is the <code>Map.Entry</code> in position
	 * <code>index</code> in the map's <code>entrySet</code> iterator, if there is
	 * such an entry.</li>
	 * <li>List -- this method is equivalent to the list's get method.</li>
	 * <li>Array -- the <code>index</code>-th array entry is returned, if there is
	 * such an entry; otherwise an <code>IndexOutOfBoundsException</code> is
	 * thrown.</li>
	 * <li>Collection -- the value returned is the <code>index</code>-th object
	 * returned by the collection's default iterator, if there is such an
	 * element.</li>
	 * <li>Iterator or Enumeration -- the value returned is the
	 * <code>index</code>-th object in the Iterator/Enumeration, if there is such an
	 * element. The Iterator/Enumeration is advanced to <code>index</code> (or to
	 * the end, if <code>index</code> exceeds the number of entries) as a side
	 * effect of this method.</li>
	 * </ul>
	 *
	 * @param object the object to get a value from
	 * @param index  the index to get
	 * @return the object at the specified index
	 * @throws IndexOutOfBoundsException if the index is invalid
	 * @throws IllegalArgumentException  if the object type is invalid
	 */
	public static Object get(final Object object, final int index) {
		return org.apache.commons.collections4.CollectionUtils.get(object, index);
	}

	/**
	 * Returns the <code>index</code>-th <code>Map.Entry</code> in the
	 * <code>map</code>'s <code>entrySet</code>, throwing
	 * <code>IndexOutOfBoundsException</code> if there is no such element.
	 *
	 * @param       <K> the key type in the {@link Map}
	 * @param       <V> the key type in the {@link Map}
	 * @param map   the object to get a value from
	 * @param index the index to get
	 * @return the object at the specified index
	 * @throws IndexOutOfBoundsException if the index is invalid
	 */
	public static <K, V> Map.Entry<K, V> get(final Map<K, V> map, final int index) {
		return org.apache.commons.collections4.CollectionUtils.get(map, index);
	}

	/**
	 * Gets the size of the collection/iterator specified.
	 * <p>
	 * This method can handles objects as follows
	 * <ul>
	 * <li>Collection - the collection size
	 * <li>Map - the map size
	 * <li>Array - the array size
	 * <li>Iterator - the number of elements remaining in the iterator
	 * <li>Enumeration - the number of elements remaining in the enumeration
	 * </ul>
	 *
	 * @param object the object to get the size of, may be null
	 * @return the size of the specified collection or 0 if the object was null
	 * @throws IllegalArgumentException thrown if object is not recognized
	 */
	public static int size(final Object object) {
		return org.apache.commons.collections4.CollectionUtils.size(object);
	}

	/**
	 * Checks if the specified collection/array/iterator is empty.
	 * <p>
	 * This method can handles objects as follows
	 * <ul>
	 * <li>Collection - via collection isEmpty
	 * <li>Map - via map isEmpty
	 * <li>Array - using array size
	 * <li>Iterator - via hasNext
	 * <li>Enumeration - via hasMoreElements
	 * </ul>
	 * <p>
	 * Note: This method is named to avoid clashing with
	 * {@link #isEmpty(Collection)}.
	 *
	 * @param object the object to get the size of, may be null
	 * @return true if empty or null
	 * @throws IllegalArgumentException thrown if object is not recognized
	 */
	public static boolean sizeIsEmpty(final Object object) {
		return org.apache.commons.collections4.CollectionUtils.sizeIsEmpty(object);
	}

	// -----------------------------------------------------------------------
	/**
	 * Null-safe check if the specified collection is empty.
	 * <p>
	 * Null returns true.
	 *
	 * @param coll the collection to check, may be null
	 * @return true if empty or null
	 */
	public static boolean isEmpty(final Collection<?> coll) {
		return org.apache.commons.collections4.CollectionUtils.isEmpty(coll);
	}

	/**
	 * Null-safe check if the specified collection is not empty.
	 * <p>
	 * Null returns false.
	 *
	 * @param coll the collection to check, may be null
	 * @return true if non-null and non-empty
	 */
	public static boolean isNotEmpty(final Collection<?> coll) {
		return org.apache.commons.collections4.CollectionUtils.isNotEmpty(coll);
	}

	// -----------------------------------------------------------------------
	/**
	 * Reverses the order of the given array.
	 *
	 * @param array the array to reverse
	 */
	public static void reverseArray(final Object[] array) {
		org.apache.commons.collections4.CollectionUtils.reverseArray(array);
	}

	/**
	 * Returns true if no more elements can be added to the Collection.
	 * <p>
	 * This method uses the {@link BoundedCollection} interface to determine the
	 * full status. If the collection does not implement this interface then false
	 * is returned.
	 * <p>
	 * The collection does not have to implement this interface directly. If the
	 * collection has been decorated using the decorators subpackage then these will
	 * be removed to access the BoundedCollection.
	 *
	 * @param coll the collection to check
	 * @return true if the BoundedCollection is full
	 * @throws NullPointerException if the collection is null
	 */
	public static boolean isFull(final Collection<? extends Object> coll) {
		return org.apache.commons.collections4.CollectionUtils.isFull(coll);
	}

	/**
	 * Get the maximum number of elements that the Collection can contain.
	 * <p>
	 * This method uses the {@link BoundedCollection} interface to determine the
	 * maximum size. If the collection does not implement this interface then -1 is
	 * returned.
	 * <p>
	 * The collection does not have to implement this interface directly. If the
	 * collection has been decorated using the decorators subpackage then these will
	 * be removed to access the BoundedCollection.
	 *
	 * @param coll the collection to check
	 * @return the maximum size of the BoundedCollection, -1 if no maximum size
	 * @throws NullPointerException if the collection is null
	 */
	public static int maxSize(final Collection<? extends Object> coll) {
		return org.apache.commons.collections4.CollectionUtils.maxSize(coll);
	}

	// -----------------------------------------------------------------------
	/**
	 * Merges two sorted Collections, a and b, into a single, sorted List such that
	 * the natural ordering of the elements is retained.
	 * <p>
	 * Uses the standard O(n) merge algorithm for combining two sorted lists.
	 *
	 * @param   <O> the element type
	 * @param a the first collection, must not be null
	 * @param b the second collection, must not be null
	 * @return a new sorted List, containing the elements of Collection a and b
	 * @throws NullPointerException if either collection is null
	 */
	public static <O extends Comparable<? super O>> List<O> collate(final Iterable<? extends O> a,
	        final Iterable<? extends O> b) {
		return org.apache.commons.collections4.CollectionUtils.collate(a, b);
	}

	/**
	 * Merges two sorted Collections, a and b, into a single, sorted List such that
	 * the natural ordering of the elements is retained.
	 * <p>
	 * Uses the standard O(n) merge algorithm for combining two sorted lists.
	 *
	 * @param                   <O> the element type
	 * @param a                 the first collection, must not be null
	 * @param b                 the second collection, must not be null
	 * @param includeDuplicates if {@code true} duplicate elements will be retained,
	 *                          otherwise they will be removed in the output
	 *                          collection
	 * @return a new sorted List, containing the elements of Collection a and b
	 * @throws NullPointerException if either collection is null
	 */
	public static <O extends Comparable<? super O>> List<O> collate(final Iterable<? extends O> a,
	        final Iterable<? extends O> b, final boolean includeDuplicates) {
		return org.apache.commons.collections4.CollectionUtils.collate(a, b, includeDuplicates);
	}

	/**
	 * Merges two sorted Collections, a and b, into a single, sorted List such that
	 * the ordering of the elements according to Comparator c is retained.
	 * <p>
	 * Uses the standard O(n) merge algorithm for combining two sorted lists.
	 *
	 * @param   <O> the element type
	 * @param a the first collection, must not be null
	 * @param b the second collection, must not be null
	 * @param c the comparator to use for the merge.
	 * @return a new sorted List, containing the elements of Collection a and b
	 * @throws NullPointerException if either collection or the comparator is null
	 */
	public static <O> List<O> collate(final Iterable<? extends O> a, final Iterable<? extends O> b,
	        final Comparator<? super O> c) {
		return org.apache.commons.collections4.CollectionUtils.collate(a, b, c);
	}

	/**
	 * Merges two sorted Collections, a and b, into a single, sorted List such that
	 * the ordering of the elements according to Comparator c is retained.
	 * <p>
	 * Uses the standard O(n) merge algorithm for combining two sorted lists.
	 *
	 * @param                   <O> the element type
	 * @param a                 the first collection, must not be null
	 * @param b                 the second collection, must not be null
	 * @param c                 the comparator to use for the merge.
	 * @param includeDuplicates if {@code true} duplicate elements will be retained,
	 *                          otherwise they will be removed in the output
	 *                          collection
	 * @return a new sorted List, containing the elements of Collection a and b
	 * @throws NullPointerException if either collection or the comparator is null
	 */
	public static <O> List<O> collate(final Iterable<? extends O> a, final Iterable<? extends O> b,
	        final Comparator<? super O> c, final boolean includeDuplicates) {
		return org.apache.commons.collections4.CollectionUtils.collate(a, b, c, includeDuplicates);
	}

	// -----------------------------------------------------------------------

	/**
	 * Returns a {@link Collection} of all the permutations of the input collection.
	 * <p>
	 * NOTE: the number of permutations of a given collection is equal to n!, where
	 * n is the size of the collection. Thus, the resulting collection will become
	 * <b>very</b> large for collections &gt; 10 (e.g. 10! = 3628800, 15! =
	 * 1307674368000).
	 * <p>
	 * For larger collections it is advised to use a {@link PermutationIterator} to
	 * iterate over all permutations.
	 *
	 * @see PermutationIterator
	 *
	 * @param            <E> the element type
	 * @param collection the collection to create permutations for, may not be null
	 * @return an unordered collection of all permutations of the input collection
	 * @throws NullPointerException if collection is null
	 */
	public static <E> Collection<List<E>> permutations(final Collection<E> collection) {
		return org.apache.commons.collections4.CollectionUtils.permutations(collection);
	}

	// -----------------------------------------------------------------------
	/**
	 * Returns a collection containing all the elements in <code>collection</code>
	 * that are also in <code>retain</code>. The cardinality of an element
	 * <code>e</code> in the returned collection is the same as the cardinality of
	 * <code>e</code> in <code>collection</code> unless <code>retain</code> does not
	 * contain <code>e</code>, in which case the cardinality is zero. This method is
	 * useful if you do not wish to modify the collection <code>c</code> and thus
	 * cannot call <code>c.retainAll(retain);</code>.
	 * <p>
	 * This implementation iterates over <code>collection</code>, checking each
	 * element in turn to see if it's contained in <code>retain</code>. If it's
	 * contained, it's added to the returned list. As a consequence, it is advised
	 * to use a collection type for <code>retain</code> that provides a fast (e.g.
	 * O(1)) implementation of {@link Collection#contains(Object)}.
	 *
	 * @param            <C> the type of object the {@link Collection} contains
	 * @param collection the collection whose contents are the target of the
	 *                   #retailAll operation
	 * @param retain     the collection containing the elements to be retained in
	 *                   the returned collection
	 * @return a <code>Collection</code> containing all the elements of
	 *         <code>collection</code> that occur at least once in
	 *         <code>retain</code>.
	 * @throws NullPointerException if either parameter is null
	 */
	public static <C> Collection<C> retainAll(final Collection<C> collection, final Collection<?> retain) {
		return org.apache.commons.collections4.CollectionUtils.retainAll(collection, retain);
	}

	/**
	 * Removes the elements in <code>remove</code> from <code>collection</code>.
	 * That is, this method returns a collection containing all the elements in
	 * <code>c</code> that are not in <code>remove</code>. The cardinality of an
	 * element <code>e</code> in the returned collection is the same as the
	 * cardinality of <code>e</code> in <code>collection</code> unless
	 * <code>remove</code> contains <code>e</code>, in which case the cardinality is
	 * zero. This method is useful if you do not wish to modify the collection
	 * <code>c</code> and thus cannot call
	 * <code>collection.removeAll(remove);</code>.
	 * <p>
	 * This implementation iterates over <code>collection</code>, checking each
	 * element in turn to see if it's contained in <code>remove</code>. If it's not
	 * contained, it's added to the returned list. As a consequence, it is advised
	 * to use a collection type for <code>remove</code> that provides a fast (e.g.
	 * O(1)) implementation of {@link Collection#contains(Object)}.
	 *
	 * @param            <E> the type of object the {@link Collection} contains
	 * @param collection the collection from which items are removed (in the
	 *                   returned collection)
	 * @param remove     the items to be removed from the returned
	 *                   <code>collection</code>
	 * @return a <code>Collection</code> containing all the elements of
	 *         <code>collection</code> except any elements that also occur in
	 *         <code>remove</code>.
	 * @throws NullPointerException if either parameter is null
	 */
	public static <E> Collection<E> removeAll(final Collection<E> collection, final Collection<?> remove) {
		return org.apache.commons.collections4.CollectionUtils.removeAll(collection, remove);
	}

	/**
	 * Extract the lone element of the specified Collection.
	 * 
	 * @param            <E> collection type
	 * @param collection to read
	 * @return sole member of collection
	 * @throws NullPointerException     if collection is null
	 * @throws IllegalArgumentException if collection is empty or contains more than
	 *                                  one element
	 */
	public static <E> E extractSingleton(final Collection<E> collection) {
		return org.apache.commons.collections4.CollectionUtils.extractSingleton(collection);
	}

}
