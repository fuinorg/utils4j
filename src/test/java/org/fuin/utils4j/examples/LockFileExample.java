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

import org.fuin.utils4j.LockingFailedException;
import org.fuin.utils4j.Utils4J;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

/**
 * Short example locking a file.
 */
// CHECKSTYLE:OFF
public class LockFileExample {

    public static void main(final String[] args) throws IOException, LockingFailedException {

        // Number of tries to lock before throwing an exception.
        final int tryLockMax = 1;

        // Milliseconds to sleep between retries.
        final int tryWaitMillis = 500;

        // Create temp file
        final File file = File.createTempFile("LockFileExample", ".bin");
        final RandomAccessFile raf = new RandomAccessFile(file, "rw");
        try {
            final FileLock lock = Utils4J.lockRandomAccessFile(raf, tryLockMax, tryWaitMillis);
            try {
                System.out.println("LOCKED: " + file);

                // Access the same file a second time
                final RandomAccessFile sameFile = new RandomAccessFile(file, "rw");
                try {
                    try {
                        // Try to lock again... Should fail
                        Utils4J.lockRandomAccessFile(sameFile, tryLockMax, tryWaitMillis);
                    } catch (final LockingFailedException ex) {
                        System.err.println("CANNOT LOCK AGAIN: " + file);
                    }
                } finally {
                    sameFile.close();
                }

            } finally {
                lock.release();
                System.out.println("UNLOCKED: " + file);
            }
        } finally {
            raf.close();
        }

    }

}
// CHECKSTYLE:ON
