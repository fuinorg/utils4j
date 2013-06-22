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
package org.fuin.utils4j;

import org.testng.Assert;

/**
 * Tests for IllegalNullArgumentException.
 */
public class IllegalNullArgumentExceptionTest {

    /**
     * @testng.test
     */
    public final void testConstruction() {
        final String argument = "xyz123";
        final IllegalNullArgumentException ex = new IllegalNullArgumentException(argument);
        Assert.assertEquals(ex.getArgument(), argument);
        Assert.assertEquals(ex.getMessage(), "The argument '" + argument + "' cannot be null!");
        Assert.assertNull(ex.getCause());
    }

}
