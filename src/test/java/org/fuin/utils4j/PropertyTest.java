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

//CHECKSTYLE:OFF
public final class PropertyTest {

    private static EqualsHashCodeTestCase equalsHashCodeTestCase;

    /**
     * @testng.before-method
     */
    public final void beforeMethod() {
        equalsHashCodeTestCase = new EqualsHashCodeTestCase(PropertyTest.class.getName()) {

            protected Object createNotEqualInstance() throws Exception {
                return new Property("anotherKey", "1", "1");
            }

            protected Object createInstance() throws Exception {
                return new Property("key", "1", "1");
            }
        };
        equalsHashCodeTestCase.setUp();
    }

    /**
     * @testng.after-method
     */
    public final void afterMethod() {
        equalsHashCodeTestCase.tearDown();
        equalsHashCodeTestCase = null;
    }

    /**
     * @testng.test
     */
    public final void testConstruction() {
        final String key = "A";
        final String initialValue = null;
        final String value = "1";
        final Property prop = new Property(key, initialValue, value);
        Assert.assertEquals(prop.getKey(), key);
        Assert.assertEquals(prop.getInitialValue(), initialValue);
        Assert.assertEquals(prop.getValue(), value);
        Assert.assertEquals(prop.hasChanged(), true);
        Assert.assertEquals(prop.isNew(), true);
        Assert.assertEquals(prop.isDeleted(), false);
    }

    /**
     * @testng.test
     */
    public final void testSetGetValue() {
        final String key = "A";
        final String initialValue = "1";
        final String value = "1";
        final Property prop = new Property(key, initialValue, value);
        prop.setValue("2");
        Assert.assertEquals(prop.getValue(), "2");
    }

    /**
     * @testng.test
     */
    public final void testHasChanged() {
        final String key = "A";
        final String value = "1";

        final Property prop1 = new Property(key, value, value);
        Assert.assertFalse(prop1.hasChanged());

        final Property prop2 = new Property(key, null, value);
        Assert.assertTrue(prop2.isNew());

    }

    /**
     * @testng.test
     */
    public final void testIsNew() {
        final String key = "A";
        final String value = "1";

        final Property prop1 = new Property(key, value, value);
        Assert.assertFalse(prop1.isNew());

        final Property prop2 = new Property(key, null, value);
        Assert.assertTrue(prop2.isNew());

    }

    /**
     * @testng.test
     */
    public final void testIsDeleted() {
        final String key = "A";
        final String value = "1";

        // Delete an existing value
        final Property prop1 = new Property(key, value, value);
        Assert.assertFalse(prop1.isDeleted());
        prop1.setValue(null);
        Assert.assertTrue(prop1.isDeleted());

        // A new value can never get deleted
        final Property prop2 = new Property(key, null, value);
        prop2.setValue(null);
        Assert.assertFalse(prop2.isDeleted());
        prop2.setValue(value);
        Assert.assertFalse(prop2.isDeleted());

    }

    /**
     * @testng.test
     */
    public final void testGetStatus() {

        final Property newProp = new Property("one", null, "1");
        Assert.assertTrue(newProp.isNew());
        Assert.assertEquals(newProp.getStatus(), "NEW");

        final Property unchangedProp = new Property("one", "1", "1");
        Assert.assertFalse(unchangedProp.isNew());
        Assert.assertFalse(unchangedProp.hasChanged());
        Assert.assertFalse(unchangedProp.isDeleted());
        Assert.assertEquals(unchangedProp.getStatus(), "---");

        final Property changedProp = new Property("one", "1", "1");
        changedProp.setValue("123");
        Assert.assertTrue(changedProp.hasChanged());
        Assert.assertEquals(changedProp.getStatus(), "CHG");

        final Property deletedProp = new Property("one", "1", "1");
        deletedProp.setValue(null);
        Assert.assertTrue(deletedProp.isDeleted());
        Assert.assertEquals(deletedProp.getStatus(), "DEL");

    }

    /**
     * @testng.test
     */
    public final void testEqualsAgainstNewObject() {
        equalsHashCodeTestCase.testEqualsAgainstNewObject();
    }

    /**
     * @testng.test
     */
    public final void testEqualsAgainstNull() {
        equalsHashCodeTestCase.testEqualsAgainstNull();
    }

    /**
     * @testng.test
     */
    public final void testEqualsAgainstUnequalsObjects() {
        equalsHashCodeTestCase.testEqualsAgainstUnequalObjects();
    }

    /**
     * @testng.test
     */
    public final void testEqualsIsConstistentAcrossInvocations() {
        equalsHashCodeTestCase.testEqualsIsConsistentAcrossInvocations();
    }

    /**
     * @testng.test
     */
    public final void testEqualsIsReflexive() {
        equalsHashCodeTestCase.testEqualsIsReflexive();
    }

    /**
     * @testng.test
     */
    public final void testEqualsIsSymmetricAndTransitive() {
        equalsHashCodeTestCase.testEqualsIsSymmetricAndTransitive();
    }

    /**
     * @testng.test
     */
    public final void testHashCodeContract() {
        equalsHashCodeTestCase.testHashCodeContract();
    }

    /**
     * @testng.test
     */
    public final void testHashCodeIsConsistentAcrossInvocations() {
        equalsHashCodeTestCase.testHashCodeIsConsistentAcrossInvocations();
    }

}
// CHECKSTYLE:ON
