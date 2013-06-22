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
public class AndFilterTest extends ListFilterTest {

	protected final ListFilter createTestee(final String name) {
		final AndFilter filter = new AndFilter();
		filter.setAndStr(name);
		return filter;
	}

	/**
	 * @testng.test
	 */
	public final void testConstructor() {
		final Filter filterA = new DummyFilter();
		final Filter filterB = new DummyFilter();
		final AndFilter filter = new AndFilter(filterA, filterB);
		Assert.assertNotNull(filter.getFilterList());
		Assert.assertEquals(filter.getFilterList().size(), 2);
		Assert.assertEquals(filter.getFilterList().get(0), filterA);
		Assert.assertEquals(filter.getFilterList().get(1), filterB);
	}

	/**
	 * @testng.test
	 */
	public final void testComplies() {

		final Filter filterTRUE = new DummyFilter("true", true);
		final Filter filterFALSE = new DummyFilter("false", false);

		Assert.assertTrue((new AndFilter(filterTRUE, filterTRUE))
				.complies("Does not matter"));
		Assert.assertFalse((new AndFilter(filterTRUE, filterFALSE))
				.complies("Does not matter"));
		Assert.assertFalse((new AndFilter(filterFALSE, filterTRUE))
				.complies("Does not matter"));
		Assert.assertFalse((new AndFilter(filterFALSE, filterFALSE))
				.complies("Does not matter"));

	}

	/**
	 * @testng.test
	 */
	public final void testSetGetAndStr() {
		final AndFilter filter = new AndFilter();
		filter.setAndStr("&&");
		Assert.assertEquals(filter.getAndStr(), "&&");
	}

	/**
	 * @testng.test
	 */
	public final void testToString() {
		final Filter filterA = new DummyFilter("A");
		final Filter filterB = new DummyFilter("B");
		final Filter filterC = new DummyFilter("C");
		final AndFilter filter = new AndFilter(filterA, filterB);

		filter.setAndStr("&&");
		Assert.assertEquals(filter.toString(), "(A && B)");

		filter.setAndStr("and");
		filter.setOpenBracket("[");
		filter.setCloseBracket("]");
		Assert.assertEquals(filter.toString(), "[A and B]");

		filter.addFilter(filterC);
		Assert.assertEquals(filter.toString(), "[A and B and C]");
	}

}
//CHECKSTYLE:ON
