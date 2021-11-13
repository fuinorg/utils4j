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

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.stream.XMLStreamWriter;

import org.fuin.utils4j.Utils4J;

/**
 * JAXB releated functions.
 */
public final class JaxbUtils {

    private static final String ERROR_MARSHALLING_TEST_DATA = "Error marshalling test data";

    /** Standard XML prefix with UTF-8 encoding. */
    public static final String XML_PREFIX = "<?xml version=\"1.0\" " + "encoding=\"UTF-8\" standalone=\"yes\"?>";

    private JaxbUtils() {
        throw new UnsupportedOperationException("It's not allowed to create an instance of a utility class");
    }

    /**
     * Marshals the given data. A <code>null</code> data argument returns <code>null</code>.
     * 
     * @param data
     *            Data to serialize or <code>null</code>.
     * @param classesToBeBound
     *            List of java classes to be recognized by the {@link JAXBContext} - Cannot be <code>null</code>.
     * 
     * @return XML data or <code>null</code>.
     * 
     * @param <T>
     *            Type of the data.
     */
    public static <T> String marshal(final T data, final Class<?>... classesToBeBound) {
        return marshal(data, null, classesToBeBound);
    }

    /**
     * Marshals the given data. A <code>null</code> data argument returns <code>null</code>.
     * 
     * @param data
     *            Data to serialize or <code>null</code>.
     * @param adapters
     *            Adapters to associate with the marshaller or <code>null</code> .
     * @param classesToBeBound
     *            List of java classes to be recognized by the {@link JAXBContext} - Cannot be <code>null</code>.
     * 
     * @return XML data or <code>null</code>.
     * 
     * @param <T>
     *            Type of the data.
     */
    public static <T> String marshal(final T data, final XmlAdapter<?, ?>[] adapters, final Class<?>... classesToBeBound) {
        if (data == null) {
            return null;
        }
        try {
            final JAXBContext ctx = JAXBContext.newInstance(classesToBeBound);
            return marshal(ctx, data, adapters);
        } catch (final JAXBException ex) {
            throw new RuntimeException(ERROR_MARSHALLING_TEST_DATA, ex);
        }
    }

    /**
     * Marshals the given data using a given context. A <code>null</code> data argument returns <code>null</code>.
     * 
     * @param ctx
     *            Context to use - Cannot be <code>null</code>.
     * @param data
     *            Data to serialize or <code>null</code>.
     * 
     * @return XML data or <code>null</code>.
     * 
     * @param <T>
     *            Type of the data.
     */
    public static <T> String marshal(final JAXBContext ctx, final T data) {
        return marshal(ctx, data, null);
    }

    /**
     * Marshals the given data using a given context. A <code>null</code> data argument returns <code>null</code>.
     * 
     * @param ctx
     *            Context to use - Cannot be <code>null</code>.
     * @param data
     *            Data to serialize or <code>null</code>.
     * @param adapters
     *            Adapters to associate with the marshaller or <code>null</code> .
     * 
     * @return XML data or <code>null</code>.
     * 
     * @param <T>
     *            Type of the data.
     */
    public static <T> String marshal(final JAXBContext ctx, final T data, final XmlAdapter<?, ?>[] adapters) {
        if (data == null) {
            return null;
        }
        final StringWriter writer = new StringWriter();
        marshal(ctx, data, adapters, writer);
        return writer.toString();
    }

    /**
     * Marshals the given data using a given context. A <code>null</code> data argument returns <code>null</code>.
     * 
     * @param ctx
     *            Context to use - Cannot be <code>null</code>.
     * @param data
     *            Data to serialize or <code>null</code>.
     * @param adapters
     *            Adapters to associate with the marshaller or <code>null</code> .
     * @param writer
     *            Writer to use.
     * 
     * @param <T>
     *            Type of the data to write.
     */
    public static <T> void marshal(final JAXBContext ctx, final T data, final XmlAdapter<?, ?>[] adapters, final Writer writer) {
        if (data == null) {
            return;
        }
        try {
            final Marshaller marshaller = ctx.createMarshaller();
            if (adapters != null) {
                for (final XmlAdapter<?, ?> adapter : adapters) {
                    marshaller.setAdapter(adapter);
                }
            }
            marshaller.marshal(data, writer);
        } catch (final JAXBException ex) {
            throw new RuntimeException(ERROR_MARSHALLING_TEST_DATA, ex);
        }
    }

    /**
     * Marshals the given data using a given context. A <code>null</code> data argument returns <code>null</code>.
     * 
     * @param ctx
     *            Context to use - Cannot be <code>null</code>.
     * @param data
     *            Data to serialize or <code>null</code>.
     * @param adapters
     *            Adapters to associate with the marshaller or <code>null</code> .
     * @param writer
     *            Writer to use.
     * 
     * @param <T>
     *            Type of the data to write.
     */
    public static <T> void marshal(final JAXBContext ctx, final T data, final XmlAdapter<?, ?>[] adapters, final XMLStreamWriter writer) {
        if (data == null) {
            return;
        }
        try {
            final Marshaller marshaller = ctx.createMarshaller();
            if (adapters != null) {
                for (final XmlAdapter<?, ?> adapter : adapters) {
                    marshaller.setAdapter(adapter);
                }
            }
            marshaller.marshal(data, writer);
        } catch (final JAXBException ex) {
            throw new RuntimeException(ERROR_MARSHALLING_TEST_DATA, ex);
        }
    }

    /**
     * Unmarshals the given data. A <code>null</code> XML data argument returns <code>null</code>.
     * 
     * @param xmlData
     *            XML data or <code>null</code>.
     * @param classesToBeBound
     *            List of java classes to be recognized by the {@link JAXBContext} - Cannot be <code>null</code>.
     * 
     * @return Data or <code>null</code>.
     * 
     * @param <T>
     *            Type of the expected data.
     * 
     * @deprecated Use method {@link #unmarshal(Unmarshaller, String)} together with {@link UnmarshallerBuilder} instead
     */
    public static <T> T unmarshal(final String xmlData, final Class<?>... classesToBeBound) {
        final Unmarshaller unmarshaller = new UnmarshallerBuilder().addClassesToBeBound(classesToBeBound).build();
        return unmarshal(unmarshaller, xmlData);
    }

    /**
     * Unmarshals the given data. A <code>null</code> XML data argument returns <code>null</code>.
     * 
     * @param xmlData
     *            XML data or <code>null</code>.
     * @param adapters
     *            Adapters to associate with the unmarshaller or <code>null</code>.
     * @param classesToBeBound
     *            List of java classes to be recognized by the {@link JAXBContext} - Cannot be <code>null</code>.
     * 
     * @return Data or <code>null</code>.
     * 
     * @param <T>
     *            Type of the expected data.
     * 
     * @deprecated Use method {@link #unmarshal(Unmarshaller, String)} together with {@link UnmarshallerBuilder} instead
     */
    public static <T> T unmarshal(final String xmlData, final XmlAdapter<?, ?>[] adapters, final Class<?>... classesToBeBound) {
        final Unmarshaller unmarshaller = new UnmarshallerBuilder().addClassesToBeBound(classesToBeBound).addAdapters(adapters).build();
        return unmarshal(unmarshaller, xmlData);
    }

    /**
     * Unmarshals the given data using a given context. A <code>null</code> XML data argument returns <code>null</code>.
     * 
     * @param ctx
     *            Context to use - Cannot be <code>null</code>.
     * @param xmlData
     *            XML data or <code>null</code>.
     * @param adapters
     *            Adapters to associate with the unmarshaller or <code>null</code>.
     * 
     * @return Data or <code>null</code>.
     * 
     * @param <T>
     *            Type of the expected data.
     * 
     * @deprecated Use method {@link #unmarshal(Unmarshaller, String)} together with {@link UnmarshallerBuilder} instead
     */
    public static <T> T unmarshal(final JAXBContext ctx, final String xmlData, final XmlAdapter<?, ?>[] adapters) {
        final Unmarshaller unmarshaller = new UnmarshallerBuilder().withContext(ctx).addAdapters(adapters).build();
        return unmarshal(unmarshaller, xmlData);
    }

    /**
     * Unmarshals the given data using a given context. A <code>null</code> XML data argument returns <code>null</code>.
     * 
     * @param ctx
     *            Context to use - Cannot be <code>null</code>.
     * @param reader
     *            Reader with XML data.
     * @param adapters
     *            Adapters to associate with the unmarshaller or <code>null</code>.
     * 
     * @return Data or <code>null</code>.
     * 
     * @param <T>
     *            Type of the expected data.
     * 
     * @deprecated Use method {@link #unmarshal(Unmarshaller, Reader)} together with {@link UnmarshallerBuilder} instead
     */
    public static <T> T unmarshal(final JAXBContext ctx, final Reader reader, final XmlAdapter<?, ?>[] adapters) {
        final Unmarshaller unmarshaller = new UnmarshallerBuilder().withContext(ctx).addAdapters(adapters).build();
        return unmarshal(unmarshaller, reader);
    }

    /**
     * Convenience method to unmarshal an object from a string.
     * 
     * @param unmarshaller
     *            Unmarshaller to use.
     * @param xml
     *            XML source to parse or <code>null</code>.
     * 
     * @return Unmarshalled object or <code>null</code> (in case the provided XML source argument was null).
     * 
     * @param <T>
     *            Type returned.
     */
    @SuppressWarnings("unchecked")
    public static <T> T unmarshal(final Unmarshaller unmarshaller, final String xml) {
        Utils4J.checkNotNull("unmarshaller", unmarshaller);
        if (xml == null) {
            return null;
        }
        try {
            return (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (final JAXBException ex) {
            throw new RuntimeException("Error unmarshalling from XML: " + xml, ex);
        }
    }

    /**
     * Convenience method to unmarshal an object from a reader.
     * 
     * @param unmarshaller
     *            Unmarshaller to use.
     * @param reader
     *            Reader with XML source to parse.
     * 
     * @return Unmarshalled object.
     * 
     * @param <T>
     *            Type returned.
     */
    @SuppressWarnings("unchecked")
    public static <T> T unmarshal(final Unmarshaller unmarshaller, final Reader reader) {
        Utils4J.checkNotNull("unmarshaller", unmarshaller);
        Utils4J.checkNotNull("reader", reader);
        try {
            return (T) unmarshaller.unmarshal(reader);
        } catch (final JAXBException ex) {
            throw new RuntimeException("Error unmarshalling from reader", ex);
        }
    }

    /**
     * Convenience method to unmarshal an object from a file.
     * 
     * @param unmarshaller
     *            Unmarshaller to use.
     * @param file
     *            File with XML source to parse. Cannot be <code>null</code> and must be an existing file.
     * 
     * @return Unmarshalled object.
     * 
     * @param <T>
     *            Type returned.
     */
    @SuppressWarnings("unchecked")
    public static <T> T unmarshal(final Unmarshaller unmarshaller, final File file) {
        Utils4J.checkNotNull("unmarshaller", unmarshaller);
        Utils4J.checkNotNull("file", file);
        Utils4J.checkValidFile(file);
        try (final FileReader reader = new FileReader(file)) {
            return (T) unmarshaller.unmarshal(reader);
        } catch (final JAXBException | IOException ex) {
            throw new RuntimeException("Error unmarshalling from file: " + file, ex);
        }
    }

}

