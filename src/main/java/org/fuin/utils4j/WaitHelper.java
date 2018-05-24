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
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Class that supports waiting for some condition.
 */
public final class WaitHelper {

    private final long sleepMillis;

    private final long maxTries;

    /**
     * Constructor with mandatory data.
     * 
     * @param sleepMillis
     *            Number of milliseconds to sleep between tries.
     * @param maxTries
     *            Number of retries before waiting terminates.
     */
    public WaitHelper(final long sleepMillis, final long maxTries) {
        super();
        this.sleepMillis = sleepMillis;
        this.maxTries = maxTries;
    }

    /**
     * Wait until no more expected exception is thrown or the number of wait cycles has been exceeded.
     * 
     * @param runnable
     *            Code to run.
     * @param expectedExceptions
     *            List of expected exceptions.
     */
    public void waitUntilNoMoreException(final Runnable runnable, final List<Class<? extends Exception>> expectedExceptions) {

        final List<RuntimeException> actualExceptions = new ArrayList<>();
        int tries = 0;
        while (tries < maxTries) {
            try {
                runnable.run();
                return;
            } catch (final RuntimeException ex) {
                if (!Utils4J.expectedException(ex, expectedExceptions) && !Utils4J.expectedCause(ex, expectedExceptions)) {
                    throw ex;
                }
                actualExceptions.add(ex);
                tries++;
                Utils4J.sleep(sleepMillis);
            }
        }
        throw new IllegalStateException("Waited too long for execution without exception. Expected exceptions: " + expectedExceptions
                + ", Actual exceptions: " + actualExceptions);

    }

    /**
     * Wait until one of the expected values was returned or the number of wait cycles has been exceeded. Throws
     * {@link IllegalStateException} if the timeout is reached.
     * 
     * @param function
     *            Function to read the value from.
     * @param expectedValues
     *            List of values that are acceptable.
     * 
     * @return Result.
     * 
     * @throws Exception
     *             The function raised an exception.
     * 
     * @param <T>
     *            Expected return type that must be an object that support equals/hasCode that is not the {@link Object}'s default method.
     */
    public <T> T waitUntilResult(final Callable<T> function, final List<T> expectedValues) throws Exception {

        final List<T> actualResults = new ArrayList<>();
        int tries = 0;
        while (tries < maxTries) {
            final T result = function.call();
            if (expectedValues.contains(result)) {
                return result;
            }
            actualResults.add(result);
            tries++;
            Utils4J.sleep(sleepMillis);
        }
        throw new IllegalStateException(
                "Waited too long for one of the expected results: " + expectedValues + ", Actual results: " + actualResults);

    }

}
