/**
 * Copyright (C) 2009 Future Invent Informationsmanagement GmbH. All rights
 * reserved. <http://www.fuin.org/>
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
 * along with this library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fuin.utils4j;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

//CHECKSTYLE:OFF
public class ChangeTrackingUniqueListTest {

    private ChangeTrackingUniqueList initialEmptyList;

    private ChangeTrackingUniqueList initialFilledList;

    /**
     * @testng.before-method
     */
    public final void beforeMethod() {
        initialEmptyList = new ChangeTrackingUniqueList(new ArrayList());
        initialFilledList = new ChangeTrackingUniqueList(toList(new String[] { "one", "two",
                "three" }));

    }

    /**
     * @testng.after-method
     */
    public final void afterMethod() {
        initialFilledList = null;
        initialEmptyList = null;
    }

    /**
     * @testng.test
     */
    public void testAddObjectToInitialEmptyList() {

        // Add one
        initialEmptyList.add("one");
        Assert.assertTrue(initialEmptyList.isChanged());
        Assert.assertTrue(initialEmptyList.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyList.getAdded(), toList(new String[] { "one" }));
        Assert.assertEquals(initialEmptyList.getDeleted().size(), 0);

        // Add second
        initialEmptyList.add("two");
        Assert.assertTrue(initialEmptyList.isChanged());
        Assert.assertTrue(initialEmptyList.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyList.getAdded(), toList(new String[] { "one", "two" }));
        Assert.assertEquals(initialEmptyList.getDeleted().size(), 0);

    }

    /**
     * @testng.test
     */
    public void testAddObjectToInitialFilledList() {

        // Add one
        initialFilledList.add("four");
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.getAdded(), toList(new String[] { "four" }));
        Assert.assertEquals(initialFilledList.getDeleted().size(), 0);

        // Add second
        initialFilledList.add("five");
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.getAdded(), toList(new String[] { "four", "five" }));
        Assert.assertEquals(initialFilledList.getDeleted().size(), 0);

    }

    /**
     * @testng.test
     */
    public void testAddIntObjectToInitialEmptyList() {

        // Add one
        initialEmptyList.add(0, "one");
        Assert.assertTrue(initialEmptyList.isChanged());
        Assert.assertTrue(initialEmptyList.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyList.getAdded(), toList(new String[] { "one" }));
        Assert.assertEquals(initialEmptyList.getDeleted().size(), 0);

        // Add second
        initialEmptyList.add(0, "two");
        Assert.assertTrue(initialEmptyList.isChanged());
        Assert.assertTrue(initialEmptyList.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyList.getAdded(), toList(new String[] { "one", "two" }));
        Assert.assertEquals(initialEmptyList.getDeleted().size(), 0);

    }

    /**
     * @testng.test
     */
    public void testAddIntObjectToInitialFilledList() {

        // Add one
        initialFilledList.add(0, "four");
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 4);
        Assert.assertEquals(initialFilledList.getAdded(), toList(new String[] { "four" }));
        Assert.assertEquals(initialFilledList.getDeleted().size(), 0);

        // Add second
        initialFilledList.add(0, "five");
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 5);
        Assert.assertEquals(initialFilledList.getAdded(), toList(new String[] { "four", "five" }));
        Assert.assertEquals(initialFilledList.getDeleted().size(), 0);

    }

    /**
     * @testng.test
     */
    public void testAddAllCollectionToInitialEmptyList() {

        final List toAdd = new ArrayList();
        toAdd.add("one");
        toAdd.add("two");
        toAdd.add("three");

        initialEmptyList.addAll(toAdd);
        Assert.assertTrue(initialEmptyList.isChanged());
        Assert.assertTrue(initialEmptyList.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyList.getAdded(), toList(new String[] { "one", "two",
                "three" }));
        Assert.assertEquals(initialEmptyList.getDeleted().size(), 0);

    }

    /**
     * @testng.test
     */
    public void testAddAllCollectionToInitialFilledList() {

        final List toAdd = new ArrayList();
        toAdd.add("four");
        toAdd.add("five");
        toAdd.add("six");

        initialFilledList.addAll(toAdd);
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 6);
        Assert.assertEquals(initialFilledList.getAdded(), toList(new String[] { "four", "five",
                "six" }));
        Assert.assertEquals(initialFilledList.getDeleted().size(), 0);

    }

    /**
     * @testng.test
     */
    public void testAddAllCollectionWithRemovedElements() {

        final List toAdd = new ArrayList();
        toAdd.add("one");
        toAdd.add("two");
        toAdd.add("three");

        initialFilledList.clear();
        initialFilledList.addAll(toAdd);
        Assert.assertFalse(initialFilledList.isChanged());
        Assert.assertFalse(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 3);
        Assert.assertEquals(initialFilledList.getAdded().size(), 0);
        Assert.assertEquals(initialFilledList.getDeleted().size(), 0);

    }

    /**
     * @testng.test
     */
    public void testAddAllIntCollectionToInitialEmptyList() {

        final List toAdd1 = new ArrayList();
        toAdd1.add("one");
        toAdd1.add("two");
        toAdd1.add("three");

        final List toAdd2 = new ArrayList();
        toAdd2.add("four");
        toAdd2.add("five");
        toAdd2.add("six");

        initialEmptyList.addAll(0, toAdd1);
        initialEmptyList.addAll(0, toAdd2);
        Assert.assertTrue(initialEmptyList.isChanged());
        Assert.assertTrue(initialEmptyList.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyList.getAdded(), toList(new String[] { "one", "two",
                "three", "four", "five", "six" }));
        Assert.assertEquals(initialEmptyList.getDeleted().size(), 0);

    }

    /**
     * @testng.test
     */
    public void testAddAllIntCollectionToInitialFilledList() {

        final List toAdd = new ArrayList();
        toAdd.add("four");
        toAdd.add("five");
        toAdd.add("six");

        initialFilledList.addAll(1, toAdd);
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 6);
        Assert.assertEquals(initialFilledList.getAdded(), toList(new String[] { "four", "five",
                "six" }));
        Assert.assertEquals(initialFilledList.getDeleted().size(), 0);

    }

    /**
     * @testng.test
     */
    public void testAddAllIntCollectionWithRemoved() {

        final ChangeTrackingUniqueList filledList = new ChangeTrackingUniqueList(
                toList(new String[] { "one", "two", "three", "four" }));
        filledList.remove("two");
        filledList.remove("three");

        final List toAdd = new ArrayList();
        toAdd.add("two");
        toAdd.add("three");

        filledList.addAll(1, toAdd);
        Assert.assertFalse(filledList.isChanged());
        Assert.assertFalse(filledList.hasChangedSinceTagging());
        Assert.assertEquals(filledList.size(), 4);
        Assert.assertEquals(filledList, toList(new String[] { "one", "two", "three", "four" }));
        Assert.assertEquals(filledList.getAdded().size(), 0);
        Assert.assertEquals(filledList.getDeleted().size(), 0);

    }

    /**
     * @testng.test
     */
    public void testClearInitialEmptyList() {

        // Add some new entries
        initialEmptyList.add("four");
        initialEmptyList.add("five");
        initialEmptyList.add("six");

        // Check the result
        Assert.assertTrue(initialEmptyList.isChanged());
        Assert.assertTrue(initialEmptyList.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyList.getAdded(), toList(new String[] { "four", "five",
                "six" }));
        Assert.assertEquals(initialEmptyList.getDeleted().size(), 0);

        // Clear the list and check the result
        initialEmptyList.clear();
        Assert.assertFalse(initialEmptyList.isChanged());
        Assert.assertFalse(initialEmptyList.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyList.size(), 0);
        Assert.assertEquals(initialEmptyList.getAdded().size(), 0);
        Assert.assertEquals(initialEmptyList.getDeleted().size(), 0);

    }

    /**
     * @testng.test
     */
    public void testClearInitialFilledList() {

        // Add some new entries
        initialFilledList.add("four");
        initialFilledList.add("five");
        initialFilledList.add("six");

        // Check the result
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.getAdded(), toList(new String[] { "four", "five",
                "six" }));
        Assert.assertEquals(initialFilledList.getDeleted().size(), 0);

        // Clear the list and check the result
        initialFilledList.clear();
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 0);
        Assert.assertEquals(initialFilledList.getAdded().size(), 0);
        Assert.assertEquals(initialFilledList.getDeleted(), toList(new String[] { "one", "two",
                "three" }));

    }

    /**
     * @testng.test
     */
    public void testRemoveObjectFromInitialEmptyList() {

        // Add some elements
        initialEmptyList.add("one");
        initialEmptyList.add("two");
        initialEmptyList.add("three");

        // Check preconditions
        Assert.assertTrue(initialEmptyList.isChanged());
        Assert.assertTrue(initialEmptyList.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyList.size(), 3);
        Assert.assertEquals(initialEmptyList.getAdded(), toList(new String[] { "one", "two",
                "three" }));
        Assert.assertEquals(initialEmptyList.getDeleted().size(), 0);

        // Remove the entries one by one and check the result
        initialEmptyList.remove("one");
        Assert.assertTrue(initialEmptyList.isChanged());
        Assert.assertTrue(initialEmptyList.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyList.size(), 2);
        Assert.assertEquals(initialEmptyList.getAdded(), toList(new String[] { "two", "three" }));
        Assert.assertEquals(initialEmptyList.getDeleted().size(), 0);

        initialEmptyList.remove("two");
        Assert.assertTrue(initialEmptyList.isChanged());
        Assert.assertTrue(initialEmptyList.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyList.size(), 1);
        Assert.assertEquals(initialEmptyList.getAdded(), toList(new String[] { "three" }));
        Assert.assertEquals(initialEmptyList.getDeleted().size(), 0);

        initialEmptyList.remove("three");
        Assert.assertFalse(initialEmptyList.isChanged());
        Assert.assertFalse(initialEmptyList.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyList.size(), 0);
        Assert.assertEquals(initialEmptyList.getAdded().size(), 0);
        Assert.assertEquals(initialEmptyList.getDeleted().size(), 0);

    }

    /**
     * @testng.test
     */
    public void testRemoveObjectFromInitialFilledList() {

        // Check preconditions
        Assert.assertFalse(initialFilledList.isChanged());
        Assert.assertFalse(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 3);
        Assert.assertEquals(initialFilledList.getAdded().size(), 0);
        Assert.assertEquals(initialFilledList.getDeleted().size(), 0);

        // Remove the entries
        initialFilledList.remove("one");
        initialFilledList.remove("two");
        initialFilledList.remove("three");

        // Check result
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 0);
        Assert.assertEquals(initialFilledList.getAdded().size(), 0);
        Assert.assertEquals(initialFilledList.getDeleted().size(), 3);

        // Add again
        initialFilledList.add("one");
        initialFilledList.add("two");
        initialFilledList.add("three");

        // Check result
        Assert.assertFalse(initialFilledList.isChanged());
        Assert.assertFalse(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 3);
        Assert.assertEquals(initialFilledList.getAdded().size(), 0);
        Assert.assertEquals(initialFilledList.getDeleted().size(), 0);

    }

    /**
     * @testng.test
     */
    public void testRemoveIntFromInitialEmptyList() {

        // Add some elements
        initialEmptyList.add("one");
        initialEmptyList.add("two");
        initialEmptyList.add("three");

        // Check preconditions
        Assert.assertTrue(initialEmptyList.isChanged());
        Assert.assertTrue(initialEmptyList.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyList.size(), 3);
        Assert.assertEquals(initialEmptyList.getAdded().size(), 3);
        Assert.assertEquals(initialEmptyList.getDeleted().size(), 0);

        // Remove the entries one by one and check the result
        initialEmptyList.remove(0);
        Assert.assertTrue(initialEmptyList.isChanged());
        Assert.assertTrue(initialEmptyList.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyList.size(), 2);
        Assert.assertEquals(initialEmptyList.getAdded().size(), 2);
        Assert.assertEquals(initialEmptyList.getDeleted().size(), 0);

        initialEmptyList.remove(0);
        Assert.assertTrue(initialEmptyList.isChanged());
        Assert.assertTrue(initialEmptyList.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyList.size(), 1);
        Assert.assertEquals(initialEmptyList.getAdded().size(), 1);
        Assert.assertEquals(initialEmptyList.getDeleted().size(), 0);

        initialEmptyList.remove(0);
        Assert.assertFalse(initialEmptyList.isChanged());
        Assert.assertFalse(initialEmptyList.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyList.size(), 0);
        Assert.assertEquals(initialEmptyList.getAdded().size(), 0);
        Assert.assertEquals(initialEmptyList.getDeleted().size(), 0);

    }

    private List toList(final String[] elements) {
        final List list = new ArrayList();
        for (int i = 0; i < elements.length; i++) {
            list.add(elements[i]);
        }
        return list;
    }

    /**
     * @testng.test
     */
    public void testRemoveIntFromInitialFilledList() {

        // Add some elements
        initialFilledList.add("four");
        initialFilledList.add("five");
        initialFilledList.add("six");

        // Check preconditions
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 6);
        Assert.assertEquals(initialFilledList.getAdded(), toList(new String[] { "four", "five",
                "six" }));
        Assert.assertEquals(initialFilledList.getDeleted().size(), 0);

        // Remove the entries one by one and check the result
        initialFilledList.remove(0);
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 5);
        Assert.assertEquals(initialFilledList.getAdded(), toList(new String[] { "four", "five",
                "six" }));
        Assert.assertEquals(initialFilledList.getDeleted(), toList(new String[] { "one" }));

        initialFilledList.remove(0);
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 4);
        Assert.assertEquals(initialFilledList.getAdded(), toList(new String[] { "four", "five",
                "six" }));
        Assert.assertEquals(initialFilledList.getDeleted(), toList(new String[] { "one", "two" }));

        initialFilledList.remove(0);
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 3);
        Assert.assertEquals(initialFilledList.getAdded(), toList(new String[] { "four", "five",
                "six" }));
        Assert.assertEquals(initialFilledList.getDeleted(), toList(new String[] { "one", "two",
                "three" }));

        initialFilledList.remove(0);
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 2);
        Assert.assertEquals(initialFilledList.getAdded(), toList(new String[] { "five", "six" }));
        Assert.assertEquals(initialFilledList.getDeleted(), toList(new String[] { "one", "two",
                "three" }));

        initialFilledList.remove(0);
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 1);
        Assert.assertEquals(initialFilledList.getAdded(), toList(new String[] { "six" }));
        Assert.assertEquals(initialFilledList.getDeleted(), toList(new String[] { "one", "two",
                "three" }));

        initialFilledList.remove(0);
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 0);
        Assert.assertEquals(initialFilledList.getAdded().size(), 0);
        Assert.assertEquals(initialFilledList.getDeleted(), toList(new String[] { "one", "two",
                "three" }));

    }

    /**
     * @testng.test
     */
    public void testRemoveAllFromInitialEmptyList() {

        initialEmptyList.add("one");
        initialEmptyList.add("two");
        initialEmptyList.add("three");

        // Check preconditions
        Assert.assertTrue(initialEmptyList.isChanged());
        Assert.assertTrue(initialEmptyList.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyList.size(), 3);
        Assert.assertEquals(initialEmptyList.getAdded(), toList(new String[] { "one", "two",
                "three" }));
        Assert.assertEquals(initialEmptyList.getDeleted().size(), 0);

        // Remove two elements of the collection
        initialEmptyList.removeAll(toList(new String[] { "one", "three" }));

        // Check result
        Assert.assertTrue(initialEmptyList.isChanged());
        Assert.assertTrue(initialEmptyList.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyList.size(), 1);
        Assert.assertEquals(initialEmptyList.getAdded(), toList(new String[] { "two" }));
        Assert.assertEquals(initialEmptyList.getDeleted().size(), 0);

        // Remove last element of the collection
        initialEmptyList.removeAll(toList(new String[] { "two" }));

        // Check result
        Assert.assertFalse(initialEmptyList.isChanged());
        Assert.assertFalse(initialEmptyList.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyList.size(), 0);
        Assert.assertEquals(initialEmptyList.getAdded().size(), 0);
        Assert.assertEquals(initialEmptyList.getDeleted().size(), 0);

    }

    /**
     * @testng.test
     */
    public void testRemoveAllFromInitialFilledList() {

        initialFilledList.add("four");

        // Check preconditions
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 4);
        Assert.assertEquals(initialFilledList.getAdded(), toList(new String[] { "four" }));
        Assert.assertEquals(initialFilledList.getDeleted().size(), 0);

        // Remove two elements of the collection
        initialFilledList.removeAll(toList(new String[] { "one", "four" }));

        // Check result
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 2);
        Assert.assertEquals(initialFilledList.getAdded().size(), 0);
        Assert.assertEquals(initialFilledList.getDeleted(), toList(new String[] { "one" }));

        // Remove last element of the collection
        initialFilledList.removeAll(toList(new String[] { "two" }));

        // Check result
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 1);
        Assert.assertEquals(initialFilledList.getAdded().size(), 0);
        Assert.assertEquals(initialFilledList.getDeleted(), toList(new String[] { "one", "two" }));

    }

    /**
     * @testng.test
     */
    public void testRetainAllFromInitialEmptyList() {

        // Add some entries
        initialEmptyList.add("one");
        initialEmptyList.add("two");
        initialEmptyList.add("three");

        // Check precondition
        Assert.assertTrue(initialEmptyList.isChanged());
        Assert.assertTrue(initialEmptyList.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyList.size(), 3);
        Assert.assertEquals(initialEmptyList.getAdded(), toList(new String[] { "one", "two",
                "three" }));
        Assert.assertEquals(initialEmptyList.getDeleted().size(), 0);

        // Run method
        initialEmptyList.retainAll(toList(new String[] { "one" }));

        // Check result
        Assert.assertTrue(initialEmptyList.isChanged());
        Assert.assertTrue(initialEmptyList.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyList.size(), 1);
        Assert.assertEquals(initialEmptyList.getAdded(), toList(new String[] { "one" }));
        Assert.assertEquals(initialEmptyList.getDeleted().size(), 0);

    }

    /**
     * @testng.test
     */
    public void testRetainAllFromInitialFilledList() {

        // Add some more entries
        initialFilledList.add("four");
        initialFilledList.add("five");
        initialFilledList.add("six");

        // Check precondition
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 6);
        Assert.assertEquals(initialFilledList.getAdded(), toList(new String[] { "four", "five",
                "six" }));
        Assert.assertEquals(initialFilledList.getDeleted().size(), 0);

        // Run method
        initialFilledList.retainAll(toList(new String[] { "one", "two", "four" }));

        // Check result
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 3);
        Assert.assertEquals(initialFilledList.getAdded(), toList(new String[] { "four" }));
        Assert.assertEquals(initialFilledList.getDeleted(), toList(new String[] { "three" }));
    }

    /**
     * @testng.test
     */
    public void testSetInitialEmptyList() {

        initialEmptyList.add("one");
        initialEmptyList.add("two");
        initialEmptyList.add("three");

        // Check precondition
        Assert.assertTrue(initialEmptyList.isChanged());
        Assert.assertTrue(initialEmptyList.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyList.size(), 3);
        Assert.assertEquals(initialEmptyList.getAdded(), toList(new String[] { "one", "two",
                "three" }));
        Assert.assertEquals(initialEmptyList.getDeleted().size(), 0);

        // Replace element
        initialEmptyList.set(0, "zero");
        Assert.assertTrue(initialEmptyList.isChanged());
        Assert.assertTrue(initialEmptyList.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyList.size(), 3);
        Assert.assertEquals(initialEmptyList.getAdded(), toList(new String[] { "two", "three",
                "zero" }));
        Assert.assertEquals(initialEmptyList.getDeleted().size(), 0);

    }

    /**
     * @testng.test
     */
    public void testSetInitialFilledList() {

        initialFilledList.add("four");
        initialFilledList.add("five");
        initialFilledList.add("six");

        // Check precondition
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 6);
        Assert.assertEquals(initialFilledList.getAdded(), toList(new String[] { "four", "five",
                "six" }));
        Assert.assertEquals(initialFilledList.getDeleted().size(), 0);

        // Replace some elements
        initialFilledList.set(0, "1");
        initialFilledList.set(2, "3");
        initialFilledList.set(4, "5");

        // Check result
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 6);
        Assert.assertEquals(initialFilledList, toList(new String[] { "1", "two", "3", "four", "5",
                "six" }));
        Assert.assertEquals(initialFilledList.getAdded(), toList(new String[] { "four", "six", "1",
                "3", "5" }));
        Assert
                .assertEquals(initialFilledList.getDeleted(),
                        toList(new String[] { "one", "three" }));

    }

    /**
     * @testng.test
     */
    public final void testRevert() {

        // Add and remove entries
        initialFilledList.add("four");
        initialFilledList.remove("two");

        // Check precondition
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 3);
        Assert.assertEquals(initialFilledList.getAdded(), toList(new String[] { "four" }));
        Assert.assertEquals(initialFilledList.getDeleted(), toList(new String[] { "two" }));

        // Roll back the changes
        initialFilledList.revert();

        // Check result
        Assert.assertFalse(initialFilledList.isChanged());
        Assert.assertFalse(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 3);
        Assert.assertEquals(initialFilledList, toList(new String[] { "one", "three", "two" }));
        Assert.assertEquals(initialFilledList.getAdded().size(), 0);
        Assert.assertEquals(initialFilledList.getDeleted().size(), 0);

    }

    /**
     * @testng.test
     */
    public void testTag() {

        // List is always in tag mode after construction
        initialFilledList.untag();

        // Add/remove entries (without tracking the change)
        initialFilledList.add("four");
        initialFilledList.remove("two");

        // Check precondition
        Assert.assertFalse(initialFilledList.isChanged());
        Assert.assertFalse(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 3);
        Assert.assertEquals(initialFilledList.getAdded().size(), 0);
        Assert.assertEquals(initialFilledList.getDeleted().size(), 0);

        // Tag the list and add/remove entries
        initialFilledList.tag();
        initialFilledList.add("two");
        initialFilledList.remove("four");

        // Check the result
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 3);
        Assert.assertEquals(initialFilledList.getAdded(), toList(new String[] { "two" }));
        Assert.assertEquals(initialFilledList.getDeleted(), toList(new String[] { "four" }));

    }

    /**
     * @testng.test
     */
    public void testUntag() {

        // Add and remove entries
        initialFilledList.add("four");
        initialFilledList.remove("two");

        // Check precondition
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 3);
        Assert.assertEquals(initialFilledList.getAdded(), toList(new String[] { "four" }));
        Assert.assertEquals(initialFilledList.getDeleted(), toList(new String[] { "two" }));

        // Forget the changes
        initialFilledList.untag();

        // Check result
        Assert.assertFalse(initialFilledList.isChanged());
        Assert.assertFalse(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 3);
        Assert.assertEquals(initialFilledList, toList(new String[] { "one", "three", "four" }));
        Assert.assertEquals(initialFilledList.getAdded().size(), 0);
        Assert.assertEquals(initialFilledList.getDeleted().size(), 0);

    }

    /**
     * @testng.test
     */
    public void testIsTagged() {

        // Should be in tag mode after construction
        Assert.assertTrue(initialFilledList.isTagged());
        initialFilledList.untag();
        Assert.assertFalse(initialFilledList.isTagged());
        initialFilledList.tag();
        Assert.assertTrue(initialFilledList.isTagged());

    }

    /**
     * @testng.test
     */
    public void testRevertToTag() {

        // Add and remove entries
        initialFilledList.add("four");
        initialFilledList.remove("two");

        // Check precondition
        Assert.assertTrue(initialFilledList.isChanged());
        Assert.assertTrue(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 3);
        Assert.assertEquals(initialFilledList.getAdded(), toList(new String[] { "four" }));
        Assert.assertEquals(initialFilledList.getDeleted(), toList(new String[] { "two" }));

        // Roll back the changes
        initialFilledList.revertToTag();

        // Check result
        Assert.assertFalse(initialFilledList.isChanged());
        Assert.assertFalse(initialFilledList.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledList.size(), 3);
        Assert.assertEquals(initialFilledList, toList(new String[] { "one", "three", "two" }));
        Assert.assertEquals(initialFilledList.getAdded().size(), 0);
        Assert.assertEquals(initialFilledList.getDeleted().size(), 0);

    }

}
// CHECKSTYLE:ON
