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
package org.fuin.utils4j.jaxb;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Converts a string into a CDATA XML and back. CAUTION: Only works together with {@link CDataXmlStreamWriter} because otherwise the CDATA
 * XML value will be escaped.
 */
public final class CDataXmlAdapter extends XmlAdapter<String, String> {

    @Override
    public final String marshal(final String value) throws Exception {
        if (value == null) {
            return null;
        }
        return "<![CDATA[" + value + "]]>";
    }

    @Override
    public final String unmarshal(final String value) throws Exception {
        if (value == null) {
            return null;
        }
        return value;
    }

}
