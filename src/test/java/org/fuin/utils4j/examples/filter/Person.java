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
package org.fuin.utils4j.examples.filter;

/**
 * A person class used for the examples.
 */
public final class Person {

    private final int id;

    private final String firstName;

    private final String lastName;

    private final boolean unknown;

    /**
     * Constructor with all necessary data.
     * 
     * @param id
     *            Unique ID.
     * @param firstName
     *            First name.
     * @param lastName
     *            Last name.
     * @param unknown
     *            Is the person known?
     */
    public Person(final int id, final String firstName, final String lastName, final boolean unknown) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.unknown = unknown;
    }

    /**
     * Returns the unique ID.
     * 
     * @return ID.
     */
    public final int getId() {
        return id;
    }

    /**
     * Returns the first name.
     * 
     * @return Name.
     */
    public final String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name.
     * 
     * @return Name.
     */
    public final String getLastName() {
        return lastName;
    }

    /**
     * Returns if the person is unknown.
     * 
     * @return If the person is known <code>true</code> else <code>false</code>.
     */
    public final boolean isUnknown() {
        return unknown;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return firstName + " " + lastName + " [# " + id + "]";
    }

}
