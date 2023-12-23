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

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link WaitHelper}.
 */
// CHECKSTYLE:OFF
public class WaitHelperTest {

    @Test
    public final void testWaitUntilNoMoreExceptionSUCCESS() {

        // PREPARE
        final WaitHelper testee = new WaitHelper(10, 2);
        final List<Class<? extends Exception>> expectedExceptions = new ArrayList<>();
        expectedExceptions.add(FileNotFoundException.class);
        final AtomicInteger counter = new AtomicInteger(0);

        // TEST
        testee.waitUntilNoMoreException(() -> {
            if (counter.getAndIncrement() < 1) {
                throw new RuntimeException(new FileNotFoundException("dummy" + counter.get()));
            }
        }, expectedExceptions);

    }

    @Test
    public final void testWaitUntilNoMoreExceptionFAILURE() {

        // PREPARE
        final WaitHelper testee = new WaitHelper(10, 2);
        final List<Class<? extends Exception>> expectedExceptions = new ArrayList<>();
        expectedExceptions.add(FileNotFoundException.class);
        final AtomicInteger counter = new AtomicInteger(0);

        // TEST
        assertThatThrownBy(() -> {
            testee.waitUntilNoMoreException(() -> {
                if (counter.getAndIncrement() < 2) {
                    throw new RuntimeException(new FileNotFoundException("dummy" + counter.get()));
                }
            }, expectedExceptions);
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public final void testWaitUntilNoMoreExceptionUnexpectedException() {

        // PREPARE
        final WaitHelper testee = new WaitHelper(10, 2);
        final List<Class<? extends Exception>> expectedExceptions = new ArrayList<>();
        expectedExceptions.add(FileNotFoundException.class);

        // TEST
        assertThatThrownBy(() -> {
            testee.waitUntilNoMoreException(() -> {
                throw new ClassCastException("dummy");
            }, expectedExceptions);
        }).isInstanceOf(ClassCastException.class)
          .hasMessage("dummy");
    }

    @Test
    public final void testWaitUntilResultSUCCESS() throws Exception {

        // PREPARE
        final WaitHelper testee = new WaitHelper(10, 2);
        final List<Integer> expectedValues = new ArrayList<>();
        expectedValues.add(1);
        final AtomicInteger counter = new AtomicInteger(0);

        // TEST
        testee.waitUntilResult(() -> {
            if (counter.getAndIncrement() < 1) {
                return null;
            }
            return 1;
        }, expectedValues);

    }

    @Test
    public final void testWaitUntilResultFAILURE() throws Exception {

        // PREPARE
        final WaitHelper testee = new WaitHelper(10, 2);
        final List<Integer> expectedValues = new ArrayList<>();
        expectedValues.add(1);

        // TEST
        assertThatThrownBy(() -> {
            testee.waitUntilResult(() -> {
                return null;
            }, expectedValues);
        }).isInstanceOf(IllegalStateException.class);

    }

}
