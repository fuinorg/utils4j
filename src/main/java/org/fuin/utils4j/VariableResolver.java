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

import org.jspecify.annotations.Nullable;

import java.util.*;

/**
 * Resolves the references from variable values to other variable names.
 */
public final class VariableResolver {

    private final Map<String, String> unresolved;

    private final Map<String, Integer> depth;

    private final Map<String, String> resolved;

    /**
     * Constructor with variable map.
     * 
     * @param unresolved
     *            Map to use - May be <code>null</code>.
     */
    public VariableResolver(@Nullable final Map<String, String> unresolved) {
        if (unresolved == null) {
            this.unresolved = new HashMap<>();
        } else {
            this.unresolved = new HashMap<>(unresolved);
        }
        depth = new HashMap<>();
        resolved = new HashMap<>();
        resolve();
    }

    private void resolve() {
        int max = 0;
        for (String name : unresolved.keySet()) {
            final String value = unresolved.get(name);
            final int d = resolve(name, value, new ArrayList<>());
            if (d > max) {
                max = d;
            }
        }

        for (int d = 0; d <= max; d++) {
            for (String name : unresolved.keySet()) {
                final String value = unresolved.get(name);
                if (Objects.requireNonNull(depth.get(name)) == d) {
                    resolved.put(name, Utils4J.replaceCrLfTab(Utils4J.replaceVars(value, resolved)));
                }
            }
        }

    }

    private int resolve(final String name, @Nullable final String value, final List<String> path) {

        // Check for cycles
        if (path.contains(name)) {
            final StringBuilder sb = new StringBuilder();
            for (String s : path) {
                sb.append(s).append(" > ");
            }
            sb.append(name);
            throw new IllegalStateException("Cycle: " + sb);
        }

        // Analyze
        final Integer existing = depth.get(name);
        if (existing != null) {
            return existing;
        }
        int d = 0;
        final Set<String> refs = references(value);
        if (!refs.isEmpty()) {
            for (String refName : refs) {
                final String refValue = unresolved.get(refName);
                d = 1 + resolve(refName, refValue, add(path, name));
            }
        }
        depth.put(name, d);
        return d;
    }

    private List<String> add(final List<String> list, final String name) {
        final List<String> newList = new ArrayList<>(list);
        newList.add(name);
        return newList;
    }

    /**
     * Returns the variable names and how many steps are necessary to resolve all references to other variables.
     * 
     * @return Names and state of all known variables - Never <code>null</code>, but may be empty.
     */
    public final Map<String, Integer> getDepth() {
        return depth;
    }

    /**
     * Returns a map of resolved name/value pairs.
     * 
     * @return Variable map - Never <code>null</code>, but may be empty.
     */
    public final Map<String, String> getResolved() {
        return resolved;
    }

    /**
     * Returns a map of unresolved name/value pairs.
     * 
     * @return Variable map - Never <code>null</code>, but may be empty.
     */
    public final Map<String, String> getUnresolved() {
        return unresolved;
    }

    /**
     * Returns a set of references variables.
     * 
     * @param value
     *            Value to parse - May be <code>null</code>.
     * 
     * @return Referenced variable names - Never <code>null</code>, but may be empty.
     */
    public static Set<String> references(@Nullable final String value) {

        final HashSet<String> names = new HashSet<>();
        if ((value == null) || (value.isEmpty())) {
            return names;
        }

        int end = -1;
        int from = 0;
        int start = -1;
        while ((start = value.indexOf("${", from)) > -1) {
            end = value.indexOf('}', start + 1);
            if (end == -1) {
                // No closing bracket found...
                from = value.length();
            } else {
                names.add(value.substring(start + 2, end));
                from = end + 1;
            }
        }

        return names;

    }

}
