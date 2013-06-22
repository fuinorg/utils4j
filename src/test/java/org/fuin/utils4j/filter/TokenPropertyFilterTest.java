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
public class TokenPropertyFilterTest extends PropertyFilterTest {

	protected final PropertyFilter createTestee(final String propertyName) {
		return new TokenPropertyFilter(propertyName, "one", ";");
	}

	/**
	 * @testng.test
	 */
	public final void testCompliesObject() {
		Filter testee;

		testee = new TokenPropertyFilter(STRING_PROPERTY_NAME, "one", ",");
		Assert.assertTrue(testee.complies((new TestObject("one"))));
		Assert.assertTrue(testee.complies((new TestObject("one,two"))));
		Assert.assertTrue(testee.complies((new TestObject("one,two,three"))));
		Assert.assertTrue(testee.complies((new TestObject("two,one,three"))));
		Assert.assertTrue(testee.complies((new TestObject("two,three,one"))));
		Assert.assertTrue(testee.complies((new TestObject("two,one"))));

		testee = new TokenPropertyFilter(STRING_PROPERTY_NAME, "one", ";");
		Assert.assertTrue(testee.complies((new TestObject("one"))));
		Assert.assertTrue(testee.complies((new TestObject("one;two"))));
		Assert.assertTrue(testee.complies((new TestObject("one;two;three"))));
		Assert.assertTrue(testee.complies((new TestObject("two;one;three"))));
		Assert.assertTrue(testee.complies((new TestObject("two;three;one"))));
		Assert.assertTrue(testee.complies((new TestObject("two;one"))));

		testee = new TokenPropertyFilter(STRING_PROPERTY_NAME, "four", ";");
		Assert.assertFalse(testee.complies((new TestObject("one;two;three"))));

		testee = new TokenPropertyFilter(STRING_PROPERTY_NAME, "x", ";");
		Assert.assertFalse(testee.complies((new TestObject("one;two;three"))));

		testee = new TokenPropertyFilter(STRING_PROPERTY_NAME, " ", ";");
		Assert.assertTrue(testee.complies((new TestObject(
				"one;two;three; ;five"))));

	}

	/**
	 * @testng.test
	 */
	public final void testToString() {
		Assert.assertEquals(""
				+ new TokenPropertyFilter(STRING_PROPERTY_NAME, "one", ","),
				STRING_PROPERTY_NAME + " contains 'one' [,]");
	}

}
// CHECKSTYLE:ON
