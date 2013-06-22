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

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.testng.Assert;

//CHECKSTYLE:OFF
public class ChangeTrackingMapTest {

    private ChangeTrackingMap initialEmptyMap;

    private ChangeTrackingMap initialFilledMap;

    private Map toMap(final String keyValues) {
        final Map map = new HashMap();
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

    /**
     * @testng.before-method
     */
    public final void beforeMethod() {
        initialEmptyMap = new ChangeTrackingMap(new HashMap());
        initialFilledMap = new ChangeTrackingMap(toMap("one=1,two=2,three=3"));
    }

    /**
     * @testng.after-method
     */
    public final void afterMethod() {
        initialFilledMap = null;
        initialEmptyMap = null;
    }

    /**
     * @testng.test
     */
    public final void testPutSameAddedValueInitialEmpty() {

        // Add a new value to the map
        initialEmptyMap.put("four", "4");

        // Check the result
        Assert.assertTrue(initialEmptyMap.isChanged());
        Assert.assertTrue(initialEmptyMap.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyMap.get("four"), "4");
        Assert.assertEquals(initialEmptyMap.getAdded(), toMap("four=4"));
        Assert.assertEquals(initialEmptyMap.getChanged().size(), 0);
        Assert.assertEquals(initialEmptyMap.getRemoved().size(), 0);

        // Put the same value again
        initialEmptyMap.put("four", "4");

        // Should be the same state!
        Assert.assertTrue(initialEmptyMap.isChanged());
        Assert.assertTrue(initialEmptyMap.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyMap.get("four"), "4");
        Assert.assertEquals(initialEmptyMap.getAdded(), toMap("four=4"));
        Assert.assertEquals(initialEmptyMap.getChanged().size(), 0);
        Assert.assertEquals(initialEmptyMap.getRemoved().size(), 0);

    }

    /**
     * @testng.test
     */
    public final void testPutSameAddedValueInitialFilled() {

        // Add a new value to the map
        initialFilledMap.put("four", "4");

        // Check the result
        Assert.assertTrue(initialFilledMap.isChanged());
        Assert.assertTrue(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.get("four"), "4");
        Assert.assertEquals(initialFilledMap.getAdded(), toMap("four=4"));
        Assert.assertEquals(initialFilledMap.getChanged().size(), 0);
        Assert.assertEquals(initialFilledMap.getRemoved().size(), 0);

        // Put the same value again
        initialFilledMap.put("four", "4");

        // Should be the same state!
        Assert.assertTrue(initialFilledMap.isChanged());
        Assert.assertTrue(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.get("four"), "4");
        Assert.assertEquals(initialFilledMap.getAdded(), toMap("four=4"));
        Assert.assertEquals(initialFilledMap.getChanged().size(), 0);
        Assert.assertEquals(initialFilledMap.getRemoved().size(), 0);

    }

    /**
     * @testng.test
     */
    public final void testPutDifferentAddedValueInitialEmpty() {

        // Add a new value to the map
        initialEmptyMap.put("four", "4");

        // Check the result
        Assert.assertTrue(initialEmptyMap.isChanged());
        Assert.assertTrue(initialEmptyMap.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyMap.get("four"), "4");
        Assert.assertEquals(initialEmptyMap.getAdded(), toMap("four=4"));
        Assert.assertEquals(initialEmptyMap.getChanged().size(), 0);
        Assert.assertEquals(initialEmptyMap.getRemoved().size(), 0);

        // Change the value
        initialEmptyMap.put("four", "100");

        // Check the result
        Assert.assertTrue(initialEmptyMap.isChanged());
        Assert.assertTrue(initialEmptyMap.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyMap.get("four"), "100");
        Assert.assertEquals(initialEmptyMap.getAdded(), toMap("four=100"));
        Assert.assertEquals(initialEmptyMap.getChanged().size(), 0);
        Assert.assertEquals(initialEmptyMap.getRemoved().size(), 0);

    }

    /**
     * @testng.test
     */
    public final void testPutDifferentAddedValueInitialFilled() {

        // Add a new value to the map
        initialFilledMap.put("four", "4");

        // Check the result
        Assert.assertTrue(initialFilledMap.isChanged());
        Assert.assertTrue(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.get("four"), "4");
        Assert.assertEquals(initialFilledMap.getAdded(), toMap("four=4"));
        Assert.assertEquals(initialFilledMap.getChanged().size(), 0);
        Assert.assertEquals(initialFilledMap.getRemoved().size(), 0);

        // Change the value
        initialFilledMap.put("four", "100");

        // Check the result
        Assert.assertTrue(initialFilledMap.isChanged());
        Assert.assertTrue(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.get("four"), "100");
        Assert.assertEquals(initialFilledMap.getAdded(), toMap("four=100"));
        Assert.assertEquals(initialFilledMap.getChanged().size(), 0);
        Assert.assertEquals(initialFilledMap.getRemoved().size(), 0);

    }

    /**
     * @testng.test
     */
    public final void testPutSameChangedValue() {

        // Change a value
        initialFilledMap.put("three", "11");

        // Check the result
        Assert.assertTrue(initialFilledMap.isChanged());
        Assert.assertTrue(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.get("three"), "11");
        Assert.assertEquals(initialFilledMap.getAdded().size(), 0);
        Assert.assertEquals(initialFilledMap.getChanged(), toMap("three=3"));
        Assert.assertEquals(initialFilledMap.getRemoved().size(), 0);

        // Set the same the value again
        initialFilledMap.put("three", "11");

        // Should be the same state!
        Assert.assertTrue(initialFilledMap.isChanged());
        Assert.assertTrue(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.get("three"), "11");
        Assert.assertEquals(initialFilledMap.getAdded().size(), 0);
        Assert.assertEquals(initialFilledMap.getChanged(), toMap("three=3"));
        Assert.assertEquals(initialFilledMap.getRemoved().size(), 0);

    }

    /**
     * @testng.test
     */
    public final void testPutSameDeletedValue() {

        // Delete a value
        initialFilledMap.remove("three");

        // Check the result
        Assert.assertTrue(initialFilledMap.isChanged());
        Assert.assertTrue(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.get("three"), null);
        Assert.assertEquals(initialFilledMap.size(), 2);
        Assert.assertEquals(initialFilledMap.getAdded().size(), 0);
        Assert.assertEquals(initialFilledMap.getChanged().size(), 0);
        Assert.assertEquals(initialFilledMap.getRemoved(), toMap("three=3"));

        // Add the same value again
        initialFilledMap.put("three", "3");

        // Check result
        Assert.assertFalse(initialFilledMap.isChanged());
        Assert.assertFalse(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.get("three"), "3");
        Assert.assertEquals(initialFilledMap.size(), 3);
        Assert.assertEquals(initialFilledMap.getAdded().size(), 0);
        Assert.assertEquals(initialFilledMap.getChanged().size(), 0);
        Assert.assertEquals(initialFilledMap.getRemoved().size(), 0);

    }

    /**
     * @testng.test
     */
    public final void testPutDifferentDeletedValue() {

        // Delete a value
        initialFilledMap.remove("three");

        // Check the result
        Assert.assertTrue(initialFilledMap.isChanged());
        Assert.assertTrue(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.get("three"), null);
        Assert.assertEquals(initialFilledMap.size(), 2);
        Assert.assertEquals(initialFilledMap.getAdded().size(), 0);
        Assert.assertEquals(initialFilledMap.getChanged().size(), 0);
        Assert.assertEquals(initialFilledMap.getRemoved(), toMap("three=3"));

        // Add the the same key but another value
        initialFilledMap.put("three", "11");

        // Check result
        Assert.assertTrue(initialFilledMap.isChanged());
        Assert.assertTrue(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.get("three"), "11");
        Assert.assertEquals(initialFilledMap.size(), 3);
        Assert.assertEquals(initialFilledMap.getAdded().size(), 0);
        Assert.assertEquals(initialFilledMap.getChanged(), toMap("three=3"));
        Assert.assertEquals(initialFilledMap.getRemoved().size(), 0);

    }

    /**
     * @testng.test
     */
    public final void testPutNewKeyInitialEmpty() {

        // Add a new value to the empty map
        initialEmptyMap.put("one", "1");

        // Check the result
        Assert.assertTrue(initialEmptyMap.isChanged());
        Assert.assertTrue(initialEmptyMap.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyMap.get("one"), "1");
        Assert.assertEquals(initialEmptyMap.size(), 1);
        Assert.assertEquals(initialEmptyMap.getAdded(), toMap("one=1"));
        Assert.assertEquals(initialEmptyMap.getChanged().size(), 0);
        Assert.assertEquals(initialEmptyMap.getRemoved().size(), 0);

    }

    /**
     * @testng.test
     */
    public final void testPutNewKeyInitialFilled() {

        // Add a new value to the map
        initialFilledMap.put("four", "4");

        // Check the result
        Assert.assertTrue(initialFilledMap.isChanged());
        Assert.assertTrue(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.get("four"), "4");
        Assert.assertEquals(initialFilledMap.size(), 4);
        Assert.assertEquals(initialFilledMap.getAdded(), toMap("four=4"));
        Assert.assertEquals(initialFilledMap.getChanged().size(), 0);
        Assert.assertEquals(initialFilledMap.getRemoved().size(), 0);

    }

    /**
     * @testng.test
     */
    public final void testChangeValue() {

        // Change an existing value
        initialFilledMap.put("two", "10");

        // Check the result
        Assert.assertTrue(initialFilledMap.isChanged());
        Assert.assertTrue(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.get("one"), "1");
        Assert.assertEquals(initialFilledMap.get("two"), "10");
        Assert.assertEquals(initialFilledMap.get("three"), "3");
        Assert.assertEquals(initialFilledMap.size(), 3);
        Assert.assertEquals(initialFilledMap.getAdded().size(), 0);
        Assert.assertEquals(initialFilledMap.getChanged(), toMap("two=2"));
        Assert.assertEquals(initialFilledMap.getRemoved().size(), 0);

        // Change the value again to original value
        initialFilledMap.put("two", "2");

        // Check the result
        Assert.assertFalse(initialFilledMap.isChanged());
        Assert.assertFalse(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.get("one"), "1");
        Assert.assertEquals(initialFilledMap.get("two"), "2");
        Assert.assertEquals(initialFilledMap.get("three"), "3");
        Assert.assertEquals(initialFilledMap.size(), 3);
        Assert.assertEquals(initialFilledMap.getAdded().size(), 0);
        Assert.assertEquals(initialFilledMap.getChanged().size(), 0);
        Assert.assertEquals(initialFilledMap.getRemoved().size(), 0);
        
    }

    /**
     * @testng.test
     */
    public final void testRemoveAddedInitialEmpty() {

        // Add a new value to the map
        initialEmptyMap.put("four", "4");

        // Check the result
        Assert.assertTrue(initialEmptyMap.isChanged());
        Assert.assertTrue(initialEmptyMap.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyMap.get("four"), "4");
        Assert.assertEquals(initialEmptyMap.size(), 1);
        Assert.assertEquals(initialEmptyMap.getAdded(), toMap("four=4"));
        Assert.assertEquals(initialEmptyMap.getChanged().size(), 0);
        Assert.assertEquals(initialEmptyMap.getRemoved().size(), 0);

        // Remove the value
        initialEmptyMap.remove("four");

        // Check the result
        Assert.assertFalse(initialEmptyMap.isChanged());
        Assert.assertFalse(initialEmptyMap.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyMap.size(), 0);
        Assert.assertEquals(initialEmptyMap.getAdded().size(), 0);
        Assert.assertEquals(initialEmptyMap.getChanged().size(), 0);
        Assert.assertEquals(initialEmptyMap.getRemoved().size(), 0);

    }

    /**
     * @testng.test
     */
    public final void testRemoveAddedInitialFilled() {

        // Add a new value to the map
        initialFilledMap.put("four", "4");

        // Check the result
        Assert.assertTrue(initialFilledMap.isChanged());
        Assert.assertTrue(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.get("four"), "4");
        Assert.assertEquals(initialFilledMap.size(), 4);
        Assert.assertEquals(initialFilledMap.getAdded(), toMap("four=4"));
        Assert.assertEquals(initialFilledMap.getChanged().size(), 0);
        Assert.assertEquals(initialFilledMap.getRemoved().size(), 0);

        // Remove the value
        initialFilledMap.remove("four");

        // Check the result
        Assert.assertFalse(initialFilledMap.isChanged());
        Assert.assertFalse(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.get("four"), null);
        Assert.assertEquals(initialFilledMap.size(), 3);
        Assert.assertEquals(initialFilledMap.getAdded().size(), 0);
        Assert.assertEquals(initialFilledMap.getChanged().size(), 0);
        Assert.assertEquals(initialFilledMap.getRemoved().size(), 0);

    }

    /**
     * @testng.test
     */
    public final void testRemoveChanged() {

        // Change an existing value
        initialFilledMap.put("two", "10");

        // Check the result
        Assert.assertTrue(initialFilledMap.isChanged());
        Assert.assertTrue(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.get("two"), "10");
        Assert.assertEquals(initialFilledMap.size(), 3);
        Assert.assertEquals(initialFilledMap.getAdded().size(), 0);
        Assert.assertEquals(initialFilledMap.getChanged(), toMap("two=2"));
        Assert.assertEquals(initialFilledMap.getRemoved().size(), 0);

        // Remove the changed entry
        initialFilledMap.remove("two");

        // Check the result
        Assert.assertTrue(initialFilledMap.isChanged());
        Assert.assertTrue(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.get("two"), null);
        Assert.assertEquals(initialFilledMap.size(), 2);
        Assert.assertEquals(initialFilledMap.getAdded().size(), 0);
        Assert.assertEquals(initialFilledMap.getChanged().size(), 0);
        Assert.assertEquals(initialFilledMap.getRemoved(), toMap("two=2"));

    }

    /**
     * @testng.test
     */
    public final void testRemoveDeleted() {

        // Remove a value
        initialFilledMap.remove("two");

        // Check the result
        Assert.assertTrue(initialFilledMap.isChanged());
        Assert.assertTrue(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.get("two"), null);
        Assert.assertEquals(initialFilledMap.size(), 2);
        Assert.assertEquals(initialFilledMap.getAdded().size(), 0);
        Assert.assertEquals(initialFilledMap.getChanged().size(), 0);
        Assert.assertEquals(initialFilledMap.getRemoved(), toMap("two=2"));

        // Remove the value again
        initialFilledMap.remove("two");

        // Should be the same state!
        Assert.assertTrue(initialFilledMap.isChanged());
        Assert.assertTrue(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.get("two"), null);
        Assert.assertEquals(initialFilledMap.size(), 2);
        Assert.assertEquals(initialFilledMap.getAdded().size(), 0);
        Assert.assertEquals(initialFilledMap.getChanged().size(), 0);
        Assert.assertEquals(initialFilledMap.getRemoved(), toMap("two=2"));

    }

    /**
     * @testng.test
     */
    public final void testRemove() {

        // Remove a value from the map
        initialFilledMap.remove("one");

        // Check the result
        Assert.assertTrue(initialFilledMap.isChanged());
        Assert.assertTrue(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.size(), 2);
        Assert.assertEquals(initialFilledMap.getAdded().size(), 0);
        Assert.assertEquals(initialFilledMap.getChanged().size(), 0);
        Assert.assertEquals(initialFilledMap.getRemoved(), toMap("one=1"));

    }

    /**
     * @testng.test
     */
    public final void testRemoveUnknownKey() {

        // Remove a value that is not in the map
        initialFilledMap.remove("four");

        // Check the result
        Assert.assertFalse(initialFilledMap.isChanged());
        Assert.assertFalse(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.size(), 3);
        Assert.assertEquals(initialFilledMap.getAdded().size(), 0);
        Assert.assertEquals(initialFilledMap.getChanged().size(), 0);
        Assert.assertEquals(initialFilledMap.getRemoved().size(), 0);

    }

    /**
     * @testng.test
     */
    public final void testClearInitialEmptyMap() {

        // Remove a value that is not in the map
        initialEmptyMap.clear();

        // Check the result
        Assert.assertFalse(initialEmptyMap.isChanged());
        Assert.assertFalse(initialEmptyMap.hasChangedSinceTagging());
        Assert.assertEquals(initialEmptyMap.size(), 0);
        Assert.assertEquals(initialEmptyMap.getAdded().size(), 0);
        Assert.assertEquals(initialEmptyMap.getChanged().size(), 0);
        Assert.assertEquals(initialEmptyMap.getRemoved().size(), 0);

    }

    /**
     * @testng.test
     */
    public final void testClearInitialFilledMap() {

        // Remove a value that is not in the map
        initialFilledMap.clear();

        // Check the result
        Assert.assertTrue(initialFilledMap.isChanged());
        Assert.assertTrue(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.size(), 0);
        Assert.assertEquals(initialFilledMap.getAdded().size(), 0);
        Assert.assertEquals(initialFilledMap.getChanged().size(), 0);
        Assert.assertEquals(initialFilledMap.getRemoved().size(), 3);
        Assert.assertEquals(initialFilledMap.getRemoved(), toMap("one=1,two=2,three=3"));

    }

    /**
     * @testng.test
     */
    public final void testRevert() {

        // Add a value
        initialFilledMap.put("four", "4");
        // Change a value
        initialFilledMap.put("three", "11");
        // Remove a value
        initialFilledMap.remove("two");

        // Check the precondition
        Assert.assertTrue(initialFilledMap.isChanged());
        Assert.assertTrue(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.size(), 3);
        Assert.assertEquals(initialFilledMap.get("one"), "1");
        Assert.assertEquals(initialFilledMap.get("two"), null);
        Assert.assertEquals(initialFilledMap.get("three"), "11");
        Assert.assertEquals(initialFilledMap.get("four"), "4");
        Assert.assertEquals(initialFilledMap.getAdded(), toMap("four=4"));
        Assert.assertEquals(initialFilledMap.getChanged(), toMap("three=3"));
        Assert.assertEquals(initialFilledMap.getRemoved(), toMap("two=2"));

        // Roll back the changes
        initialFilledMap.revert();

        // Check the result
        Assert.assertFalse(initialFilledMap.isChanged());
        Assert.assertFalse(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.size(), 3);
        Assert.assertEquals(initialFilledMap.get("one"), "1");
        Assert.assertEquals(initialFilledMap.get("two"), "2");
        Assert.assertEquals(initialFilledMap.get("three"), "3");
        Assert.assertEquals(initialFilledMap.getAdded().size(), 0);
        Assert.assertEquals(initialFilledMap.getChanged().size(), 0);
        Assert.assertEquals(initialFilledMap.getRemoved().size(), 0);

    }

    /**
     * @testng.test
     */
    public void testTag() {
        
        // Map is always in tag mode after construction
        initialFilledMap.untag();
        
        // Add/Change/Remove a value
        initialFilledMap.put("four", "4");
        initialFilledMap.put("three", "11");
        initialFilledMap.remove("two");

        // Check the precondition
        Assert.assertFalse(initialFilledMap.isChanged());
        Assert.assertFalse(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.size(), 3);
        Assert.assertEquals(initialFilledMap.get("one"), "1");
        Assert.assertEquals(initialFilledMap.get("two"), null);
        Assert.assertEquals(initialFilledMap.get("three"), "11");
        Assert.assertEquals(initialFilledMap.get("four"), "4");
        Assert.assertEquals(initialFilledMap.getAdded().size(), 0);
        Assert.assertEquals(initialFilledMap.getChanged().size(), 0);
        Assert.assertEquals(initialFilledMap.getRemoved().size(), 0);
                
        // Tag and revert the changes manually
        initialFilledMap.tag();
        initialFilledMap.remove("four");
        initialFilledMap.put("three", "3");
        initialFilledMap.put("two", "2");
        
        // Check the result
        Assert.assertTrue(initialFilledMap.isChanged());
        Assert.assertTrue(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.size(), 3);
        Assert.assertEquals(initialFilledMap.get("one"), "1");
        Assert.assertEquals(initialFilledMap.get("two"), "2");
        Assert.assertEquals(initialFilledMap.get("three"), "3");
        Assert.assertEquals(initialFilledMap.getAdded(), toMap("two=2"));
        Assert.assertEquals(initialFilledMap.getChanged(), toMap("three=11"));
        Assert.assertEquals(initialFilledMap.getRemoved(), toMap("four=4"));
                
    }

    /**
     * @testng.test
     */
    public void testUntag() {
        
        // Add/change/delete a value
        initialFilledMap.put("four", "4");
        initialFilledMap.put("three", "11");
        initialFilledMap.remove("two");

        // Check the precondition
        Assert.assertTrue(initialFilledMap.isChanged());
        Assert.assertTrue(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.size(), 3);
        Assert.assertEquals(initialFilledMap.get("one"), "1");
        Assert.assertEquals(initialFilledMap.get("two"), null);
        Assert.assertEquals(initialFilledMap.get("three"), "11");
        Assert.assertEquals(initialFilledMap.get("four"), "4");
        Assert.assertEquals(initialFilledMap.getAdded(), toMap("four=4"));
        Assert.assertEquals(initialFilledMap.getChanged(), toMap("three=3"));
        Assert.assertEquals(initialFilledMap.getRemoved(), toMap("two=2"));

        // Roll back the changes
        initialFilledMap.untag();

        // Check the result
        Assert.assertFalse(initialFilledMap.isChanged());
        Assert.assertFalse(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.size(), 3);
        Assert.assertEquals(initialFilledMap.get("one"), "1");
        Assert.assertEquals(initialFilledMap.get("four"), "4");
        Assert.assertEquals(initialFilledMap.get("three"), "11");
        Assert.assertEquals(initialFilledMap.getAdded().size(), 0);
        Assert.assertEquals(initialFilledMap.getChanged().size(), 0);
        Assert.assertEquals(initialFilledMap.getRemoved().size(), 0);
        
    }

    /**
     * @testng.test
     */
    public void testIsTagged() {
        
        // Should be in tag mode after construction
        Assert.assertTrue(initialFilledMap.isTagged());
        initialFilledMap.untag();
        Assert.assertFalse(initialFilledMap.isTagged());
        initialFilledMap.tag();
        Assert.assertTrue(initialFilledMap.isTagged());
        
    }

    /**
     * @testng.test
     */
    public void testRevertToTag() {
        
        // Add a value
        initialFilledMap.put("four", "4");
        // Change a value
        initialFilledMap.put("three", "11");
        // Remove a value
        initialFilledMap.remove("two");

        // Check the precondition
        Assert.assertTrue(initialFilledMap.isChanged());
        Assert.assertTrue(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.size(), 3);
        Assert.assertEquals(initialFilledMap.get("one"), "1");
        Assert.assertEquals(initialFilledMap.get("two"), null);
        Assert.assertEquals(initialFilledMap.get("three"), "11");
        Assert.assertEquals(initialFilledMap.get("four"), "4");
        Assert.assertEquals(initialFilledMap.getAdded(), toMap("four=4"));
        Assert.assertEquals(initialFilledMap.getChanged(), toMap("three=3"));
        Assert.assertEquals(initialFilledMap.getRemoved(), toMap("two=2"));

        // Roll back the changes
        initialFilledMap.revertToTag();

        // Check the result
        Assert.assertFalse(initialFilledMap.isChanged());
        Assert.assertFalse(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.size(), 3);
        Assert.assertEquals(initialFilledMap.get("one"), "1");
        Assert.assertEquals(initialFilledMap.get("two"), "2");
        Assert.assertEquals(initialFilledMap.get("three"), "3");
        Assert.assertEquals(initialFilledMap.getAdded().size(), 0);
        Assert.assertEquals(initialFilledMap.getChanged().size(), 0);
        Assert.assertEquals(initialFilledMap.getRemoved().size(), 0);
        
    }

    /**
     * @testng.test
     */
    public void testPutAll() {
        
        initialFilledMap.putAll(toMap("four=4,three=11"));

        // Check the result
        Assert.assertTrue(initialFilledMap.isChanged());
        Assert.assertTrue(initialFilledMap.hasChangedSinceTagging());
        Assert.assertEquals(initialFilledMap.size(), 4);
        Assert.assertEquals(initialFilledMap.get("one"), "1");
        Assert.assertEquals(initialFilledMap.get("two"), "2");
        Assert.assertEquals(initialFilledMap.get("three"), "11");
        Assert.assertEquals(initialFilledMap.get("four"), "4");
        Assert.assertEquals(initialFilledMap.getAdded(), toMap("four=4"));
        Assert.assertEquals(initialFilledMap.getChanged(), toMap("three=3"));
        Assert.assertEquals(initialFilledMap.getRemoved().size(), 0);
        
    }
    
}
// CHECKSTYLE:ON
