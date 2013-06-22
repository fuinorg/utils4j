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
public class StringFilterTest {

	/**
	 * @testng.test
	 */
	public final void testCompliesLT() {
		final Filter filter = new StringFilter(StringFilter.Operator.LT, "2");
		Assert.assertTrue(filter.complies("1"));
		Assert.assertFalse(filter.complies("2"));
		Assert.assertFalse(filter.complies("3"));
	}

	/**
	 * @testng.test
	 */
	public final void testCompliesLTE() {
		final Filter filter = new StringFilter(StringFilter.Operator.LTE, "2");
		Assert.assertTrue(filter.complies("1"));
		Assert.assertTrue(filter.complies("2"));
		Assert.assertFalse(filter.complies("3"));
	}

	/**
	 * @testng.test
	 */
	public final void testCompliesEQ() {
		final Filter filter = new StringFilter(StringFilter.Operator.EQ, "2");
		Assert.assertFalse(filter.complies("1"));
		Assert.assertTrue(filter.complies("2"));
		Assert.assertFalse(filter.complies("3"));
	}

	/**
	 * @testng.test
	 */
	public final void testCompliesEQRELAXED() {
		Filter filter;
		filter = new StringFilter(StringFilter.Operator.EQ_RELAXED, "abc");
		Assert.assertFalse(filter.complies("ab"));
		Assert.assertTrue(filter.complies("abc"));
		Assert.assertTrue(filter.complies("abcd"));
		Assert.assertTrue(filter.complies("abcde"));
	}

	/**
	 * @testng.test
	 */
	public final void testCompliesGT() {
		final Filter filter = new StringFilter(StringFilter.Operator.GT, "2");
		Assert.assertFalse(filter.complies("1"));
		Assert.assertFalse(filter.complies("2"));
		Assert.assertTrue(filter.complies("3"));
	}

	/**
	 * @testng.test
	 */
	public final void testCompliesGTE() {
		final Filter filter = new StringFilter(StringFilter.Operator.GTE, "2");
		Assert.assertFalse(filter.complies("1"));
		Assert.assertTrue(filter.complies("2"));
		Assert.assertTrue(filter.complies("3"));
	}

	/**
	 * @testng.test
	 */
	public final void testToString() {
		Assert.assertEquals(""
				+ new StringFilter(StringFilter.Operator.LT, "2"), " < '2'");
		Assert.assertEquals(""
				+ new StringFilter(StringFilter.Operator.LTE, "2"), " <= '2'");
		Assert.assertEquals(""
				+ new StringFilter(StringFilter.Operator.EQ, "2"), " = '2'");
		Assert.assertEquals(""
				+ new StringFilter(StringFilter.Operator.EQ_RELAXED, "2"), " ~ '2'");
		Assert.assertEquals(""
				+ new StringFilter(StringFilter.Operator.GT, "2"), " > '2'");
		Assert.assertEquals(""
				+ new StringFilter(StringFilter.Operator.GTE, "2"), " >= '2'");
	}

}
//CHECKSTYLE:ON
