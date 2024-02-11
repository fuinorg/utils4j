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

import static org.assertj.core.api.Assertions.assertThat;

//CHECKSTYLE:OFF Test code
public class MultipleCommandsTest {

    @Test
    public void testCreate() {

        final MyCmd cmd1 = new MyCmd(true, null);
        final MultipleCommands<String> testee1 = new MultipleCommands<>(cmd1);
        testee1.init("Hello1");
        testee1.execute();
        
        assertThat(testee1.getFailureDescription()).isEmpty();
        assertThat(testee1.isSuccessful()).isTrue();
        assertThat(cmd1.getContext()).isEqualTo("Hello1");
        assertThat(cmd1.isExecuted()).isTrue();;

        final MyCmd cmd2 = new MyCmd(false, "Oops");
        final MultipleCommands<String> testee2 = new MultipleCommands<>(cmd1, cmd2);
        testee2.init("Hello2");
        testee2.execute();

        assertThat(testee2.isSuccessful()).isFalse();
        assertThat(testee2.getFailureDescription()).isEqualTo("Oops\n");
        assertThat(cmd1.getContext()).isEqualTo("Hello2");
        assertThat(cmd1.isExecuted()).isTrue();;
        assertThat(cmd2.getContext()).isEqualTo("Hello2");
        assertThat(cmd2.isExecuted()).isTrue();;

    }

    public static final class MyCmd implements TestCommand<String> {

        private String context;

        private boolean executed;

        private boolean successful;

        private String failureDescription;

        public MyCmd(boolean successful, String failureDescription) {
            super();
            this.successful = successful;
            this.failureDescription = failureDescription;
        }

        @Override
        public void init(String context) {
            this.context = context;
        }

        @Override
        public void execute() {
            executed = true;
        }

        @Override
        public boolean isSuccessful() {
            return successful;
        }

        @Override
        public String getFailureDescription() {
            return failureDescription;
        }

        public String getContext() {
            return context;
        }

        public boolean isExecuted() {
            return executed;
        }

        @Override
        public void verify() {
            // DO nothing
        }

    }

}
// CHECKSTYLE:ON
