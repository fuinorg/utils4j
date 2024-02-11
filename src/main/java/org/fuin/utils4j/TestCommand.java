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

/**
 * A command used in a test scenario.
 * 
 * @param <CONTEXT>
 *            Type of the context.
 */
public interface TestCommand<CONTEXT> {

    /**
     * Initializes the command before executing it.
     * 
     * @param context
     *            Context with specific information that may be used by the command. Not {@literal null}.
     */
    public void init(CONTEXT context);

    /**
     * Executes the command. Exceptions will be catched used for creating a nice failure description. They are also logged.
     */
    public void execute();

    /**
     * Returns if the command execution was successful. If this method is called before {@link #execute()} was executed, an illegal state
     * exception will be thrown.
     * 
     * @return TRUE if it was successful, else FALSE if it was a failure.
     */
    public boolean isSuccessful();

    /**
     * Returns a description of the failure condition.
     * 
     * @return Expected and current result.
     */
    public String getFailureDescription();

    /**
     * Verifies that the command was successful and throws a runtime exception otherwise.
     */
    public void verify();

}
