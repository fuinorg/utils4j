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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a simple implementation of the {@link Variable} interface.
 */
@XmlRootElement(name = "variable")
@XmlAccessorType(XmlAccessType.FIELD)
public final class SimpleVariable implements Variable {

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "value")
    private String value;

    /**
     * Default constructor for JAXB.
     */
    protected SimpleVariable() {
        super();
    }

    /**
     * Constructor with mandatory data.
     * 
     * @param name
     *            Unique name - May not be <code>null</code> or empty.
     * @param value
     *            Variable value - May not be <code>null</code>.
     */
    public SimpleVariable(final String name, final String value) {
        super();
        Utils4J.checkNotNull("name", name);
        Utils4J.checkNotEmpty("name", name);
        Utils4J.checkNotNull("value", value);
        this.name = name;
        this.value = value;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final String getValue() {
        return value;
    }

    @Override
    public final int hashCode() {
        return name.hashCode();
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SimpleVariable other = (SimpleVariable) obj;
        return name.equals(other.name);
    }

    @Override
    public final String toString() {
        return name + "=" + value;
    }

}
