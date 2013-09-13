/**
 * Copyright (C) 2009 Future Invent Informationsmanagement GmbH. All rights
 * reserved. <http://www.fuin.org/>
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
 * along with this library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.fuin.utils4j.fileprocessor;

import java.io.File;

/**
 * Handles a file or directory and decides on how to continue with processing.
 */
public interface FileHandler {

    /**
     * Handles the file or directory.
     * 
     * @param file
     *            File to do something with.
     * 
     * @return Instruction on how to continue processing.
     */
    public FileHandlerResult handleFile(File file);

}
