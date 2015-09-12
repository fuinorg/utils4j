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

import java.util.ArrayList;
import java.util.List;

import org.fuin.utils4j.filter.AndFilter;
import org.fuin.utils4j.filter.BooleanPropertyFilter;
import org.fuin.utils4j.filter.IntegerFilter;
import org.fuin.utils4j.filter.IntegerPropertyFilter;
import org.fuin.utils4j.filter.OrFilter;

/**
 * A basic filter example.
 */
public final class SimpleFilterExample {

    /**
     * Executes the example.
     */
    public final void execute() {

        // Create a list of persons
        final List<Person> persons = new ArrayList<Person>();
        persons.add(new Person(1, "Peter", "Parker", false));
        persons.add(new Person(2, "Mary Jane", "Watson", false));
        persons.add(new Person(3, "Harry", "Osborn", false));
        persons.add(new Person(4, "John", "Doe", true));
        persons.add(new Person(5, "Jane", "Doe", true));

        // Define a filter with several conditions
        final BooleanPropertyFilter unknownFilter = new BooleanPropertyFilter(
                "unknown", true);
        final IntegerPropertyFilter minIdFilter = new IntegerPropertyFilter(
                "id", IntegerFilter.Operator.GTE, 1);
        final IntegerPropertyFilter maxIdFilter = new IntegerPropertyFilter(
                "id", IntegerFilter.Operator.LTE, 2);
        final AndFilter minMaxFilter = new AndFilter(minIdFilter, maxIdFilter);
        final OrFilter orFilter = new OrFilter(minMaxFilter, unknownFilter);

        // Print the condition
        System.out.println("FILTER:");
        System.out.println(orFilter);
        System.out.println();

        // Apply filter
        System.out.println("RESULT:");
        for (int i = 0; i < persons.size(); i++) {
            final Person person = persons.get(i);
            if (orFilter.complies(person)) {
                System.out.println(person);
            }
        }

    }

    /**
     * Starts the example.
     * 
     * @param args
     *            Not used.
     */
    public static void main(final String[] args) {
        new SimpleFilterExample().execute();
    }

}
