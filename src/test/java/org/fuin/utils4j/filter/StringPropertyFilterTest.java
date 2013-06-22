/**
 * Copyright (C) 2009 Future Invent Informationsmanagement GmbH. All rights
 * reserved. <http://www.fuin.org/>
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fuin.utils4j.filter;

import org.testng.Assert;

//CHECKSTYLE:OFF
public class StringPropertyFilterTest extends PropertyFilterTest {

	protected final PropertyFilter createTestee(final String propertyName) {
		return new StringPropertyFilter(propertyName, StringFilter.Operator.EQ,
				"One");
	}

	/**
	 * @testng.test
	 */
	public final void testCompliesLT() {
		final Filter filter = new StringPropertyFilter(STRING_PROPERTY_NAME,
				StringFilter.Operator.LT, "2");
		Assert.assertTrue(filter.complies(new TestObject("1")));
		Assert.assertFalse(filter.complies(new TestObject("2")));
		Assert.assertFalse(filter.complies(new TestObject("3")));
	}

	/**
	 * @testng.test
	 */
	public final void testCompliesLTE() {
		final Filter filter = new StringPropertyFilter(STRING_PROPERTY_NAME,
				StringFilter.Operator.LTE, "2");
		Assert.assertTrue(filter.complies(new TestObject("1")));
		Assert.assertTrue(filter.complies(new TestObject("2")));
		Assert.assertFalse(filter.complies(new TestObject("3")));
	}

	/**
	 * @testng.test
	 */
	public final void testCompliesEQ() {
		final Filter filter = new StringPropertyFilter(STRING_PROPERTY_NAME,
				StringFilter.Operator.EQ, "2");
		Assert.assertFalse(filter.complies(new TestObject("1")));
		Assert.assertTrue(filter.complies(new TestObject("2")));
		Assert.assertFalse(filter.complies(new TestObject("3")));
	}

	/**
	 * @testng.test
	 */
	public final void testCompliesEQRELAXED() {
		Filter filter;
		filter = new StringPropertyFilter(STRING_PROPERTY_NAME,
				StringFilter.Operator.EQ_RELAXED, "abc");
		Assert.assertFalse(filter.complies(new TestObject("ab")));
		Assert.assertTrue(filter.complies(new TestObject("abc")));
		Assert.assertTrue(filter.complies(new TestObject("abcd")));
		Assert.assertTrue(filter.complies(new TestObject("abcde")));
	}

	/**
	 * @testng.test
	 */
	public final void testCompliesGT() {
		final Filter filter = new StringPropertyFilter(STRING_PROPERTY_NAME,
				StringFilter.Operator.GT, "2");
		Assert.assertFalse(filter.complies(new TestObject("1")));
		Assert.assertFalse(filter.complies(new TestObject("2")));
		Assert.assertTrue(filter.complies(new TestObject("3")));
	}

	/**
	 * @testng.test
	 */
	public final void testCompliesGTE() {
		final Filter filter = new StringPropertyFilter(STRING_PROPERTY_NAME,
				StringFilter.Operator.GTE, "2");
		Assert.assertFalse(filter.complies(new TestObject("1")));
		Assert.assertTrue(filter.complies(new TestObject("2")));
		Assert.assertTrue(filter.complies(new TestObject("3")));
	}

	/**
	 * @testng.test
	 */
	public final void testToString() {
		Assert.assertEquals(""
				+ new StringPropertyFilter(STRING_PROPERTY_NAME,
						StringFilter.Operator.LT, "2"), STRING_PROPERTY_NAME + " < '2'");
		Assert.assertEquals(""
				+ new StringPropertyFilter(STRING_PROPERTY_NAME,
						StringFilter.Operator.LTE, "2"), STRING_PROPERTY_NAME + " <= '2'");
		Assert.assertEquals(""
				+ new StringPropertyFilter(STRING_PROPERTY_NAME,
						StringFilter.Operator.EQ, "2"), STRING_PROPERTY_NAME + " = '2'");
		Assert.assertEquals(""
				+ new StringPropertyFilter(STRING_PROPERTY_NAME,
						StringFilter.Operator.EQ_RELAXED, "2"), STRING_PROPERTY_NAME + " ~ '2'");
		Assert.assertEquals(""
				+ new StringPropertyFilter(STRING_PROPERTY_NAME,
						StringFilter.Operator.GT, "2"), STRING_PROPERTY_NAME + " > '2'");
		Assert.assertEquals(""
				+ new StringPropertyFilter(STRING_PROPERTY_NAME,
						StringFilter.Operator.GTE, "2"), STRING_PROPERTY_NAME + " >= '2'");
	}


}
//CHECKSTYLE:ON
