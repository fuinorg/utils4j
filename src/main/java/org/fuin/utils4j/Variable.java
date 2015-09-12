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

import java.net.URL;

/**
 * Represents a variable definition with name and value (both strings). Hash
 * code and equals are defined based on the name. Either value or URL has has to
 * be defined.
 */
public interface Variable {

    /**
     * Returns the name.
     * 
     * @return Current name - Never <code>null</code> or empty.
     */
    public String getName();

    /**
     * Returns the value.
     * 
     * @return Current value - Never <code>null</code>.
     */
    public String getValue();

    /**
     * Returns the URL.
     * 
     * @return URL - May be <code>null</code>.
     */
    public URL getURL();

    /**
     * Returns the encoding.
     * 
     * @return Encoding - May be <code>null</code>.
     */
    public String getEncoding();
    
    /**
     * Returns the encoding or a default value of 'utf-8'.
     * 
     * @return Encoding - Never <code>null</code>.
     */
    public String getEncodingOrDefault();

}
