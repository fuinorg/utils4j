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

import org.fuin.utils4j.VariableResolver;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Example how to use the variable resolver.
 */
// CHECKSTYLE:OFF
public class VariableResolverExample {

    /**
     * Executes the example.
     * 
     * @param args
     *            Not used.
     */
    public static void main(String[] args) {

        // Define some variables
        Map<String, String> vars = new HashMap<>();
        vars.put("a", "1");
        vars.put("b", "${a}/2");
        vars.put("c", "${b}/3");
        vars.put("d", "${a}/${e}"); // Does not exist

        // Create a resolver
        VariableResolver resolver = new VariableResolver(vars);

        // Get the variables with all references replaced
        Map<String, String> resolved = resolver.getResolved();
        Iterator<String> nameIt = resolved.keySet().iterator();
        while (nameIt.hasNext()) {
            String name = nameIt.next();
            System.out.println(name + "=" + resolved.get(name));
        }

        // Output:
        // a=1
        // b=1/2
        // c=1/2/3
        // d=1/${e}

    }

}
// CHECKSTYLE:ON
