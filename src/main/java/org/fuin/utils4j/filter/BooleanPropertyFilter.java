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

/**
 * Defines a filter on a Boolean value.
 */
public class BooleanPropertyFilter extends PropertyFilter {

    private final BooleanFilter filter;

    /**
     * Constructor with all necessary values.
     * 
     * @param newPropertyName
     *            Property name.
     * @param constValue
     *            Value the property is compared with.
     */
    public BooleanPropertyFilter(final String newPropertyName, final Boolean constValue) {
        super(newPropertyName);
        this.filter = new BooleanFilter(constValue);
    }

    @Override
    protected final String[] createGetterNames(final String property) {
        final String name = Character.toUpperCase(property.charAt(0)) + property.substring(1);
        return new String[] { "is" + name, "get" + name };
    }

    /**
     * Returns the value the property is compared with.
     * 
     * @return Value.
     */
    public final Boolean getConstValue() {
        return filter.getConstValue();
    }

    @Override
    public final boolean complies(final Object obj) {
        final Object value = getProperty(obj, getPropertyName());
        return filter.complies(value);
    }

    @Override
    public final String toString() {
        return getPropertyName() + filter.toString();
    }

}
