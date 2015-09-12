/**
 * Copyright (C) 2015 Michael Schnell. All rights reserved. 
 * http://www.fuin.org/
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
 * along with this library. If not, see http://www.gnu.org/licenses/.
 */
package org.fuin.utils4j;

import static org.assertj.core.api.StrictAssertions.assertThat;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Test;

//CHECKSTYLE:OFF
public final class PropertyTest {

    @Test
    public final void testConstruction() {
        final String key = "A";
        final String initialValue = null;
        final String value = "1";
        final Property prop = new Property(key, initialValue, value);
        assertThat(prop.getKey()).isEqualTo(key);
        assertThat(prop.getInitialValue()).isEqualTo(initialValue);
        assertThat(prop.getValue()).isEqualTo(value);
        assertThat(prop.hasChanged()).isTrue();
        assertThat(prop.isNew()).isTrue();
        assertThat(prop.isDeleted()).isFalse();
    }

    @Test
    public final void testSetGetValue() {
        final String key = "A";
        final String initialValue = "1";
        final String value = "1";
        final Property prop = new Property(key, initialValue, value);
        prop.setValue("2");
        assertThat(prop.getValue()).isEqualTo("2");
    }

    @Test
    public final void testHasChanged() {
        final String key = "A";
        final String value = "1";

        final Property prop1 = new Property(key, value, value);
        assertThat(prop1.hasChanged()).isFalse();

        final Property prop2 = new Property(key, null, value);
        assertThat(prop2.isNew()).isTrue();

    }

    @Test
    public final void testIsNew() {
        final String key = "A";
        final String value = "1";

        final Property prop1 = new Property(key, value, value);
        assertThat(prop1.isNew()).isFalse();

        final Property prop2 = new Property(key, null, value);
        assertThat(prop2.isNew()).isTrue();

    }

    @Test
    public final void testIsDeleted() {
        final String key = "A";
        final String value = "1";

        // Delete an existing value
        final Property prop1 = new Property(key, value, value);
        assertThat(prop1.isDeleted()).isFalse();
        prop1.setValue(null);
        assertThat(prop1.isDeleted()).isTrue();

        // A new value can never get deleted
        final Property prop2 = new Property(key, null, value);
        prop2.setValue(null);
        assertThat(prop2.isDeleted()).isFalse();
        prop2.setValue(value);
        assertThat(prop2.isDeleted()).isFalse();

    }

    @Test
    public final void testGetStatus() {

        final Property newProp = new Property("one", null, "1");
        assertThat(newProp.isNew()).isTrue();
        assertThat(newProp.getStatus()).isEqualTo("NEW");

        final Property unchangedProp = new Property("one", "1", "1");
        assertThat(unchangedProp.isNew()).isFalse();
        assertThat(unchangedProp.hasChanged()).isFalse();
        assertThat(unchangedProp.isDeleted()).isFalse();
        assertThat(unchangedProp.getStatus()).isEqualTo("---");

        final Property changedProp = new Property("one", "1", "1");
        changedProp.setValue("123");
        assertThat(changedProp.hasChanged()).isTrue();
        assertThat(changedProp.getStatus()).isEqualTo("CHG");

        final Property deletedProp = new Property("one", "1", "1");
        deletedProp.setValue(null);
        assertThat(deletedProp.isDeleted()).isTrue();
        assertThat(deletedProp.getStatus()).isEqualTo("DEL");

    }

    @Test
    public final void testEqualsHashCode() {

        final Property property1 = new Property("anotherKey", "1", "1");
        final Property property2 = new Property("key", "1", "1");
        EqualsVerifier.forExamples(property1, property2).suppress(Warning.NULL_FIELDS).verify();

    }

}
// CHECKSTYLE:ON
