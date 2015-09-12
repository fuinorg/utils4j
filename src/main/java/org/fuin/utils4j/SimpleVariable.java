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

import static org.fuin.utils4j.Utils4J.readAsString;
import static org.fuin.utils4j.Utils4J.url;

import java.net.URL;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a simple implementation of the {@link Variable} interface.
 */
@XmlRootElement(name = "variable")
@XmlAccessorType(XmlAccessType.FIELD)
public final class SimpleVariable implements Variable {

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "value")
    private String value;

    @XmlAttribute(name = "url")
    private String urlStr;

    @XmlAttribute(name = "encoding")
    private String encoding;

    private transient URL url;

    /**
     * Default constructor for JAXB.
     */
    protected SimpleVariable() {
        super();
    }

    /**
     * Constructor with value.
     * 
     * @param name
     *            Unique name - May not be <code>null</code> or empty.
     * @param value
     *            Variable value - May not be <code>null</code>.
     */
    public SimpleVariable(final String name, final String value) {
        super();
        Utils4J.checkNotNull("name", name);
        Utils4J.checkNotEmpty("name", name);
        Utils4J.checkNotNull("value", value);
        this.name = name;
        this.value = value;
    }

    /**
     * Constructor with URL.
     * 
     * @param name
     *            Unique name - May not be <code>null</code> or empty.
     * @param url
     *            URL that references a text resource - May not be <code>null</code>.
     */
    public SimpleVariable(final String name, final URL url) {
        this(name, url, null);
    }

    /**
     * Constructor with URL.
     * 
     * @param name
     *            Unique name - May not be <code>null</code> or empty.
     * @param url
     *            URL that references a text resource - May not be <code>null</code>.
     * @param encoding
     *            Encoding of the text resource the URL points to - May be <code>null</code> but not empty.
     */
    public SimpleVariable(final String name, final URL url, final String encoding) {
        super();
        Utils4J.checkNotNull("name", name);
        Utils4J.checkNotEmpty("name", name);
        Utils4J.checkNotNull("url", url);
        if (encoding != null) {
            Utils4J.checkNotEmpty("encoding", encoding);
        }
        this.name = name;
        this.urlStr = url.toString();
        this.encoding = encoding;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final String getValue() {
        if ((value == null) && (urlStr != null)) {
            value = readAsString(getURL(), getEncodingOrDefault(), 1024);
            if (value == null) {
                throw new IllegalStateException("Reading the URL returned null: " + urlStr);
            }
        }
        return value;
    }

    @Override
    public final URL getURL() {
        if (url == null) {
            try {
                url = url(urlStr);
            } catch (final IllegalArgumentException ex) {
                throw new RuntimeException("Variable '" + name + "' has a wrong URL", ex);
            }
        }
        return url;
    }

    @Override
    public final String getEncoding() {
        return encoding;
    }

    @Override
    public final String getEncodingOrDefault() {
        if (encoding == null) {
            return "utf-8";
        }
        return encoding;
    }

    @Override
    public final int hashCode() {
        return name.hashCode();
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SimpleVariable other = (SimpleVariable) obj;
        return name.equals(other.name);
    }

    @Override
    public final String toString() {
        return name + "=" + value;
    }

}
