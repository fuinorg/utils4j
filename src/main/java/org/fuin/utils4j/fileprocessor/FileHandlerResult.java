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
 * Result after a file was processed.
 */
public final class FileHandlerResult {

    /** Continue processing. */
    public static final FileHandlerResult CONTINUE = new FileHandlerResult("CONTINUE");

    /** Skips all files and sub directories in the current one. */
    public static final FileHandlerResult SKIP_ALL = new FileHandlerResult("SKIP_ALL");

    /** Skip all other files in the current directory. */
    public static final FileHandlerResult SKIP_FILES = new FileHandlerResult("SKIP_FILES");

    /** Skip all other sub directories of the current one. */
    public static final FileHandlerResult SKIP_SUBDIRS = new FileHandlerResult("SKIP_SUBDIRS");

    /** Stop searching. */
    public static final FileHandlerResult STOP = new FileHandlerResult("STOP");

    /** All enumeration instances. */
    public static final List<FileHandlerResult> INSTANCES = asList(CONTINUE, SKIP_ALL, SKIP_FILES, SKIP_SUBDIRS, STOP); // NOSONAR

    private final String name;

    /**
     * Private ENUM constructor.
     * 
     * @param name
     *            Unique name.
     */
    private FileHandlerResult(final String name) {
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
        final FileHandlerResult other = (FileHandlerResult) obj;
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
    public static FileHandlerResult fromName(final String name) {
        if (name == null) {
            return null;
        }
        for (final FileHandlerResult result : INSTANCES) {
            if (result.name.equals(name)) {
                return result;
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
        for (final FileHandlerResult result : INSTANCES) {
            if (result.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    private static List<FileHandlerResult> asList(final FileHandlerResult... handler) {
        final List<FileHandlerResult> list = new ArrayList<>(handler.length);
        for (final FileHandlerResult result : handler) {
            list.add(result);
        }
        return Collections.unmodifiableList(list);
    }

}
