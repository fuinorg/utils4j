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

import java.util.ArrayList;
import java.util.List;

import org.fuin.utils4j.ChangeTrackingUniqueList;

/**
 * Short example for using the {@link ChangeTrackingUniqueList}.
 */
// CHECKSTYLE:OFF
public final class ChangeTrackingUniqueListExample {

    /**
     * Private constructor.
     */
    private ChangeTrackingUniqueListExample() {
        throw new UnsupportedOperationException(
                "It's not allowed to create an instance of this class!");
    }

    private static void printList(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
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

        // Create a standard string list
        List<String> list = new ArrayList<>();

        // Add some initial content
        list.add("one");
        list.add("two");
        list.add("three");

        // Wrap the list to track changes
        final ChangeTrackingUniqueList<String> trackingList = new ChangeTrackingUniqueList<>(
                list);

        // Add and remove some items
        trackingList.add("four");
        trackingList.add("five");
        trackingList.remove("three");

        // Print the status
        // Output: true
        System.out.println("HAS CHANGED:");
        System.out.println(trackingList.isChanged());
        System.out.println();

        // Print the deleted items
        // Output: "three"
        System.out.println("DELETED:");
        printList(trackingList.getDeleted());

        // Print the added items
        // Output: "four", "five"
        System.out.println("ADDED:");
        printList(trackingList.getAdded());

        // Revert all changes
        trackingList.revert();

        System.out.println("REVERTED:");
        // Output: "one", "two", "three"
        printList(trackingList);

    }

}
// CHECKSTYLE:ON
