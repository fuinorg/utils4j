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

import org.fuin.utils4j.Utils4J;

import java.io.File;
import java.io.IOException;

/**
 * Short example of listing all JARs and classes in the classpath.
 */
// CHECKSTYLE:OFF
public class FindJarsAndClassesInClasspath {

    public static void main(String[] args) throws IOException {

        // List CLASS files
        System.out.println("CLASSES:");
        for (final File file : Utils4J.classpathFiles(Utils4J::classFile)) {
            System.out.println(file);
        }

        // List JAR files that are not located in the JRE directory
        System.out.println("NON-JRE JAR:");
        for (final File file : Utils4J.classpathFiles(Utils4J::nonJreJarFile)) {
            System.out.println(file);
        }

        // List JAR files that are located in the JRE directory
        System.out.println("JRE JAR:");
        for (final File file : Utils4J.classpathFiles(Utils4J::jreJarFile)) {
            System.out.println(file);
        }

        // List JAR files that are located in the boot path of the JRE
        System.out.println("BOOT JAR:");
        for (final File file : Utils4J.pathsFiles(System.getProperty("sun.boot.class.path"), Utils4J::jreJarFile)) {
            System.out.println(file);
        }

        // List JAR files that are located in the extension directories of the
        // JRE
        System.out.println("EXT JAR:");
        for (final File file : Utils4J.pathsFiles(System.getProperty("java.ext.dirs"), Utils4J::jreJarFile)) {
            System.out.println(file);
        }

    }

}
