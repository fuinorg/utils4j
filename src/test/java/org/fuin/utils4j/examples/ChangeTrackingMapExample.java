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
package org.fuin.utils4j.examples;

import org.fuin.utils4j.ChangeTrackingMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Short example for using the {@link ChangeTrackingMap}.
 */
// CHECKSTYLE:OFF
public final class ChangeTrackingMapExample {

    /**
     * Private constructor.
     */
    private ChangeTrackingMapExample() {
        throw new UnsupportedOperationException("It's not allowed to create an instance of this class!");
    }

    private static void printMap(final Map<String, Integer> added) {
        final Iterator<String> addIter = added.keySet().iterator();
        while (addIter.hasNext()) {
            final String key = addIter.next();
            System.out.println(key + "=" + added.get(key));
        }
        System.out.println();
    }

    /**
     * Executes the example.
     * 
     * @param args
     *            Not used.
     */
    public static void main(final String[] args) {

        // Create a standard map
        final Map<String, Integer> map = new HashMap<>();

        // Add some initial content
        map.put("one", Integer.valueOf(1));
        map.put("two", Integer.valueOf(2));
        map.put("three", Integer.valueOf(3));

        // Wrap the list to track changes
        final ChangeTrackingMap<String, Integer> trackingMap = new ChangeTrackingMap<>(map);

        // Add/change/remove item
        trackingMap.put("four", Integer.valueOf(4));
        trackingMap.put("three", Integer.valueOf(10));
        trackingMap.remove("one");

        // Print the status
        // Output: true
        System.out.println("HAS CHANGED:");
        System.out.println(trackingMap.isChanged());
        System.out.println();

        // Print the added items
        // Output: "four=4"
        System.out.println("ADDED:");
        printMap(trackingMap.getAdded());

        // Print the changed items
        // Output: "three=3"
        System.out.println("CHANGED:");
        printMap(trackingMap.getChanged());

        // Print the removed items
        // Output: "one=1"
        System.out.println("REMOVED:");
        printMap(trackingMap.getRemoved());

        // Revert all changes
        trackingMap.revert();

        System.out.println("REVERTED:");
        // Output: "one=1", "two=2", "three=3"
        printMap(trackingMap);

    }

}
// CHECKSTYLE:ON
