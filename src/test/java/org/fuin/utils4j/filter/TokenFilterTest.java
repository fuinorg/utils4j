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
public class TokenFilterTest {

	/**
	 * @testng.test
	 */
	public final void testCompliesObject() {
		Filter testee;

		testee = new TokenFilter("one", ",");
		Assert.assertTrue(testee.complies("one"));
		Assert.assertTrue(testee.complies("one,two"));
		Assert.assertTrue(testee.complies("one,two,three"));
		Assert.assertTrue(testee.complies("two,one,three"));
		Assert.assertTrue(testee.complies("two,three,one"));
		Assert.assertTrue(testee.complies("two,one"));

		testee = new TokenFilter("one", ";");
		Assert.assertTrue(testee.complies("one"));
		Assert.assertTrue(testee.complies("one;two"));
		Assert.assertTrue(testee.complies("one;two;three"));
		Assert.assertTrue(testee.complies("two;one;three"));
		Assert.assertTrue(testee.complies("two;three;one"));
		Assert.assertTrue(testee.complies("two;one"));

		testee = new TokenFilter("four", ";");
		Assert.assertFalse(testee.complies("one;two;three"));

		testee = new TokenFilter("x", ";");
		Assert.assertFalse(testee.complies("one;two;three"));

		testee = new TokenFilter(" ", ";");
		Assert.assertTrue(testee.complies("one;two;three; ;five"));

	}

	/**
	 * @testng.test
	 */
	public final void testToString() {
		Assert.assertEquals("" + new TokenFilter("one", ","), " contains 'one' [,]");
	}


}
//CHECKSTYLE:ON
