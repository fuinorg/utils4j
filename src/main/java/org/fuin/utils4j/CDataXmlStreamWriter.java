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

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * XML stream writer that does not escape the content of a CDATA section. This
 * is meant to be used with JAXB to serialize a string field to a CDATA section.
 * This field is annotated with {@link CDataXmlAdapter}.
 */
public final class CDataXmlStreamWriter extends XMLStreamWriterAdapter {

    /**
     * Constructor with delegate.
     * 
     * @param delegate
     *            All method calls are delegated to this writer.
     */
    public CDataXmlStreamWriter(final XMLStreamWriter delegate) {
	super(delegate);
    }

    @Override
    public final void writeCharacters(final String text)
	    throws XMLStreamException {
	writeText(text);
    }

    @Override
    public final void writeCharacters(final char[] text, final int start,
	    final int len) throws XMLStreamException {
	writeText(new String(text, start, len));
    }

    private void writeText(final String text) throws XMLStreamException {
	if (text.startsWith("<![CDATA[") && text.endsWith("]]>")) {
	    final String str = text.substring(9, text.length() - 3);
	    super.writeCData(str);
	} else {
	    super.writeCharacters(text);
	}
    }

}
