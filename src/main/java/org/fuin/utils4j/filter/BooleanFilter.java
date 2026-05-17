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

import org.jspecify.annotations.Nullable;

/**
 * Defines a filter on a Boolean property value.
 */
public class BooleanFilter implements Filter {

    @Nullable
    private final Boolean constValue;

    /**
     * Constructor with all necessary values.
     * 
     * @param constValue
     *            Value the property is compared with.
     */
    public BooleanFilter(@Nullable final Boolean constValue) {
        super();
        this.constValue = constValue;
    }

    /**
     * Returns the value the property is compared with.
     * 
     * @return Value.
     */
    @Nullable
    public final Boolean getConstValue() {
        return constValue;
    }

    @Override
    public final boolean complies(@Nullable final Object value) {
        if (value == null) {
            return (constValue == null);
        }
        return value.equals(constValue);
    }

    @Override
    public final String toString() {
        return " = " + constValue;
    }

}
