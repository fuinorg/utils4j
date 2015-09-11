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
package org.fuin.utils4j.fileprocessor;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Processes one or more files, directories and sub directories.
 */
public final class FileProcessor {

    private final FileHandler handler;

    private final FileOrder order;

    private final boolean sort;

    /**
     * Constructor for unsorted default order.
     * 
     * @param handler
     *            Handler - Cannot be NULL.
     */
    public FileProcessor(final FileHandler handler) {
        this(handler, FileOrder.DEFAULT, false);
    }

    /**
     * Constructor with order but not sorted.
     * 
     * @param handler
     *            Handler - Cannot be NULL.
     * @param order
     *            Order - Cannot be NULL.
     */
    public FileProcessor(final FileHandler handler, final FileOrder order) {
        this(handler, order, false);
    }

    /**
     * Constructor all data.
     * 
     * @param handler
     *            Handler - Cannot be NULL.
     * @param order
     *            Order - Cannot be NULL.
     * @param sort
     *            Sort files or not.
     */
    public FileProcessor(final FileHandler handler, final FileOrder order,
            final boolean sort) {
        super();
        if (handler == null) {
            throw new IllegalArgumentException(
                    "Argument 'handler' cannot be NULL");
        }
        if (order == null) {
            throw new IllegalArgumentException(
                    "Argument 'order' cannot be NULL");
        }
        this.handler = handler;
        this.order = order;
        this.sort = sort;
    }

    /**
     * Processes a file or directory.
     * 
     * @param file
     *            File - Cannot be NULL.
     */
    public final void process(final File file) {
        if (file == null) {
            throw new IllegalArgumentException("Argument 'file' cannot be NULL");
        }
        if (file.isFile()) {
            handler.handleFile(file);
        } else {
            processDir(file);
        }
    }

    /**
     * Processes a directory.
     * 
     * @param dir
     *            Directory to handle.
     * 
     * @return Result of the processing - Either {@link FileHandlerResult#STOP}
     *         or {@link FileHandlerResult#CONTINUE}.
     */
    // CHECKSTYLE:OFF Cyclomatic complexity of 11 is OK here
    private FileHandlerResult processDir(final File dir) {
        // CHECKSTYLE:ON

        final FileHandlerResult dirResult = handler.handleFile(dir);
        if (dirResult == FileHandlerResult.STOP) {
            return FileHandlerResult.STOP;
        }
        if (dirResult == FileHandlerResult.SKIP_ALL) {
            return FileHandlerResult.CONTINUE;
        }

        final File[] files = dir.listFiles();
        if (files != null) {
            final List<File> sortedFiles = asList(files);
            for (int i = 0; i < sortedFiles.size(); i++) {
                final File file = (File) sortedFiles.get(i);
                FileHandlerResult result = FileHandlerResult.CONTINUE;
                if (file.isDirectory()
                        && (dirResult != FileHandlerResult.SKIP_SUBDIRS)) {
                    result = processDir(file);
                } else if (file.isFile()
                        && (dirResult != FileHandlerResult.SKIP_FILES)) {
                    result = handler.handleFile(file);
                }
                if (result == FileHandlerResult.STOP) {
                    return FileHandlerResult.STOP;
                }
                if (result == FileHandlerResult.SKIP_ALL) {
                    return FileHandlerResult.CONTINUE;
                }
            }
        }

        return FileHandlerResult.CONTINUE;

    }

    private List<File> asList(final File[] files) {
        if (order == FileOrder.DEFAULT) {
            return defaultList(files);
        }
        return orderedList(files);
    }

    private List<File> orderedList(final File[] files) {

        // Create separate lists for files and directories
        final List<File> dirList = new ArrayList<>();
        final List<File> fileList = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            final File file = (File) files[i];
            if (file.isDirectory()) {
                dirList.add(file);
            } else {
                fileList.add(file);
            }
        }

        // Sort both lists?
        if (sort) {
            Collections.sort(dirList, new FilenameComparator());
            Collections.sort(fileList, new FilenameComparator());
        }

        // Create result list
        final List<File> list;
        if (order == FileOrder.DIR_FIRST) {
            list = new ArrayList<>(dirList);
            list.addAll(fileList);
        } else {
            list = new ArrayList<>(fileList);
            list.addAll(dirList);
        }
        return list;
    }

    private List<File> defaultList(final File[] files) {
        final List<File> list = new ArrayList<>(files.length);
        for (int i = 0; i < files.length; i++) {
            final File file = (File) files[i];
            list.add(file);
        }
        if (sort) {
            Collections.sort(list, new FilenameComparator());
        }
        return list;
    }

    /**
     * Compare two files only by the name (not including the path).
     */
    private static class FilenameComparator implements Comparator<File> {

        public final int compare(final File f1, final File f2) {
            return f1.getName().compareTo(f2.getName());
        }

    }

}
