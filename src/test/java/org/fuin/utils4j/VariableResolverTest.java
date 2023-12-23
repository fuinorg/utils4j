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
import static org.assertj.core.data.MapEntry.entry;
import static org.fuin.utils4j.VariableResolver.references;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Test for {@link VariableResolver}.
 */
public class VariableResolverTest {

    // CHECKSTYLE:OFF

    @Test
    public void testNullConstruction() {

        // PREPARE & TEST
        final VariableResolver testee = new VariableResolver(null);

        // VERIFY
        assertThat(testee.getDepth()).isEmpty();
        assertThat(testee.getResolved()).isEmpty();
        assertThat(testee.getUnresolved()).isEmpty();

    }

    @Test
    public void testEmptyConstruction() {

        // PREPARE & TEST
        final VariableResolver testee = new VariableResolver(new HashMap<String, String>());

        // VERIFY
        assertThat(testee.getDepth()).isEmpty();
        assertThat(testee.getResolved()).isEmpty();
        assertThat(testee.getUnresolved()).isEmpty();

    }

    @Test
    public void testOneLevel() {

        // PREPARE
        final Map<String, String> vars = new HashMap<>();
        vars.put("a", "1");
        vars.put("b", "2");
        vars.put("c", "3");

        // TEST
        final VariableResolver testee = new VariableResolver(vars);

        // VERIFY
        assertThat(testee.getDepth()).contains(entry("a", 0), entry("b", 0), entry("c", 0));
        assertThat(testee.getResolved()).contains(entry("a", "1"), entry("b", "2"), entry("c", "3"));
        assertThat(testee.getUnresolved()).contains(entry("a", "1"), entry("b", "2"), entry("c", "3"));

    }

    @Test
    public void testTwoLevels() {

        // PREPARE
        final Map<String, String> vars = new HashMap<>();
        vars.put("a", "a${b}");
        vars.put("b", "2");

        // TEST
        final VariableResolver testee = new VariableResolver(vars);

        // VERIFY
        assertThat(testee.getDepth()).contains(entry("a", 1), entry("b", 0));
        assertThat(testee.getResolved()).contains(entry("a", "a2"), entry("b", "2"));
        assertThat(testee.getUnresolved()).contains(entry("a", "a${b}"), entry("b", "2"));

    }

    @Test
    public void testThreeLevels() {

        // PREPARE
        final Map<String, String> vars = new HashMap<>();
        vars.put("a", "1${b}");
        vars.put("b", "2${c}");
        vars.put("c", "3");

        // TEST
        final VariableResolver testee = new VariableResolver(vars);

        // VERIFY
        assertThat(testee.getDepth()).contains(entry("a", 2), entry("b", 1), entry("c", 0));
        assertThat(testee.getResolved()).contains(entry("a", "123"), entry("b", "23"), entry("c", "3"));
        assertThat(testee.getUnresolved()).contains(entry("a", "1${b}"), entry("b", "2${c}"), entry("c", "3"));

    }

    @Test
    public void testFourLevels() {

        // PREPARE
        final Map<String, String> vars = new HashMap<>();
        vars.put("a", "1${b}");
        vars.put("b", "2${c}");
        vars.put("c", "3${d}");
        vars.put("d", "4");

        // TEST
        final VariableResolver testee = new VariableResolver(vars);

        // VERIFY
        assertThat(testee.getDepth()).contains(entry("a", 3), entry("b", 2), entry("c", 1), entry("d", 0));
        assertThat(testee.getResolved()).contains(entry("a", "1234"), entry("b", "234"), entry("c", "34"), entry("d", "4"));
        assertThat(testee.getUnresolved()).contains(entry("a", "1${b}"), entry("b", "2${c}"), entry("c", "3${d}"), entry("d", "4"));

    }

    @Test
    public void testCycleOneLevel() {

        // PREPARE
        final Map<String, String> vars = new HashMap<>();
        vars.put("a", "${b}");
        vars.put("b", "${a}");

        // TEST
        try {
            new VariableResolver(vars);
        } catch (final IllegalStateException ex) {
            assertThat(ex.getMessage()).isEqualTo("Cycle: a > b > a");
        }

    }

    @Test
    public void testCycleTwoLevels() {

        // PREPARE
        final Map<String, String> vars = new HashMap<>();
        vars.put("a", "${b}");
        vars.put("b", "${c}");
        vars.put("c", "${a}");

        // TEST
        try {
            new VariableResolver(vars);
        } catch (final IllegalStateException ex) {
            assertThat(ex.getMessage()).isEqualTo("Cycle: a > b > c > a");
        }

    }

    @Test
    public void testUnresolved() {

        // PREPARE
        final Map<String, String> vars = new HashMap<>();
        vars.put("a", "1${b}");
        vars.put("b", "${c}");

        // TEST
        final VariableResolver testee = new VariableResolver(vars);

        // VERIFY
        assertThat(testee.getDepth()).contains(entry("a", 2), entry("b", 1));
        assertThat(testee.getResolved()).contains(entry("a", "1${c}"), entry("b", "${c}"));
        assertThat(testee.getUnresolved()).contains(entry("a", "1${b}"), entry("b", "${c}"));

    }

    @Test
    public void testReferences() {

        assertThat(references(null)).isEmpty();
        assertThat(references("")).isEmpty();
        assertThat(references("a")).isEmpty();
        assertThat(references("a b c")).isEmpty();
        assertThat(references(" a $b c ")).isEmpty();
        assertThat(references(" a ${b c ")).isEmpty(); // Non closing bracket
        assertThat(references("${a}")).contains("a");
        assertThat(references("a ${b} ${c}")).contains("b", "c");
        assertThat(references("${a}${b}")).contains("a", "b");

    }

    // CHECKSTYLE:ON

}
