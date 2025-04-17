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
import java.util.Arrays;
import java.util.List;

/**
 * Combines multiple test commands into one.
 * 
 * @param <CONTEXT>
 *            Type of the test context.
 */
public final class MultipleCommands<CONTEXT> implements TestCommand<CONTEXT> {

    private final List<TestCommand<CONTEXT>> commands;

    /**
     * Default constructor.
     */
    public MultipleCommands() {
        super();
        this.commands = new ArrayList<>();
    }

    /**
     * Constructor with command array.
     * 
     * @param commands
     *            One or more commands to execute. Not {@literal null}.
     */
    @SafeVarargs
    public MultipleCommands(final TestCommand<CONTEXT>... commands) {
        this(Arrays.asList(commands));
    }

    /**
     * Constructor with command list.
     * 
     * @param commands
     *            List of commands to execute. Not {@literal null}.
     */
    public MultipleCommands(final List<? extends TestCommand<CONTEXT>> commands) {
        super();
        Utils4J.checkNotNull("commands", commands);
        this.commands = new ArrayList<>(commands);
    }

    /**
     * Adds a new command.
     * 
     * @param command
     *            Command to add. Not {@literal null}.
     */
    public void add(final TestCommand<CONTEXT> command) {
        this.commands.add(command);
    }

    @Override
    public void init(final CONTEXT context) {
        for (final TestCommand<CONTEXT> command : commands) {
            command.init(context);
        }
    }

    @Override
    public final void execute() {
        for (final TestCommand<CONTEXT> command : commands) {
            command.execute();
        }
    }

    @Override
    public final boolean isSuccessful() {
        for (final TestCommand<CONTEXT> command : commands) {
            if (!command.isSuccessful()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public final String getFailureDescription() {
        final StringBuilder sb = new StringBuilder();
        for (final TestCommand<CONTEXT> command : commands) {
            if (!command.isSuccessful()) {
                sb.append(command.getFailureDescription());
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public final void verify() {
        if (!isSuccessful()) {
            throw new RuntimeException("There was at least one failure:\n" + getFailureDescription());
        }
    }

}
