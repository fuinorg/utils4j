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
package org.fuin.utils4j.examples;

import org.fuin.utils4j.WaitHelper;

import java.util.Arrays;
import java.util.UUID;

/**
 * Short example for using the {@link WaitHelper}.
 */
// CHECKSTYLE:OFF
public class WaitHelperExample {

    /**
     * Example function that loads a customer name, but always throws an exception.
     * 
     * @param id
     *            Unique ID of the customer to load the name for.
     * 
     * @return Customer name.
     * 
     * @throws CustomerNotFoundException
     *             No customer with the given ID was found.
     */
    private static String loadCustomerName(final UUID id) throws CustomerNotFoundException {
        throw new CustomerNotFoundException();
    }

    /**
     * Example function that loads a customer name.
     * 
     * @param id
     *            Unique ID of the customer to load the name for.
     * 
     * @return Customer name.
     * 
     * @throws CustomerNotFoundException
     *             No customer with the given ID was found.
     */
    private static String loadCustomerName2(final UUID id) throws CustomerNotFoundException {
        return "Harry Osborn, Inc";
    }

    /**
     * Executes the example.
     * 
     * @param args
     *            Not used.
     */
    public static void main(String[] args) throws Exception {

        // Customer ID to load a name for
        final UUID customerId = UUID.randomUUID();

        // Wait one second between tries
        final long sleepMillis = 1000;

        // Try 5 times (wait at max 5 seconds)
        final long maxTries = 5;

        // Create helper class
        final WaitHelper waitHelper = new WaitHelper(sleepMillis, maxTries);

        try {

            // Example of waiting for a customer to be found
            waitHelper.waitUntilNoMoreException(() -> {

                // We want to wait some time to see if the
                // CustomerNotFoundException
                // disappears and a customer name is finally loaded
                loadCustomerName(customerId);

            }, Arrays.asList(CustomerNotFoundException.class));

        } catch (final IllegalStateException ex) {
            ex.printStackTrace();
        }

        try {

            // Example of waiting for a customer name
            waitHelper.waitUntilResult(() -> {

                // We want to wait some time to see if the
                // CustomerNotFoundException
                // disappears and a customer name is finally loaded
                return loadCustomerName2(customerId);

            }, Arrays.asList("Peter Parker, Inc"));

        } catch (final IllegalStateException ex) {
            ex.printStackTrace();
        }

    }

    @SuppressWarnings("serial")
    private static class CustomerNotFoundException extends RuntimeException {
    }

}
// CHECKSTYLE:ON
