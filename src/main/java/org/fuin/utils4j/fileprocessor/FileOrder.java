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
package org.fuin.utils4j.fileprocessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Sort order of files.
 */
public final class FileOrder {

    /** No order. */
    public static final FileOrder DEFAULT = new FileOrder("DEFAULT");

    /** Files first. */
    public static final FileOrder FILES_FIRST = new FileOrder("FILES_FIRST");

    /** Directories first. */
    public static final FileOrder DIR_FIRST = new FileOrder("DIR_FIRST");

    /** All enumeration instances. */
    public static final List<FileOrder> INSTANCES = asList( DEFAULT, FILES_FIRST, DIR_FIRST ); //NOSONAR

    private final String name;

    /**
     * Private ENUM constructor.
     * 
     * @param name
     *            Unique name.
     */
    private FileOrder(final String name) {
        super();
        this.name = name;
    }

    // CHECKSTYLE:OFF Generated code
    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
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
        final FileOrder other = (FileOrder) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    // CHECKSTYLE:ON

    @Override
    public final String toString() {
        return name;
    }

    /**
     * Returns the instance by it's name or throws an exception otherwise.
     * 
     * @param name
     *            Unique name or NULL.
     * 
     * @return Enumeration instance or NULL (if argument name was NULL).
     */
    public static FileOrder fromName(final String name) {
        if (name == null) {
            return null;
        }
        for (final FileOrder fo : INSTANCES) {
            if (fo.name.equals(name)) {
                return fo;
            }
        }
        throw new IllegalArgumentException("Unknown name: " + name);
    }

    /**
     * Returns if an instance with a given name exists.
     * 
     * @param name
     *            Unique name or NULL (returns also TRUE).
     * 
     * @return If the name is known TRUE else FALSE.
     */
    public static boolean isValid(final String name) {
        if (name == null) {
            return true;
        }
        for (final FileOrder fo : INSTANCES) {
            if (fo.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    private static List<FileOrder> asList(final FileOrder...handler) {
	final List<FileOrder> list = new ArrayList<>(handler.length);
	for (final FileOrder result : handler) {
	    list.add(result);
	}
	return Collections.unmodifiableList(list);
    }
    
}
