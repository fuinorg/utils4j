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
public class BooleanPropertyFilterTest extends PropertyFilterTest {

	private static final String PROPERTY_NAME = "flag";

	protected final PropertyFilter createTestee(final String propertyName) {
		return new BooleanPropertyFilter(propertyName, Boolean.TRUE);
	}

	/**
	 * @testng.test
	 */
	public final void testCreateAndGet() {
		final BooleanPropertyFilter filterTRUE = new BooleanPropertyFilter(
				PROPERTY_NAME, Boolean.TRUE);
		Assert.assertEquals(filterTRUE.getConstValue(), Boolean.TRUE);
		Assert.assertEquals(filterTRUE.getPropertyName(), PROPERTY_NAME);

		final BooleanPropertyFilter filterFALSE = new BooleanPropertyFilter(
				PROPERTY_NAME, Boolean.FALSE);
		Assert.assertEquals(filterFALSE.getConstValue(), Boolean.FALSE);
		Assert.assertEquals(filterFALSE.getPropertyName(), PROPERTY_NAME);
	}

	/**
	 * @testng.test
	 */
	public final void testComplies() {
		final Filter trueFilter = new BooleanPropertyFilter(PROPERTY_NAME,
				Boolean.TRUE);
		final Filter falseFilter = new BooleanPropertyFilter(PROPERTY_NAME,
				Boolean.FALSE);
		final TestObject trueObj = new TestObject(Boolean.TRUE);
		final TestObject falseObj = new TestObject(Boolean.FALSE);
		Assert.assertTrue(trueFilter.complies(trueObj));
		Assert.assertFalse(trueFilter.complies(falseObj));
		Assert.assertTrue(falseFilter.complies(falseObj));
		Assert.assertFalse(falseFilter.complies(trueObj));
	}

	/**
	 * @testng.test
	 */
	public final void testToString() {
		final Filter trueFilter = new BooleanPropertyFilter(PROPERTY_NAME,
				Boolean.TRUE);
		final Filter falseFilter = new BooleanPropertyFilter(PROPERTY_NAME,
				Boolean.FALSE);

		Assert.assertEquals(trueFilter.toString(), PROPERTY_NAME + " = true");
		Assert.assertEquals(falseFilter.toString(), PROPERTY_NAME + " = false");

	}

}
//CHECKSTYLE:ON
