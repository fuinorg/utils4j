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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.StrictAssertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//CHECKSTYLE:OFF
public class ChangeTrackingUniqueListTest {

    private ChangeTrackingUniqueList<String> initialEmptyList;

    private ChangeTrackingUniqueList<String> initialFilledList;

    @Before
    public final void beforeMethod() {
        initialEmptyList = new ChangeTrackingUniqueList<String>(new ArrayList<String>());
        initialFilledList = new ChangeTrackingUniqueList<String>(toList("one", "two", "three"));

    }

    @After
    public final void afterMethod() {
        initialFilledList = null;
        initialEmptyList = null;
    }

    @Test
    public void testAddObjectToInitialEmptyList() {

        // Add one
        initialEmptyList.add("one");
        assertThat(initialEmptyList.isChanged()).isTrue();
        assertThat(initialEmptyList.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyList.getAdded()).containsExactly("one");
        assertThat(initialEmptyList.getDeleted()).isEmpty();

        // Add second
        initialEmptyList.add("two");
        assertThat(initialEmptyList.isChanged()).isTrue();
        assertThat(initialEmptyList.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyList.getAdded()).containsExactly("one", "two");
        assertThat(initialEmptyList.getDeleted()).isEmpty();

    }

    @Test
    public void testAddObjectToInitialFilledList() {

        // Add one
        initialFilledList.add("four");
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList.getAdded()).containsExactly("four");
        assertThat(initialFilledList.getDeleted()).isEmpty();

        // Add second
        initialFilledList.add("five");
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList.getAdded()).containsExactly("four", "five");
        assertThat(initialFilledList.getDeleted()).isEmpty();

    }

    @Test
    public void testAddIntObjectToInitialEmptyList() {

        // Add one
        initialEmptyList.add(0, "one");
        assertThat(initialEmptyList.isChanged()).isTrue();
        assertThat(initialEmptyList.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyList.getAdded()).containsExactly("one");
        assertThat(initialEmptyList.getDeleted()).isEmpty();

        // Add second
        initialEmptyList.add(0, "two");
        assertThat(initialEmptyList.isChanged()).isTrue();
        assertThat(initialEmptyList.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyList.getAdded()).containsExactly("one", "two");
        assertThat(initialEmptyList.getDeleted()).isEmpty();

    }

    @Test
    public void testAddIntObjectToInitialFilledList() {

        // Add one
        initialFilledList.add(0, "four");
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList).hasSize(4);
        assertThat(initialFilledList.getAdded()).containsExactly("four");
        assertThat(initialFilledList.getDeleted()).isEmpty();

        // Add second
        initialFilledList.add(0, "five");
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList).hasSize(5);
        assertThat(initialFilledList.getAdded()).containsExactly("four", "five");
        assertThat(initialFilledList.getDeleted()).isEmpty();

    }

    @Test
    public void testAddAllCollectionToInitialEmptyList() {

        final List<String> toAdd = new ArrayList<>();
        toAdd.add("one");
        toAdd.add("two");
        toAdd.add("three");

        initialEmptyList.addAll(toAdd);
        assertThat(initialEmptyList.isChanged()).isTrue();
        assertThat(initialEmptyList.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyList.getAdded()).containsExactly("one", "two", "three");
        assertThat(initialEmptyList.getDeleted()).isEmpty();

    }

    @Test
    public void testAddAllCollectionToInitialFilledList() {

        final List<String> toAdd = new ArrayList<>();
        toAdd.add("four");
        toAdd.add("five");
        toAdd.add("six");

        initialFilledList.addAll(toAdd);
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList).hasSize(6);
        assertThat(initialFilledList.getAdded()).containsExactly("four", "five", "six");
        assertThat(initialFilledList.getDeleted()).isEmpty();

    }

    @Test
    public void testAddAllCollectionWithRemovedElements() {

        final List<String> toAdd = new ArrayList<>();
        toAdd.add("one");
        toAdd.add("two");
        toAdd.add("three");

        initialFilledList.clear();
        initialFilledList.addAll(toAdd);
        assertThat(initialFilledList.isChanged()).isFalse();
        assertThat(initialFilledList.hasChangedSinceTagging()).isFalse();
        assertThat(initialFilledList).hasSize(3);
        assertThat(initialFilledList.getAdded()).isEmpty();
        assertThat(initialFilledList.getDeleted()).isEmpty();

    }

    @Test
    public void testAddAllIntCollectionToInitialEmptyList() {

        final List<String> toAdd1 = new ArrayList<>();
        toAdd1.add("one");
        toAdd1.add("two");
        toAdd1.add("three");

        final List<String> toAdd2 = new ArrayList<>();
        toAdd2.add("four");
        toAdd2.add("five");
        toAdd2.add("six");

        initialEmptyList.addAll(0, toAdd1);
        initialEmptyList.addAll(0, toAdd2);
        assertThat(initialEmptyList.isChanged()).isTrue();
        assertThat(initialEmptyList.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyList.getAdded()).containsExactly("one", "two", "three", "four", "five", "six");
        assertThat(initialEmptyList.getDeleted()).isEmpty();

    }

    @Test
    public void testAddAllIntCollectionToInitialFilledList() {

        final List<String> toAdd = new ArrayList<>();
        toAdd.add("four");
        toAdd.add("five");
        toAdd.add("six");

        initialFilledList.addAll(1, toAdd);
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList).hasSize(6);
        assertThat(initialFilledList.getAdded()).containsExactly("four", "five", "six");
        assertThat(initialFilledList.getDeleted()).isEmpty();

    }

    @Test
    public void testAddAllIntCollectionWithRemoved() {

        final ChangeTrackingUniqueList<String> filledList = new ChangeTrackingUniqueList<>(toList("one",
                "two", "three", "four"));
        filledList.remove("two");
        filledList.remove("three");

        final List<String> toAdd = new ArrayList<>();
        toAdd.add("two");
        toAdd.add("three");

        filledList.addAll(1, toAdd);
        assertThat(filledList.isChanged()).isFalse();
        assertThat(filledList.hasChangedSinceTagging()).isFalse();
        assertThat(filledList).hasSize(4);
        assertThat(new ArrayList<String>(filledList)).containsExactly("one", "two", "three", "four");
        assertThat(filledList.getAdded()).isEmpty();
        assertThat(filledList.getDeleted()).isEmpty();

    }

    @Test
    public void testClearInitialEmptyList() {

        // Add some new entries
        initialEmptyList.add("four");
        initialEmptyList.add("five");
        initialEmptyList.add("six");

        // Check the result
        assertThat(initialEmptyList.isChanged());
        assertThat(initialEmptyList.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyList.getAdded()).containsExactly("four", "five", "six");
        assertThat(initialEmptyList.getDeleted()).isEmpty();

        // Clear the list and check the result
        initialEmptyList.clear();
        assertThat(initialEmptyList.isChanged()).isFalse();
        assertThat(initialEmptyList.hasChangedSinceTagging()).isFalse();
        assertThat(initialEmptyList).isEmpty();
        assertThat(initialEmptyList.getAdded()).isEmpty();
        assertThat(initialEmptyList.getDeleted()).isEmpty();

    }

    @Test
    public void testClearInitialFilledList() {

        // Add some new entries
        initialFilledList.add("four");
        initialFilledList.add("five");
        initialFilledList.add("six");

        // Check the result
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList.getAdded()).containsExactly("four", "five", "six");
        assertThat(initialFilledList.getDeleted()).isEmpty();

        // Clear the list and check the result
        initialFilledList.clear();
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList).isEmpty();
        assertThat(initialFilledList.getAdded()).isEmpty();
        assertThat(initialFilledList.getDeleted()).containsExactly("one", "two", "three");

    }

    @Test
    public void testRemoveObjectFromInitialEmptyList() {

        // Add some elements
        initialEmptyList.add("one");
        initialEmptyList.add("two");
        initialEmptyList.add("three");

        // Check preconditions
        assertThat(initialEmptyList.isChanged()).isTrue();
        assertThat(initialEmptyList.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyList).hasSize(3);
        assertThat(initialEmptyList.getAdded()).containsExactly("one", "two", "three");
        assertThat(initialEmptyList.getDeleted()).isEmpty();

        // Remove the entries one by one and check the result
        initialEmptyList.remove("one");
        assertThat(initialEmptyList.isChanged()).isTrue();
        assertThat(initialEmptyList.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyList).hasSize(2);
        assertThat(initialEmptyList.getAdded()).containsExactly("two", "three");
        assertThat(initialEmptyList.getDeleted()).isEmpty();

        initialEmptyList.remove("two");
        assertThat(initialEmptyList.isChanged()).isTrue();
        assertThat(initialEmptyList.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyList).hasSize(1);
        assertThat(initialEmptyList.getAdded()).containsExactly("three");
        assertThat(initialEmptyList.getDeleted()).isEmpty();

        initialEmptyList.remove("three");
        assertThat(initialEmptyList.isChanged()).isFalse();
        assertThat(initialEmptyList.hasChangedSinceTagging()).isFalse();
        assertThat(initialEmptyList).isEmpty();
        assertThat(initialEmptyList.getAdded()).isEmpty();
        assertThat(initialEmptyList.getDeleted()).isEmpty();

    }

    @Test
    public void testRemoveObjectFromInitialFilledList() {

        // Check preconditions
        assertThat(initialFilledList.isChanged()).isFalse();
        assertThat(initialFilledList.hasChangedSinceTagging()).isFalse();
        assertThat(initialFilledList).hasSize(3);
        assertThat(initialFilledList.getAdded()).isEmpty();
        assertThat(initialFilledList.getDeleted()).isEmpty();

        // Remove the entries
        initialFilledList.remove("one");
        initialFilledList.remove("two");
        initialFilledList.remove("three");

        // Check result
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList).isEmpty();
        assertThat(initialFilledList.getAdded()).isEmpty();
        assertThat(initialFilledList.getDeleted()).hasSize(3);

        // Add again
        initialFilledList.add("one");
        initialFilledList.add("two");
        initialFilledList.add("three");

        // Check result
        assertThat(initialFilledList.isChanged()).isFalse();
        assertThat(initialFilledList.hasChangedSinceTagging()).isFalse();
        assertThat(initialFilledList).hasSize(3);
        assertThat(initialFilledList.getAdded()).isEmpty();
        assertThat(initialFilledList.getDeleted()).isEmpty();

    }

    @Test
    public void testRemoveIntFromInitialEmptyList() {

        // Add some elements
        initialEmptyList.add("one");
        initialEmptyList.add("two");
        initialEmptyList.add("three");

        // Check preconditions
        assertThat(initialEmptyList.isChanged()).isTrue();
        assertThat(initialEmptyList.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyList).hasSize(3);
        assertThat(initialEmptyList.getAdded()).hasSize(3);
        assertThat(initialEmptyList.getDeleted()).isEmpty();

        // Remove the entries one by one and check the result
        initialEmptyList.remove(0);
        assertThat(initialEmptyList.isChanged()).isTrue();
        assertThat(initialEmptyList.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyList).hasSize(2);
        assertThat(initialEmptyList.getAdded()).hasSize(2);
        assertThat(initialEmptyList.getDeleted()).isEmpty();

        initialEmptyList.remove(0);
        assertThat(initialEmptyList.isChanged()).isTrue();
        assertThat(initialEmptyList.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyList).hasSize(1);
        assertThat(initialEmptyList.getAdded()).hasSize(1);
        assertThat(initialEmptyList.getDeleted()).isEmpty();

        initialEmptyList.remove(0);
        assertThat(initialEmptyList.isChanged()).isFalse();
        assertThat(initialEmptyList.hasChangedSinceTagging()).isFalse();
        assertThat(initialEmptyList).isEmpty();
        assertThat(initialEmptyList.getAdded()).isEmpty();
        assertThat(initialEmptyList.getDeleted()).isEmpty();

    }

    private List<String> toList(final String... elements) {
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < elements.length; i++) {
            list.add(elements[i]);
        }
        return list;
    }

    @Test
    public void testRemoveIntFromInitialFilledList() {

        // Add some elements
        initialFilledList.add("four");
        initialFilledList.add("five");
        initialFilledList.add("six");

        // Check preconditions
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList).hasSize(6);
        assertThat(initialFilledList.getAdded()).containsExactly("four", "five", "six");
        assertThat(initialFilledList.getDeleted()).isEmpty();

        // Remove the entries one by one and check the result
        initialFilledList.remove(0);
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList).hasSize(5);
        assertThat(initialFilledList.getAdded()).containsExactly("four", "five", "six");
        assertThat(initialFilledList.getDeleted()).containsExactly("one");

        initialFilledList.remove(0);
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList).hasSize(4);
        assertThat(initialFilledList.getAdded()).containsExactly("four", "five", "six");
        assertThat(initialFilledList.getDeleted()).containsExactly("one", "two");

        initialFilledList.remove(0);
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList).hasSize(3);
        assertThat(initialFilledList.getAdded()).containsExactly("four", "five", "six");
        assertThat(initialFilledList.getDeleted()).containsExactly("one", "two", "three");

        initialFilledList.remove(0);
        assertThat(initialFilledList.isChanged()).isTrue();
        ;
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList).hasSize(2);
        assertThat(initialFilledList.getAdded()).containsExactly("five", "six");
        assertThat(initialFilledList.getDeleted()).containsExactly("one", "two", "three");

        initialFilledList.remove(0);
        assertThat(initialFilledList.isChanged()).isTrue();
        ;
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList).hasSize(1);
        assertThat(initialFilledList.getAdded()).containsExactly("six");
        assertThat(initialFilledList.getDeleted()).containsExactly("one", "two", "three");

        initialFilledList.remove(0);
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList).isEmpty();
        assertThat(initialFilledList.getAdded()).isEmpty();
        assertThat(initialFilledList.getDeleted()).containsExactly("one", "two", "three");

    }

    @Test
    public void testRemoveAllFromInitialEmptyList() {

        initialEmptyList.add("one");
        initialEmptyList.add("two");
        initialEmptyList.add("three");

        // Check preconditions
        assertThat(initialEmptyList.isChanged()).isTrue();
        assertThat(initialEmptyList.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyList).hasSize(3);
        assertThat(initialEmptyList.getAdded()).containsExactly("one", "two", "three");
        assertThat(initialEmptyList.getDeleted()).isEmpty();

        // Remove two elements of the collection
        initialEmptyList.removeAll(toList("one", "three"));

        // Check result
        assertThat(initialEmptyList.isChanged()).isTrue();
        assertThat(initialEmptyList.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyList).hasSize(1);
        assertThat(initialEmptyList.getAdded()).containsExactly("two");
        assertThat(initialEmptyList.getDeleted()).isEmpty();

        // Remove last element of the collection
        initialEmptyList.removeAll(toList("two"));

        // Check result
        assertThat(initialEmptyList.isChanged()).isFalse();
        assertThat(initialEmptyList.hasChangedSinceTagging()).isFalse();
        assertThat(initialEmptyList).isEmpty();
        assertThat(initialEmptyList.getAdded()).isEmpty();
        assertThat(initialEmptyList.getDeleted()).isEmpty();

    }

    @Test
    public void testRemoveAllFromInitialFilledList() {

        initialFilledList.add("four");

        // Check preconditions
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList).hasSize(4);
        assertThat(initialFilledList.getAdded()).containsExactly("four");
        assertThat(initialFilledList.getDeleted()).isEmpty();

        // Remove two elements of the collection
        initialFilledList.removeAll(toList("one", "four"));

        // Check result
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList).hasSize(2);
        assertThat(initialFilledList.getAdded()).isEmpty();
        assertThat(initialFilledList.getDeleted()).containsExactly("one");

        // Remove last element of the collection
        initialFilledList.removeAll(toList("two"));

        // Check result
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList).hasSize(1);
        assertThat(initialFilledList.getAdded()).isEmpty();
        assertThat(initialFilledList.getDeleted()).containsExactly("one", "two");

    }

    @Test
    public void testRetainAllFromInitialEmptyList() {

        // Add some entries
        initialEmptyList.add("one");
        initialEmptyList.add("two");
        initialEmptyList.add("three");

        // Check precondition
        assertThat(initialEmptyList.isChanged()).isTrue();
        assertThat(initialEmptyList.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyList).hasSize(3);
        assertThat(initialEmptyList.getAdded()).containsExactly("one", "two", "three");
        assertThat(initialEmptyList.getDeleted()).isEmpty();

        // Run method
        initialEmptyList.retainAll(toList("one"));

        // Check result
        assertThat(initialEmptyList.isChanged()).isTrue();
        assertThat(initialEmptyList.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyList).hasSize(1);
        assertThat(initialEmptyList.getAdded()).containsExactly("one");
        assertThat(initialEmptyList.getDeleted()).isEmpty();

    }

    @Test
    public void testRetainAllFromInitialFilledList() {

        // Add some more entries
        initialFilledList.add("four");
        initialFilledList.add("five");
        initialFilledList.add("six");

        // Check precondition
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList).hasSize(6);
        assertThat(initialFilledList.getAdded()).containsExactly("four", "five", "six");
        assertThat(initialFilledList.getDeleted()).isEmpty();

        // Run method
        initialFilledList.retainAll(toList("one", "two", "four"));

        // Check result
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList).hasSize(3);
        assertThat(initialFilledList.getAdded()).containsExactly("four");
        assertThat(initialFilledList.getDeleted()).containsExactly("three");
    }

    @Test
    public void testSetInitialEmptyList() {

        initialEmptyList.add("one");
        initialEmptyList.add("two");
        initialEmptyList.add("three");

        // Check precondition
        assertThat(initialEmptyList.isChanged()).isTrue();
        assertThat(initialEmptyList.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyList).hasSize(3);
        assertThat(initialEmptyList.getAdded()).containsExactly("one", "two", "three");
        assertThat(initialEmptyList.getDeleted()).isEmpty();

        // Replace element
        initialEmptyList.set(0, "zero");
        assertThat(initialEmptyList.isChanged()).isTrue();
        assertThat(initialEmptyList.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyList).hasSize(3);
        assertThat(initialEmptyList.getAdded()).containsExactly("two", "three", "zero");
        assertThat(initialEmptyList.getDeleted()).isEmpty();

    }

    @Test
    public void testSetInitialFilledList() {

        initialFilledList.add("four");
        initialFilledList.add("five");
        initialFilledList.add("six");

        // Check precondition
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList).hasSize(6);
        assertThat(initialFilledList.getAdded()).containsExactly("four", "five", "six");
        assertThat(initialFilledList.getDeleted()).isEmpty();

        // Replace some elements
        initialFilledList.set(0, "1");
        initialFilledList.set(2, "3");
        initialFilledList.set(4, "5");

        // Check result
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList).hasSize(6);
        assertThat(new ArrayList<String>(initialFilledList)).containsExactly("1", "two", "3", "four", "5",
                "six");
        assertThat(initialFilledList.getAdded()).containsExactly("four", "six", "1", "3", "5");
        assertThat(initialFilledList.getDeleted()).containsExactly("one", "three");

    }

    @Test
    public final void testRevert() {

        // Add and remove entries
        initialFilledList.add("four");
        initialFilledList.remove("two");

        // Check precondition
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList).hasSize(3);
        assertThat(initialFilledList.getAdded()).containsExactly("four");
        assertThat(initialFilledList.getDeleted()).containsExactly("two");

        // Roll back the changes
        initialFilledList.revert();

        // Check result
        assertThat(initialFilledList.isChanged()).isFalse();
        assertThat(initialFilledList.hasChangedSinceTagging()).isFalse();
        assertThat(initialFilledList).hasSize(3);
        assertThat(new ArrayList<String>(initialFilledList)).containsExactly("one", "three", "two");
        assertThat(initialFilledList.getAdded()).isEmpty();
        assertThat(initialFilledList.getDeleted()).isEmpty();

    }

    @Test
    public void testTag() {

        // List is always in tag mode after construction
        initialFilledList.untag();

        // Add/remove entries (without tracking the change)
        initialFilledList.add("four");
        initialFilledList.remove("two");

        // Check precondition
        assertThat(initialFilledList.isChanged()).isFalse();
        assertThat(initialFilledList.hasChangedSinceTagging()).isFalse();
        assertThat(initialFilledList).hasSize(3);
        assertThat(initialFilledList.getAdded()).isEmpty();
        assertThat(initialFilledList.getDeleted()).isEmpty();

        // Tag the list and add/remove entries
        initialFilledList.tag();
        initialFilledList.add("two");
        initialFilledList.remove("four");

        // Check the result
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList).hasSize(3);
        assertThat(initialFilledList.getAdded()).containsExactly("two");
        assertThat(initialFilledList.getDeleted()).containsExactly("four");

    }

    @Test
    public void testUntag() {

        // Add and remove entries
        initialFilledList.add("four");
        initialFilledList.remove("two");

        // Check precondition
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList).hasSize(3);
        assertThat(initialFilledList.getAdded()).containsExactly("four");
        assertThat(initialFilledList.getDeleted()).containsExactly("two");

        // Forget the changes
        initialFilledList.untag();

        // Check result
        assertThat(initialFilledList.isChanged()).isFalse();
        assertThat(initialFilledList.hasChangedSinceTagging()).isFalse();
        assertThat(initialFilledList).hasSize(3);
        assertThat(new ArrayList<String>(initialFilledList)).containsExactly("one", "three", "four");
        assertThat(initialFilledList.getAdded()).isEmpty();
        assertThat(initialFilledList.getDeleted()).isEmpty();

    }

    @Test
    public void testIsTagged() {

        // Should be in tag mode after construction
        assertThat(initialFilledList.isTagged()).isTrue();
        initialFilledList.untag();
        assertThat(initialFilledList.isTagged()).isFalse();
        initialFilledList.tag();
        assertThat(initialFilledList.isTagged()).isTrue();

    }

    @Test
    public void testRevertToTag() {

        // Add and remove entries
        initialFilledList.add("four");
        initialFilledList.remove("two");

        // Check precondition
        assertThat(initialFilledList.isChanged()).isTrue();
        assertThat(initialFilledList.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledList).hasSize(3);
        assertThat(initialFilledList.getAdded()).containsExactly("four");
        assertThat(initialFilledList.getDeleted()).containsExactly("two");

        // Roll back the changes
        initialFilledList.revertToTag();

        // Check result
        assertThat(initialFilledList.isChanged()).isFalse();
        assertThat(initialFilledList.hasChangedSinceTagging()).isFalse();
        assertThat(initialFilledList).hasSize(3);
        assertThat(new ArrayList<String>(initialFilledList)).containsExactly("one", "three", "two");
        assertThat(initialFilledList.getAdded()).isEmpty();
        assertThat(initialFilledList.getDeleted()).isEmpty();

    }

}
// CHECKSTYLE:ON
