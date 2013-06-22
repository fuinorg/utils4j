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
public class BooleanFilterTest {

	/**
	 * @testng.test
	 */
	public final void testCreateAndGet() {
		Assert.assertEquals((new BooleanFilter(Boolean.TRUE)).getConstValue(), Boolean.TRUE);
		Assert.assertEquals((new BooleanFilter(Boolean.FALSE)).getConstValue(), Boolean.FALSE);
	}

    /**
     * @testng.test
     */
	public final void testComplies() {
		final Filter trueFilter = new BooleanFilter(Boolean.TRUE);
		final Filter falseFilter = new BooleanFilter(Boolean.FALSE);
		Assert.assertTrue(trueFilter.complies(Boolean.TRUE));
		Assert.assertFalse(trueFilter.complies(Boolean.FALSE));
		Assert.assertTrue(falseFilter.complies(Boolean.FALSE));
		Assert.assertFalse(falseFilter.complies(Boolean.TRUE));
	}

	/**
	 * @testng.test
	 */
	public final void testToString() {
		final Filter trueFilter = new BooleanFilter(Boolean.TRUE);
		final Filter falseFilter = new BooleanFilter(Boolean.FALSE);
		Assert.assertEquals(trueFilter.toString(), " = true");
		Assert.assertEquals(falseFilter.toString(), " = false");
	}

}
//CHECKSTYLE:ON
