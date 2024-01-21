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

import org.jboss.jandex.DotName;
import org.jboss.jandex.Index;
import org.jboss.jandex.Indexer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Utilities related to Jandex.
 */
public final class JandexUtils {

    /**
     * Private default constructor.
     */
    private JandexUtils() {
        throw new UnsupportedOperationException("This utility class is not intended to be instanciated!");
    }

    /**
     * Indexes a single file, except it was already analyzed.
     * 
     * @param indexer
     *            Indexer to use.
     * @param knownFiles
     *            List of files already analyzed. New files will be added within this method.
     * @param classFile
     *            Class file to analyze.
     * 
     * @return TRUE if the file was indexed or FALSE if it was ignored.
     */
    public static boolean indexClassFile(final Indexer indexer, final List<File> knownFiles, final File classFile) {

        if (knownFiles.contains(classFile)) {
            return false;
        }

        knownFiles.add(classFile);
        try (final InputStream in = classFile.toURI().toURL().openStream()) {
            indexer.index(in);
        } catch (final IOException ex) {
            throw new RuntimeException("Error indexing file: " + classFile, ex);
        }
        return true;

    }

    /**
     * Indexes all classes in a directory or it's subdirectories.
     *
     * @param dir
     *            Directory to analyze.
     * @return Index of all classes in the directory.
     */
    public static Index indexDir(final File dir) {
        final Indexer indexer = new Indexer();
        indexDir(indexer, new ArrayList<>(), dir);
        return indexer.complete();
    }

    /**
     * Indexes all classes in a directory or it's subdirectories.
     * 
     * @param indexer
     *            Indexer to use.
     * @param knownFiles
     *            List of files already analyzed. New files will be added within this method.
     * @param dir
     *            Directory to analyze.
     */
    public static void indexDir(final Indexer indexer, final List<File> knownFiles, final File dir) {
        final List<File> classes = Utils4J.pathsFiles(dir.getPath(), Utils4J::classFile);
        for (final File file : classes) {
            indexClassFile(indexer, knownFiles, file);
        }
    }

    /**
     * Indexes a single JAR, except it was already analyzed.
     * 
     * @param indexer
     *            Indexer to use.
     * @param knownFiles
     *            List of files already analyzed. New files will be added within this method.
     * @param jarFile
     *            JAR to analyze.
     * 
     * @return TRUE if the JAR was indexed or FALSE if it was ignored.
     */
    public static boolean indexJar(final Indexer indexer, final List<File> knownFiles, final File jarFile) {

        if (knownFiles.contains(jarFile)) {
            return false;
        }
        knownFiles.add(jarFile);

        try (final JarFile jar = new JarFile(jarFile)) {
            final Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                final JarEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".class")) {
                    try (final InputStream stream = jar.getInputStream(entry)) {
                        indexer.index(stream);
                    } catch (final IOException ex) {
                        throw new RuntimeException("Error indexing " + entry.getName() + " in " + jarFile, ex);
                    }
                }

            }
        } catch (final IOException ex) {
            throw new RuntimeException("Error indexing " + jarFile, ex);
        }

        return true;
    }

    /**
     * Indexes all classes in the classpath (*.jar or *.class).
     * 
     * @param indexer
     *            Indexer to use.
     * @param knownFiles
     *            List of files already analyzed. New files will be added within this method.
     */
    public static void indexClasspath(final Indexer indexer, final List<File> knownFiles) {
        indexFiles(Utils4J.classpathFiles(), indexer, knownFiles);
    }

    /**
     * Indexes all classes (*.jar or *.class).
     * 
     * @param files
     *            Files to use.
     * @param indexer
     *            Indexer to use.
     * @param knownFiles
     *            List of files already analyzed. New files will be added within this method.
     */
    public static void indexFiles(final List<File> files, final Indexer indexer, final List<File> knownFiles) {

        // Variant that works with Maven "exec:java"
        for (final File file : files) {
            if (Utils4J.nonJreJarFile(file)) {
                indexJar(indexer, knownFiles, file);
            } else if (file.isDirectory() && !file.getName().startsWith(".")) {
                indexDir(indexer, knownFiles, file);
            }
        }

        // Variant that works for Maven surefire tests
        for (final File file : Utils4J.classpathFiles(Utils4J::nonJreJarFile)) {
            indexJar(indexer, knownFiles, file);
        }
        for (final File file : Utils4J.classpathFiles(Utils4J::classFile)) {
            indexClassFile(indexer, knownFiles, file);
        }

    }

    /**
     * Loads the class using the current thread's context class loader.
     *
     * @param name Name of the class to load.
     *
     * @return Loaded class.
     */
    public static Class<?> loadClass(DotName name) {
        return Utils4J.loadClass(name.toString());
    }


}
