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
package org.fuin.utils4j.filter;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

//CHECKSTYLE:OFF
public abstract class PropertyFilterTest {

    protected static final String STRING_PROPERTY_NAME = "text";

    protected static final String INTEGER_PROPERTY_NAME = "count";

    protected static class TestObject {
        private String text = null;

        private Integer count = null;

        private Boolean flag = null;

        public TestObject(final String string) {
            super();
            this.text = string;
        }

        public TestObject(final Integer integer) {
            super();
            this.count = integer;
        }

        public TestObject(final Boolean flag) {
            super();
            this.flag = flag;
        }

        public final String getText() {
            return text;
        }

        public final Integer getCount() {
            return count;
        }

        public final Boolean getFlag() {
            return flag;
        }
    }

    protected abstract PropertyFilter createTestee(String propertyName);

    @Test
    public final void testSetGetProperty() {
        final String propertyName = "whatever";
        final PropertyFilter testee = createTestee(propertyName);
        assertThat(testee.getPropertyName()).isEqualTo(propertyName);
    }

}
// CHECKSTYLE:ON
