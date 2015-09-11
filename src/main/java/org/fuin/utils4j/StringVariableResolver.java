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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Resolves the references from variable values to other variable names.
 */
public final class StringVariableResolver {

    private List<Variable> vars;

    private Map<String, String> unresolved;

    private Map<String, Integer> depth;

    private Map<String, String> resolved;

    /**
     * Constructor with variable list.
     * 
     * @param vars
     *            List to use - May be <code>null</code>.
     */
    public StringVariableResolver(final List<Variable> vars) {
        if (vars == null) {
            this.vars = new ArrayList<Variable>();
        } else {
            this.vars = vars;
        }
        unresolved = new HashMap<String, String>();
        for (final Variable var : this.vars) {
            unresolved.put(var.getName(), var.getValue());
        }
        depth = new HashMap<String, Integer>();
        resolved = new HashMap<String, String>();
        resolve();
    }

    private void resolve() {
        int max = 0;
        for (final Variable var : vars) {
            final int d = resolve(var.getName(), var.getValue(),
                    new ArrayList<String>());
            if (d > max) {
                max = d;
            }
        }

        for (int d = 0; d <= max; d++) {
            for (final Variable var : vars) {
                if (depth.get(var.getName()).intValue() == d) {
                    resolved.put(var.getName(),
                            replaceVars(var.getValue(), resolved));
                }
            }
        }

    }

    private Integer resolve(final String name, final String value,
            final List<String> path) {

        // Check for cycles
        if (path.contains(name)) {
            final StringBuilder sb = new StringBuilder();
            final Iterator<String> it = path.iterator();
            while (it.hasNext()) {
                sb.append(it.next() + " > ");
            }
            sb.append(name);
            throw new IllegalStateException("Cycle: " + sb);
        }

        // Analyze
        Integer d = depth.get(name);
        if (d == null) {
            final Set<String> refs = references(value);
            if (refs.isEmpty()) {
                d = 0;
            } else {
                final Iterator<String> it = refs.iterator();
                while (it.hasNext()) {
                    final String refName = it.next();
                    final String refValue = unresolved.get(refName);
                    d = 1 + resolve(refName, refValue, add(path, name));
                }
            }
            depth.put(name, d);
        }
        return d;
    }

    private List<String> add(final List<String> list, final String name) {
        final List<String> newList = new ArrayList<String>(list);
        newList.add(name);
        return newList;
    }

    /**
     * Returns the variable names and how many steps are necessary to resolve
     * all references to other variables.
     * 
     * @return Names and state of all known variables - Never <code>null</code>,
     *         but may be empty.
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
     * @return Referenced variable names - Never <code>null</code>, but may be
     *         empty.
     */
    public static Set<String> references(final String value) {

        final HashSet<String> names = new HashSet<String>();
        if ((value == null) || (value.length() == 0)) {
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

    /**
     * Replaces all variables inside a string with values from a map.
     * 
     * @param str
     *            Text with variables (Format: ${key} ) - May be
     *            <code>null</code> or empty.
     * @param vars
     *            Map with key/values (both of type <code>String</code> - May be
     *            <code>null</code>.
     * 
     * @return String with replaced variables. Unknown variables will remain
     *         unchanged - May be <code>null</code>.
     */
    public static String replaceVars(final String str,
            final Map<String, String> vars) {

        if ((str == null) || (str.length() == 0) || (vars == null)
                || (vars.size() == 0)) {
            return str;
        }

        final StringBuffer sb = new StringBuffer();

        int end = -1;
        int from = 0;
        int start = -1;
        while ((start = str.indexOf("${", from)) > -1) {
            sb.append(str.substring(end + 1, start));
            end = str.indexOf('}', start + 1);
            if (end == -1) {
                // No closing bracket found...
                sb.append(str.substring(start));
                from = str.length();
            } else {
                final String key = str.substring(start + 2, end);
                final String value = (String) vars.get(key);
                if (value == null) {
                    sb.append("${");
                    sb.append(key);
                    sb.append("}");
                } else {
                    sb.append(value);
                }
                from = end + 1;
            }
        }

        sb.append(str.substring(from));

        return sb.toString();

    }

}
