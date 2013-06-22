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
public class RegExprPropertyFilterTest extends PropertyFilterTest {

	protected final PropertyFilter createTestee(final String propertyName) {
		return new RegExprPropertyFilter(propertyName, "a*b");
	}

	/**
	 * @testng.test
	 */
	public final void testSetGetType() {
		final RegExprPropertyFilter filter = new RegExprPropertyFilter(
				STRING_PROPERTY_NAME, "a*b");

		filter.setType(RegExprFilter.MATCHES);
		Assert.assertEquals(filter.getType(), RegExprFilter.MATCHES);
		Assert.assertEquals(filter.getTypeName(), "matches");

		filter.setType(RegExprFilter.LOOKING_AT);
		Assert.assertEquals(filter.getType(), RegExprFilter.LOOKING_AT);
		Assert.assertNull(filter.getTypeName());

		filter.setType(RegExprFilter.FIND);
		Assert.assertEquals(filter.getType(), RegExprFilter.FIND);
		Assert.assertEquals(filter.getTypeName(), "find");
	}

	/**
	 * @testng.test
	 */
	public final void testSetGetTypeName() {
		final RegExprPropertyFilter filter = new RegExprPropertyFilter(
				STRING_PROPERTY_NAME, "a*b");

		filter.setTypeName("matches");
		Assert.assertEquals(filter.getTypeName(), "matches");
		Assert.assertEquals(filter.getType(), RegExprFilter.MATCHES);

		filter.setTypeName("lookingAt");
		Assert.assertNull(filter.getTypeName());
		Assert.assertEquals(filter.getType(), RegExprFilter.LOOKING_AT);

		filter.setTypeName("find");
		Assert.assertEquals(filter.getTypeName(), "find");
		Assert.assertEquals(filter.getType(), RegExprFilter.FIND);

	}

	/**
	 * @testng.test
	 */
	public final void testCompliesMatches() {

		final RegExprPropertyFilter filter1 = new RegExprPropertyFilter(
				STRING_PROPERTY_NAME, "a*b");
		filter1.setType(RegExprFilter.MATCHES);
		Assert.assertTrue(filter1.complies(new TestObject("ab")));
		Assert.assertTrue(filter1.complies(new TestObject("aab")));
		Assert.assertTrue(filter1.complies(new TestObject("aaab")));
		Assert.assertFalse(filter1.complies(new TestObject("aaabb")));
		Assert.assertFalse(filter1.complies(new TestObject("abc")));
		Assert.assertFalse(filter1.complies(new TestObject("bab")));
		Assert.assertFalse(filter1.complies(new TestObject("xbab")));

		final RegExprPropertyFilter filter2 = new RegExprPropertyFilter(
				STRING_PROPERTY_NAME, ".*schlemmerinfo\\.de.*");
		filter2.setType(RegExprFilter.MATCHES);
		Assert.assertTrue(filter2.complies(new TestObject(
				"http://www.schlemmerinfo.de/eng/")));
		Assert.assertTrue(filter2.complies(new TestObject(
				"http://www.schlemmerinfo.de/eng/hamburg/")));
		Assert.assertTrue(filter2.complies(new TestObject(
				"http://www.schlemmerinfo.de/deu")));
		Assert.assertTrue(filter2.complies(new TestObject("www.schlemmerinfo.de")));

	}

	/**
	 * @testng.test
	 */
	public final void testCompliesLookingAt() {

		final RegExprPropertyFilter filter1 = new RegExprPropertyFilter(
				STRING_PROPERTY_NAME, "a*b");
		filter1.setType(RegExprFilter.LOOKING_AT);
		Assert.assertTrue(filter1.complies(new TestObject("ab")));
		Assert.assertTrue(filter1.complies(new TestObject("aab")));
		Assert.assertTrue(filter1.complies(new TestObject("aaab")));
		Assert.assertTrue(filter1.complies(new TestObject("aaabb")));
		Assert.assertTrue(filter1.complies(new TestObject("abc")));
		Assert.assertTrue(filter1.complies(new TestObject("bab")));
		Assert.assertFalse(filter1.complies(new TestObject("xbab")));

		final RegExprPropertyFilter filter2 = new RegExprPropertyFilter(
				STRING_PROPERTY_NAME, ".*schlemmerinfo\\.de.*");
		filter2.setType(RegExprFilter.LOOKING_AT);
		Assert.assertTrue(filter2.complies(new TestObject(
				"http://www.schlemmerinfo.de/eng/")));
		Assert.assertTrue(filter2.complies(new TestObject(
				"http://www.schlemmerinfo.de/eng/hamburg/")));
		Assert.assertTrue(filter2.complies(new TestObject(
				"http://www.schlemmerinfo.de/deu")));
		Assert.assertTrue(filter2.complies(new TestObject("www.schlemmerinfo.de")));

	}

	/**
	 * @testng.test
	 */
	public final void testCompliesFind() {

		final RegExprPropertyFilter filter1 = new RegExprPropertyFilter(
				STRING_PROPERTY_NAME, "a*b");
		filter1.setType(RegExprFilter.FIND);
		Assert.assertTrue(filter1.complies(new TestObject("ab")));
		Assert.assertTrue(filter1.complies(new TestObject("aab")));
		Assert.assertTrue(filter1.complies(new TestObject("aaab")));
		Assert.assertTrue(filter1.complies(new TestObject("aaabb")));
		Assert.assertTrue(filter1.complies(new TestObject("abc")));
		Assert.assertTrue(filter1.complies(new TestObject("bab")));
		Assert.assertTrue(filter1.complies(new TestObject("xbab")));

		final RegExprPropertyFilter filter2 = new RegExprPropertyFilter(
				STRING_PROPERTY_NAME, ".*schlemmerinfo\\.de.*");
		filter2.setType(RegExprFilter.FIND);
		Assert.assertTrue(filter2.complies(new TestObject(
				"http://www.schlemmerinfo.de/eng/")));
		Assert.assertTrue(filter2.complies(new TestObject(
				"http://www.schlemmerinfo.de/eng/hamburg/")));
		Assert.assertTrue(filter2.complies(new TestObject(
				"http://www.schlemmerinfo.de/deu")));
		Assert.assertTrue(filter2.complies(new TestObject("www.schlemmerinfo.de")));

	}
	
}
//CHECKSTYLE:ON
