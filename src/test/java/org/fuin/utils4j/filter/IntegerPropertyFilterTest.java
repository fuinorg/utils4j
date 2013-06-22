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
public class IntegerPropertyFilterTest extends PropertyFilterTest {

	protected final PropertyFilter createTestee(final String propertyName) {
		return new IntegerPropertyFilter(propertyName,
				ComparableFilter.Operator.EQ, new Integer(1));
	}

	/**
	 * @testng.test
	 */
	public final void testCompliesLT() {
		final Filter filter = new IntegerPropertyFilter(INTEGER_PROPERTY_NAME,
				ComparableFilter.Operator.LT, new Integer(2));
		Assert.assertTrue(filter.complies(new TestObject(new Integer(1))));
		Assert.assertFalse(filter.complies(new TestObject(new Integer(2))));
		Assert.assertFalse(filter.complies(new TestObject(new Integer(3))));
	}

	/**
	 * @testng.test
	 */
	public final void testCompliesLTE() {
		final Filter filter = new IntegerPropertyFilter(INTEGER_PROPERTY_NAME,
				ComparableFilter.Operator.LTE, new Integer(2));
		Assert.assertTrue(filter.complies(new TestObject(new Integer(1))));
		Assert.assertTrue(filter.complies(new TestObject(new Integer(2))));
		Assert.assertFalse(filter.complies(new TestObject(new Integer(3))));
	}

	/**
	 * @testng.test
	 */
	public final void testCompliesEQ() {
		final Filter filter = new IntegerPropertyFilter(INTEGER_PROPERTY_NAME,
				ComparableFilter.Operator.EQ, new Integer(2));
		Assert.assertFalse(filter.complies(new TestObject(new Integer(1))));
		Assert.assertTrue(filter.complies(new TestObject(new Integer(2))));
		Assert.assertFalse(filter.complies(new TestObject(new Integer(3))));
	}

	/**
	 * @testng.test
	 */
	public final void testCompliesGT() {
		final Filter filter = new IntegerPropertyFilter(INTEGER_PROPERTY_NAME,
				ComparableFilter.Operator.GT, new Integer(2));
		Assert.assertFalse(filter.complies(new TestObject(new Integer(1))));
		Assert.assertFalse(filter.complies(new TestObject(new Integer(2))));
		Assert.assertTrue(filter.complies(new TestObject(new Integer(3))));
	}

	/**
	 * @testng.test
	 */
	public final void testCompliesGTE() {
		final Filter filter = new IntegerPropertyFilter(INTEGER_PROPERTY_NAME,
				ComparableFilter.Operator.GTE, new Integer(2));
		Assert.assertFalse(filter.complies(new TestObject(new Integer(1))));
		Assert.assertTrue(filter.complies(new TestObject(new Integer(2))));
		Assert.assertTrue(filter.complies(new TestObject(new Integer(3))));
	}

	/**
	 * @testng.test
	 */
	public final void testToString() {
		Assert.assertEquals(""
				+ new IntegerPropertyFilter(INTEGER_PROPERTY_NAME,
						ComparableFilter.Operator.LT, new Integer(2)), INTEGER_PROPERTY_NAME + " < 2");
		Assert.assertEquals(""
				+ new IntegerPropertyFilter(INTEGER_PROPERTY_NAME,
						ComparableFilter.Operator.LTE, new Integer(2)), INTEGER_PROPERTY_NAME + " <= 2");
		Assert.assertEquals(""
				+ new IntegerPropertyFilter(INTEGER_PROPERTY_NAME,
						ComparableFilter.Operator.EQ, new Integer(2)), INTEGER_PROPERTY_NAME + " = 2");
		Assert.assertEquals(""
				+ new IntegerPropertyFilter(INTEGER_PROPERTY_NAME,
						ComparableFilter.Operator.GT, new Integer(2)), INTEGER_PROPERTY_NAME + " > 2");
		Assert.assertEquals(""
				+ new IntegerPropertyFilter(INTEGER_PROPERTY_NAME,
						ComparableFilter.Operator.GTE, new Integer(2)), INTEGER_PROPERTY_NAME + " >= 2");
	}

}
//CHECKSTYLE:ON
