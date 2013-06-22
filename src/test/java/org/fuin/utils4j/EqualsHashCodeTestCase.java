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

/*
 * For some strange reason this class creates a "org.testng.TestNGException" in the Maven build,
 * but the build is still SUCCESS...
 * org.testng.TestNGException:
 * Failure in JUnit mode for class org.fuin.utils4j.EqualsHashCodeTestCase: could not 
 * create/run JUnit test suite: cannot retrieve JUnit method
 */

//CHECKSTYLE:OFF
public abstract class EqualsHashCodeTestCase extends junitx.extensions.EqualsHashCodeTestCase {

    public EqualsHashCodeTestCase(final String name) {
        super(name);
    }

    public void setUp() {
        try {
            super.setUp();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void tearDown() {
        try {
            super.tearDown();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    

}
// CHECKSTYLE:ON
