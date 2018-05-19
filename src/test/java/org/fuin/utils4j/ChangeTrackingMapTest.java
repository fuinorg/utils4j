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
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//CHECKSTYLE:OFF
public class ChangeTrackingMapTest {

    private ChangeTrackingMap<String, String> initialEmptyMap;

    private ChangeTrackingMap<String, String> initialFilledMap;

    private Map<String, String> toMap(final String keyValues) {
        final Map<String, String> map = new HashMap<>();
        final StringTokenizer tok = new StringTokenizer(keyValues, ",");
        while (tok.hasMoreTokens()) {
            final String entry = tok.nextToken();
            final int p = entry.indexOf('=');
            if (p == -1) {
                throw new IllegalArgumentException("Format error: '" + entry + "'!");
            }
            final String key = entry.substring(0, p);
            final String value = entry.substring(p + 1);
            map.put(key, value);
        }
        return map;
    }

    @Before
    public final void beforeMethod() {
        initialEmptyMap = new ChangeTrackingMap<String, String>(new HashMap<String, String>());
        initialFilledMap = new ChangeTrackingMap<String, String>(toMap("one=1,two=2,three=3"));
    }

    @After
    public final void afterMethod() {
        initialFilledMap = null;
        initialEmptyMap = null;
    }

    @Test
    public final void testPutSameAddedValueInitialEmpty() {

        // Add a new value to the map
        initialEmptyMap.put("four", "4");

        // Check the result
        assertThat(initialEmptyMap.isChanged()).isTrue();
        assertThat(initialEmptyMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyMap.get("four")).isEqualTo("4");
        assertThat(initialEmptyMap.getAdded()).isEqualTo(toMap("four=4"));
        assertThat(initialEmptyMap.getChanged()).isEmpty();
        assertThat(initialEmptyMap.getRemoved()).isEmpty();

        // Put the same value again
        initialEmptyMap.put("four", "4");

        // Should be the same state!
        assertThat(initialEmptyMap.isChanged()).isTrue();
        assertThat(initialEmptyMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyMap.get("four")).isEqualTo("4");
        assertThat(initialEmptyMap.getAdded()).isEqualTo(toMap("four=4"));
        assertThat(initialEmptyMap.getChanged()).isEmpty();
        assertThat(initialEmptyMap.getRemoved()).isEmpty();

    }

    @Test
    public final void testPutSameAddedValueInitialFilled() {

        // Add a new value to the map
        initialFilledMap.put("four", "4");

        // Check the result
        assertThat(initialFilledMap.isChanged()).isTrue();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledMap.get("four")).isEqualTo("4");
        assertThat(initialFilledMap.getAdded()).isEqualTo(toMap("four=4"));
        assertThat(initialFilledMap.getChanged()).isEmpty();
        assertThat(initialFilledMap.getRemoved()).isEmpty();

        // Put the same value again
        initialFilledMap.put("four", "4");

        // Should be the same state!
        assertThat(initialFilledMap.isChanged()).isTrue();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledMap.get("four")).isEqualTo("4");
        assertThat(initialFilledMap.getAdded()).isEqualTo(toMap("four=4"));
        assertThat(initialFilledMap.getChanged()).isEmpty();
        assertThat(initialFilledMap.getRemoved()).isEmpty();

    }

    @Test
    public final void testPutDifferentAddedValueInitialEmpty() {

        // Add a new value to the map
        initialEmptyMap.put("four", "4");

        // Check the result
        assertThat(initialEmptyMap.isChanged()).isTrue();
        assertThat(initialEmptyMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyMap.get("four")).isEqualTo("4");
        assertThat(initialEmptyMap.getAdded()).isEqualTo(toMap("four=4"));
        assertThat(initialEmptyMap.getChanged()).isEmpty();
        assertThat(initialEmptyMap.getRemoved()).isEmpty();

        // Change the value
        initialEmptyMap.put("four", "100");

        // Check the result
        assertThat(initialEmptyMap.isChanged()).isTrue();
        assertThat(initialEmptyMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyMap.get("four")).isEqualTo("100");
        assertThat(initialEmptyMap.getAdded()).isEqualTo(toMap("four=100"));
        assertThat(initialEmptyMap.getChanged()).isEmpty();
        assertThat(initialEmptyMap.getRemoved()).isEmpty();

    }

    @Test
    public final void testPutDifferentAddedValueInitialFilled() {

        // Add a new value to the map
        initialFilledMap.put("four", "4");

        // Check the result
        assertThat(initialFilledMap.isChanged()).isTrue();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledMap.get("four")).isEqualTo("4");
        assertThat(initialFilledMap.getAdded()).isEqualTo(toMap("four=4"));
        assertThat(initialFilledMap.getChanged()).isEmpty();
        assertThat(initialFilledMap.getRemoved()).isEmpty();

        // Change the value
        initialFilledMap.put("four", "100");

        // Check the result
        assertThat(initialFilledMap.isChanged()).isTrue();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledMap.get("four")).isEqualTo("100");
        assertThat(initialFilledMap.getAdded()).isEqualTo(toMap("four=100"));
        assertThat(initialFilledMap.getChanged()).isEmpty();
        assertThat(initialFilledMap.getRemoved()).isEmpty();

    }

    @Test
    public final void testPutSameChangedValue() {

        // Change a value
        initialFilledMap.put("three", "11");

        // Check the result
        assertThat(initialFilledMap.isChanged()).isTrue();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledMap.get("three")).isEqualTo("11");
        assertThat(initialFilledMap.getAdded()).isEmpty();
        assertThat(initialFilledMap.getChanged()).isEqualTo(toMap("three=3"));
        assertThat(initialFilledMap.getRemoved()).isEmpty();

        // Set the same the value again
        initialFilledMap.put("three", "11");

        // Should be the same state!
        assertThat(initialFilledMap.isChanged()).isTrue();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledMap.get("three")).isEqualTo("11");
        assertThat(initialFilledMap.getAdded()).isEmpty();
        assertThat(initialFilledMap.getChanged()).isEqualTo(toMap("three=3"));
        assertThat(initialFilledMap.getRemoved()).isEmpty();

    }

    @Test
    public final void testPutSameDeletedValue() {

        // Delete a value
        initialFilledMap.remove("three");

        // Check the result
        assertThat(initialFilledMap.isChanged()).isTrue();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledMap.get("three")).isEqualTo(null);
        assertThat(initialFilledMap).hasSize(2);
        assertThat(initialFilledMap.getAdded()).isEmpty();
        assertThat(initialFilledMap.getChanged()).isEmpty();
        assertThat(initialFilledMap.getRemoved()).isEqualTo(toMap("three=3"));

        // Add the same value again
        initialFilledMap.put("three", "3");

        // Check result
        assertThat(initialFilledMap.isChanged()).isFalse();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isFalse();
        assertThat(initialFilledMap.get("three")).isEqualTo("3");
        assertThat(initialFilledMap).hasSize(3);
        assertThat(initialFilledMap.getAdded()).isEmpty();
        assertThat(initialFilledMap.getChanged()).isEmpty();
        assertThat(initialFilledMap.getRemoved()).isEmpty();

    }

    @Test
    public final void testPutDifferentDeletedValue() {

        // Delete a value
        initialFilledMap.remove("three");

        // Check the result
        assertThat(initialFilledMap.isChanged()).isTrue();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledMap.get("three")).isEqualTo(null);
        assertThat(initialFilledMap).hasSize(2);
        assertThat(initialFilledMap.getAdded()).isEmpty();
        assertThat(initialFilledMap.getChanged()).isEmpty();
        assertThat(initialFilledMap.getRemoved()).isEqualTo(toMap("three=3"));

        // Add the the same key but another value
        initialFilledMap.put("three", "11");

        // Check result
        assertThat(initialFilledMap.isChanged()).isTrue();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledMap.get("three")).isEqualTo("11");
        assertThat(initialFilledMap).hasSize(3);
        assertThat(initialFilledMap.getAdded()).isEmpty();
        assertThat(initialFilledMap.getChanged()).isEqualTo(toMap("three=3"));
        assertThat(initialFilledMap.getRemoved()).isEmpty();

    }

    @Test
    public final void testPutNewKeyInitialEmpty() {

        // Add a new value to the empty map
        initialEmptyMap.put("one", "1");

        // Check the result
        assertThat(initialEmptyMap.isChanged()).isTrue();
        assertThat(initialEmptyMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyMap.get("one")).isEqualTo("1");
        assertThat(initialEmptyMap).hasSize(1);
        assertThat(initialEmptyMap.getAdded()).isEqualTo(toMap("one=1"));
        assertThat(initialEmptyMap.getChanged()).isEmpty();
        assertThat(initialEmptyMap.getRemoved()).isEmpty();

    }

    @Test
    public final void testPutNewKeyInitialFilled() {

        // Add a new value to the map
        initialFilledMap.put("four", "4");

        // Check the result
        assertThat(initialFilledMap.isChanged()).isTrue();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledMap.get("four")).isEqualTo("4");
        assertThat(initialFilledMap).hasSize(4);
        assertThat(initialFilledMap.getAdded()).isEqualTo(toMap("four=4"));
        assertThat(initialFilledMap.getChanged()).isEmpty();
        assertThat(initialFilledMap.getRemoved()).isEmpty();

    }

    @Test
    public final void testChangeValue() {

        // Change an existing value
        initialFilledMap.put("two", "10");

        // Check the result
        assertThat(initialFilledMap.isChanged()).isTrue();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledMap.get("one")).isEqualTo("1");
        assertThat(initialFilledMap.get("two")).isEqualTo("10");
        assertThat(initialFilledMap.get("three")).isEqualTo("3");
        assertThat(initialFilledMap).hasSize(3);
        assertThat(initialFilledMap.getAdded()).isEmpty();
        assertThat(initialFilledMap.getChanged()).isEqualTo(toMap("two=2"));
        assertThat(initialFilledMap.getRemoved()).isEmpty();

        // Change the value again to original value
        initialFilledMap.put("two", "2");

        // Check the result
        assertThat(initialFilledMap.isChanged()).isFalse();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isFalse();
        assertThat(initialFilledMap.get("one")).isEqualTo("1");
        assertThat(initialFilledMap.get("two")).isEqualTo("2");
        assertThat(initialFilledMap.get("three")).isEqualTo("3");
        assertThat(initialFilledMap).hasSize(3);
        assertThat(initialFilledMap.getAdded()).isEmpty();
        assertThat(initialFilledMap.getChanged()).isEmpty();
        assertThat(initialFilledMap.getRemoved()).isEmpty();

    }

    @Test
    public final void testRemoveAddedInitialEmpty() {

        // Add a new value to the map
        initialEmptyMap.put("four", "4");

        // Check the result
        assertThat(initialEmptyMap.isChanged()).isTrue();
        assertThat(initialEmptyMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialEmptyMap.get("four")).isEqualTo("4");
        assertThat(initialEmptyMap).hasSize(1);
        assertThat(initialEmptyMap.getAdded()).isEqualTo(toMap("four=4"));
        assertThat(initialEmptyMap.getChanged()).isEmpty();
        assertThat(initialEmptyMap.getRemoved()).isEmpty();

        // Remove the value
        initialEmptyMap.remove("four");

        // Check the result
        assertThat(initialEmptyMap.isChanged()).isFalse();
        assertThat(initialEmptyMap.hasChangedSinceTagging()).isFalse();
        assertThat(initialEmptyMap).isEmpty();
        assertThat(initialEmptyMap.getAdded()).isEmpty();
        assertThat(initialEmptyMap.getChanged()).isEmpty();
        assertThat(initialEmptyMap.getRemoved()).isEmpty();

    }

    @Test
    public final void testRemoveAddedInitialFilled() {

        // Add a new value to the map
        initialFilledMap.put("four", "4");

        // Check the result
        assertThat(initialFilledMap.isChanged()).isTrue();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledMap.get("four")).isEqualTo("4");
        assertThat(initialFilledMap).hasSize(4);
        assertThat(initialFilledMap.getAdded()).isEqualTo(toMap("four=4"));
        assertThat(initialFilledMap.getChanged()).isEmpty();
        assertThat(initialFilledMap.getRemoved()).isEmpty();

        // Remove the value
        initialFilledMap.remove("four");

        // Check the result
        assertThat(initialFilledMap.isChanged()).isFalse();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isFalse();
        assertThat(initialFilledMap.get("four")).isEqualTo(null);
        assertThat(initialFilledMap).hasSize(3);
        assertThat(initialFilledMap.getAdded()).isEmpty();
        assertThat(initialFilledMap.getChanged()).isEmpty();
        assertThat(initialFilledMap.getRemoved()).isEmpty();

    }

    @Test
    public final void testRemoveChanged() {

        // Change an existing value
        initialFilledMap.put("two", "10");

        // Check the result
        assertThat(initialFilledMap.isChanged()).isTrue();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledMap.get("two")).isEqualTo("10");
        assertThat(initialFilledMap).hasSize(3);
        assertThat(initialFilledMap.getAdded()).isEmpty();
        assertThat(initialFilledMap.getChanged()).isEqualTo(toMap("two=2"));
        assertThat(initialFilledMap.getRemoved()).isEmpty();

        // Remove the changed entry
        initialFilledMap.remove("two");

        // Check the result
        assertThat(initialFilledMap.isChanged()).isTrue();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledMap.get("two")).isEqualTo(null);
        assertThat(initialFilledMap).hasSize(2);
        assertThat(initialFilledMap.getAdded()).isEmpty();
        assertThat(initialFilledMap.getChanged()).isEmpty();
        assertThat(initialFilledMap.getRemoved()).isEqualTo(toMap("two=2"));

    }

    @Test
    public final void testRemoveDeleted() {

        // Remove a value
        initialFilledMap.remove("two");

        // Check the result
        assertThat(initialFilledMap.isChanged()).isTrue();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledMap.get("two")).isEqualTo(null);
        assertThat(initialFilledMap).hasSize(2);
        assertThat(initialFilledMap.getAdded()).isEmpty();
        assertThat(initialFilledMap.getChanged()).isEmpty();
        assertThat(initialFilledMap.getRemoved()).isEqualTo(toMap("two=2"));

        // Remove the value again
        initialFilledMap.remove("two");

        // Should be the same state!
        assertThat(initialFilledMap.isChanged()).isTrue();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledMap.get("two")).isEqualTo(null);
        assertThat(initialFilledMap).hasSize(2);
        assertThat(initialFilledMap.getAdded()).isEmpty();
        assertThat(initialFilledMap.getChanged()).isEmpty();
        assertThat(initialFilledMap.getRemoved()).isEqualTo(toMap("two=2"));

    }

    @Test
    public final void testRemove() {

        // Remove a value from the map
        initialFilledMap.remove("one");

        // Check the result
        assertThat(initialFilledMap.isChanged()).isTrue();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledMap).hasSize(2);
        assertThat(initialFilledMap.getAdded()).isEmpty();
        assertThat(initialFilledMap.getChanged()).isEmpty();
        assertThat(initialFilledMap.getRemoved()).isEqualTo(toMap("one=1"));

    }

    @Test
    public final void testRemoveUnknownKey() {

        // Remove a value that is not in the map
        initialFilledMap.remove("four");

        // Check the result
        assertThat(initialFilledMap.isChanged()).isFalse();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isFalse();
        assertThat(initialFilledMap).hasSize(3);
        assertThat(initialFilledMap.getAdded()).isEmpty();
        assertThat(initialFilledMap.getChanged()).isEmpty();
        assertThat(initialFilledMap.getRemoved()).isEmpty();

    }

    @Test
    public final void testClearInitialEmptyMap() {

        // Remove a value that is not in the map
        initialEmptyMap.clear();

        // Check the result
        assertThat(initialEmptyMap.isChanged()).isFalse();
        assertThat(initialEmptyMap.hasChangedSinceTagging()).isFalse();
        assertThat(initialEmptyMap).isEmpty();
        assertThat(initialEmptyMap.getAdded()).isEmpty();
        assertThat(initialEmptyMap.getChanged()).isEmpty();
        assertThat(initialEmptyMap.getRemoved()).isEmpty();

    }

    @Test
    public final void testClearInitialFilledMap() {

        // Remove a value that is not in the map
        initialFilledMap.clear();

        // Check the result
        assertThat(initialFilledMap.isChanged()).isTrue();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledMap).isEmpty();
        assertThat(initialFilledMap.getAdded()).isEmpty();
        assertThat(initialFilledMap.getChanged()).isEmpty();
        assertThat(initialFilledMap.getRemoved()).hasSize(3);
        assertThat(initialFilledMap.getRemoved()).isEqualTo(toMap("one=1,two=2,three=3"));

    }

    @Test
    public final void testRevert() {

        // Add a value
        initialFilledMap.put("four", "4");
        // Change a value
        initialFilledMap.put("three", "11");
        // Remove a value
        initialFilledMap.remove("two");

        // Check the precondition
        assertThat(initialFilledMap.isChanged()).isTrue();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledMap).hasSize(3);
        assertThat(initialFilledMap.get("one")).isEqualTo("1");
        assertThat(initialFilledMap.get("two")).isEqualTo(null);
        assertThat(initialFilledMap.get("three")).isEqualTo("11");
        assertThat(initialFilledMap.get("four")).isEqualTo("4");
        assertThat(initialFilledMap.getAdded()).isEqualTo(toMap("four=4"));
        assertThat(initialFilledMap.getChanged()).isEqualTo(toMap("three=3"));
        assertThat(initialFilledMap.getRemoved()).isEqualTo(toMap("two=2"));

        // Roll back the changes
        initialFilledMap.revert();

        // Check the result
        assertThat(initialFilledMap.isChanged()).isFalse();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isFalse();
        assertThat(initialFilledMap).hasSize(3);
        assertThat(initialFilledMap.get("one")).isEqualTo("1");
        assertThat(initialFilledMap.get("two")).isEqualTo("2");
        assertThat(initialFilledMap.get("three")).isEqualTo("3");
        assertThat(initialFilledMap.getAdded()).isEmpty();
        assertThat(initialFilledMap.getChanged()).isEmpty();
        assertThat(initialFilledMap.getRemoved()).isEmpty();

    }

    @Test
    public void testTag() {

        // Map is always in tag mode after construction
        initialFilledMap.untag();

        // Add/Change/Remove a value
        initialFilledMap.put("four", "4");
        initialFilledMap.put("three", "11");
        initialFilledMap.remove("two");

        // Check the precondition
        assertThat(initialFilledMap.isChanged()).isFalse();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isFalse();
        assertThat(initialFilledMap).hasSize(3);
        assertThat(initialFilledMap.get("one")).isEqualTo("1");
        assertThat(initialFilledMap.get("two")).isEqualTo(null);
        assertThat(initialFilledMap.get("three")).isEqualTo("11");
        assertThat(initialFilledMap.get("four")).isEqualTo("4");
        assertThat(initialFilledMap.getAdded()).isEmpty();
        assertThat(initialFilledMap.getChanged()).isEmpty();
        assertThat(initialFilledMap.getRemoved()).isEmpty();

        // Tag and revert the changes manually
        initialFilledMap.tag();
        initialFilledMap.remove("four");
        initialFilledMap.put("three", "3");
        initialFilledMap.put("two", "2");

        // Check the result
        assertThat(initialFilledMap.isChanged()).isTrue();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledMap).hasSize(3);
        assertThat(initialFilledMap.get("one")).isEqualTo("1");
        assertThat(initialFilledMap.get("two")).isEqualTo("2");
        assertThat(initialFilledMap.get("three")).isEqualTo("3");
        assertThat(initialFilledMap.getAdded()).isEqualTo(toMap("two=2"));
        assertThat(initialFilledMap.getChanged()).isEqualTo(toMap("three=11"));
        assertThat(initialFilledMap.getRemoved()).isEqualTo(toMap("four=4"));

    }

    @Test
    public void testUntag() {

        // Add/change/delete a value
        initialFilledMap.put("four", "4");
        initialFilledMap.put("three", "11");
        initialFilledMap.remove("two");

        // Check the precondition
        assertThat(initialFilledMap.isChanged()).isTrue();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledMap).hasSize(3);
        assertThat(initialFilledMap.get("one")).isEqualTo("1");
        assertThat(initialFilledMap.get("two")).isEqualTo(null);
        assertThat(initialFilledMap.get("three")).isEqualTo("11");
        assertThat(initialFilledMap.get("four")).isEqualTo("4");
        assertThat(initialFilledMap.getAdded()).isEqualTo(toMap("four=4"));
        assertThat(initialFilledMap.getChanged()).isEqualTo(toMap("three=3"));
        assertThat(initialFilledMap.getRemoved()).isEqualTo(toMap("two=2"));

        // Roll back the changes
        initialFilledMap.untag();

        // Check the result
        assertThat(initialFilledMap.isChanged()).isFalse();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isFalse();
        assertThat(initialFilledMap).hasSize(3);
        assertThat(initialFilledMap.get("one")).isEqualTo("1");
        assertThat(initialFilledMap.get("four")).isEqualTo("4");
        assertThat(initialFilledMap.get("three")).isEqualTo("11");
        assertThat(initialFilledMap.getAdded()).isEmpty();
        assertThat(initialFilledMap.getChanged()).isEmpty();
        assertThat(initialFilledMap.getRemoved()).isEmpty();

    }

    @Test
    public void testIsTagged() {

        // Should be in tag mode after construction
        assertThat(initialFilledMap.isTagged()).isTrue();
        initialFilledMap.untag();
        assertThat(initialFilledMap.isTagged()).isFalse();
        initialFilledMap.tag();
        assertThat(initialFilledMap.isTagged()).isTrue();

    }

    @Test
    public void testRevertToTag() {

        // Add a value
        initialFilledMap.put("four", "4");
        // Change a value
        initialFilledMap.put("three", "11");
        // Remove a value
        initialFilledMap.remove("two");

        // Check the precondition
        assertThat(initialFilledMap.isChanged()).isTrue();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledMap).hasSize(3);
        assertThat(initialFilledMap.get("one")).isEqualTo("1");
        assertThat(initialFilledMap.get("two")).isEqualTo(null);
        assertThat(initialFilledMap.get("three")).isEqualTo("11");
        assertThat(initialFilledMap.get("four")).isEqualTo("4");
        assertThat(initialFilledMap.getAdded()).isEqualTo(toMap("four=4"));
        assertThat(initialFilledMap.getChanged()).isEqualTo(toMap("three=3"));
        assertThat(initialFilledMap.getRemoved()).isEqualTo(toMap("two=2"));

        // Roll back the changes
        initialFilledMap.revertToTag();

        // Check the result
        assertThat(initialFilledMap.isChanged()).isFalse();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isFalse();
        assertThat(initialFilledMap).hasSize(3);
        assertThat(initialFilledMap.get("one")).isEqualTo("1");
        assertThat(initialFilledMap.get("two")).isEqualTo("2");
        assertThat(initialFilledMap.get("three")).isEqualTo("3");
        assertThat(initialFilledMap.getAdded()).isEmpty();
        assertThat(initialFilledMap.getChanged()).isEmpty();
        assertThat(initialFilledMap.getRemoved()).isEmpty();

    }

    @Test
    public void testPutAll() {

        initialFilledMap.putAll(toMap("four=4,three=11"));

        // Check the result
        assertThat(initialFilledMap.isChanged()).isTrue();
        assertThat(initialFilledMap.hasChangedSinceTagging()).isTrue();
        assertThat(initialFilledMap).hasSize(4);
        assertThat(initialFilledMap.get("one")).isEqualTo("1");
        assertThat(initialFilledMap.get("two")).isEqualTo("2");
        assertThat(initialFilledMap.get("three")).isEqualTo("11");
        assertThat(initialFilledMap.get("four")).isEqualTo("4");
        assertThat(initialFilledMap.getAdded()).isEqualTo(toMap("four=4"));
        assertThat(initialFilledMap.getChanged()).isEqualTo(toMap("three=3"));
        assertThat(initialFilledMap.getRemoved()).isEmpty();

    }

}
// CHECKSTYLE:ON
