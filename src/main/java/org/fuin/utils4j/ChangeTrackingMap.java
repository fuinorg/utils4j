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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A wrapper for maps that keeps track of all changes made to the map since construction. Only adding, replacing or deleting elements is
 * tracked (not changes inside the objects). It's also possible to revert all changes.
 * 
 * @param <K>
 *            the type of keys maintained by this map
 * @param <V>
 *            the type of mapped values
 */
public class ChangeTrackingMap<K, V> implements Map<K, V>, Taggable {

    private final Map<K, V> map;

    private final Map<K, V> added;

    private final Map<K, V> changed;

    private final Map<K, V> removed;

    private boolean tagged;

    /**
     * Constructor with covered map. The map is tagged at construction time - This means {@link #isTagged()} will return <code>true</code>
     * without calling {@link #tag()} first. If this behavior is not wanted you can call {@link #untag()} after constructing the map.
     * 
     * @param map
     *            Wrapped map - Be aware that this map will be changed by this class. There is no internal copy of the map - The reference
     *            itself is used.
     */
    public ChangeTrackingMap(final Map<K, V> map) {
        super();
        Utils4J.checkNotNull("map", map);
        this.map = map;
        this.added = new HashMap<>();
        this.changed = new HashMap<>();
        this.removed = new HashMap<>();
        this.tagged = true;
    }

    /**
     * Returns if the list has changed. If the map is not in tag mode (this means {@link #isTagged()} returns <code>true</code>) this method
     * will always return <code>false</code>.
     * 
     * @return If elements have been added or deleted <code>true</code> else <code>false</code>.
     */
    public final boolean isChanged() {
        return (added.size() > 0) || (changed.size() > 0) || (removed.size() > 0);
    }

    /**
     * Returns removed elements. If the map is not in tag mode (this means {@link #isTagged()} returns <code>true</code>) this method will
     * always return an empty map.
     * 
     * @return Elements that have been deleted since construction of this instance - Unmodifiable map!
     */
    public final Map<K, V> getRemoved() {
        return Collections.unmodifiableMap(removed);
    }

    /**
     * Returns changed elements. If the map is not in tag mode (this means {@link #isTagged()} returns <code>true</code>) this method will
     * always return an empty map.
     * 
     * @return Elements that have been changed since construction of this instance - Unmodifiable map!
     */
    public final Map<K, V> getChanged() {
        return Collections.unmodifiableMap(changed);
    }

    /**
     * Roll back all changes made since construction. This is the same function ad {@link #revertToTag()}. If the map is not in tag mode (
     * this means {@link #isTagged()} returns <code>true</code>) this method will do nothing.
     */
    public final void revert() {

        if (tagged) {

            // Remove the added entries
            final Iterator<K> addedIt = added.keySet().iterator();
            while (addedIt.hasNext()) {
                final K key = addedIt.next();
                map.remove(key);
                addedIt.remove();
            }

            // Replace the changed entries
            final Iterator<K> changedIt = changed.keySet().iterator();
            while (changedIt.hasNext()) {
                final K key = changedIt.next();
                final V value = changed.get(key);
                map.put(key, value);
                changedIt.remove();
            }

            // Add the removed entries
            final Iterator<K> removedIt = removed.keySet().iterator();
            while (removedIt.hasNext()) {
                final K key = removedIt.next();
                final V value = removed.get(key);
                map.put(key, value);
                removedIt.remove();
            }

        }

    }

    /**
     * Returns added elements. If the map is not in tag mode (this means {@link #isTagged()} returns <code>true</code>) this method will
     * always return an empty map.
     * 
     * @return Elements that have been added since construction of this instance - Unmodifiable map!
     */
    public final Map<K, V> getAdded() {
        return Collections.unmodifiableMap(added);
    }

    private void changeIntern(final K key, final V oldValue, final V newValue) {
        if (tagged) {
            final V addedValue = added.get(key);
            if (addedValue == null) {
                final V changedValue = changed.get(key);
                if (changedValue == null) {
                    final V removedValue = removed.get(key);
                    if (removedValue == null) {
                        if (oldValue == null) {
                            added.put(key, newValue);
                        } else {
                            changed.put(key, oldValue);
                        }
                    } else {
                        removed.remove(key);
                        if (!removedValue.equals(newValue)) {
                            changed.put(key, removedValue);
                        }
                    }
                } else {
                    if (changedValue.equals(newValue)) {
                        changed.remove(key);
                    }
                }
            } else {
                if (!addedValue.equals(newValue) && (newValue != null)) {
                    added.put(key, newValue);
                }
            }
        }
    }

    private void removeIntern(final K key, final V value) {
        if (tagged) {
            if (added.get(key) == null) {
                final V changedValue = changed.get(key);
                if (changedValue == null) {
                    if ((removed.get(key) == null) && (value != null)) {
                        removed.put(key, value);
                    }
                } else {
                    changed.remove(key);
                    removed.put(key, changedValue);
                }
            } else {
                added.remove(key);
            }
        }
    }

    @Override
    public final void clear() {
        final Iterator<K> it = map.keySet().iterator();
        while (it.hasNext()) {
            final K key = it.next();
            final V value = map.get(key);
            removeIntern(key, value);
        }
        if (tagged) {
            added.clear();
        }
        map.clear();
    }

    @Override
    public final boolean containsKey(final Object key) {
        return map.containsKey(key);
    }

    @Override
    public final boolean containsValue(final Object value) {
        return map.containsValue(value);
    }

    @Override
    public final Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    @Override
    public final V get(final Object key) {
        return map.get(key);
    }

    @Override
    public final boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public final Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public final V put(final K key, final V newValue) {
        final V oldValue = map.put(key, newValue);
        changeIntern(key, oldValue, newValue);
        return oldValue;
    }

    @Override
    public final void putAll(final Map<? extends K, ? extends V> newMap) {
        final Iterator<? extends K> it = newMap.keySet().iterator();
        while (it.hasNext()) {
            final K key = it.next();
            final V newValue = newMap.get(key);
            final V oldValue = map.put(key, newValue);
            changeIntern(key, oldValue, newValue);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public final V remove(final Object key) {
        final V oldValue = map.remove(key);
        removeIntern((K) key, oldValue);
        return oldValue;
    }

    @Override
    public final int size() {
        return map.size();
    }

    @Override
    public final Collection<V> values() {
        return map.values();
    }

    @Override
    public final String toString() {
        return map.toString();
    }

    @Override
    public final boolean hasChangedSinceTagging() {
        return isChanged();
    }

    @Override
    public final boolean isTagged() {
        return tagged;
    }

    @Override
    public final void revertToTag() {
        revert();
    }

    @Override
    public final void tag() {
        if (!tagged) {
            tagged = true;
        }
    }

    @Override
    public final void untag() {
        if (tagged) {
            tagged = false;
            added.clear();
            changed.clear();
            removed.clear();
        }
    }

}
