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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.fuin.utils4j.jaxb.JaxbUtils;
import org.fuin.utils4j.jaxb.UnmarshallerBuilder;
import org.junit.Test;
import org.xml.sax.SAXParseException;

/**
 * Test for the {@link UnmarshallerBuilder} class.
 */
public class UnmarshallerBuilderTest {

    @Test
    public void testWithContext() throws JAXBException {

        // PREPARE
        final JAXBContext ctx = JAXBContext.newInstance(MyClass.class);

        // TEST
        final Unmarshaller unmarshaller = new UnmarshallerBuilder().withContext(ctx).addAdapters(new MyId.Adapter()).build();

        // VERIFY
        final MyClass result = JaxbUtils.unmarshal(unmarshaller, getValidXml());
        assertThat(result.getId()).isEqualTo(new MyId(1));

    }

    @Test
    public void testWithClassesToBeBound() throws JAXBException {

        // PREPARE
        final Class<?>[] classesToBeBound = new Class[] { MyClass.class };

        // TEST
        final List<XmlAdapter<?, ?>> adapters = new ArrayList<>();
        adapters.add(new MyId.Adapter());
        final Unmarshaller unmarshaller = new UnmarshallerBuilder().addClassesToBeBound(classesToBeBound).addAdapters(adapters).build();

        // VERIFY
        final MyClass result = JaxbUtils.unmarshal(unmarshaller, getValidXml());
        assertThat(result.getId()).isEqualTo(new MyId(1));

    }

    @Test
    public void testClasspathSchema() throws JAXBException {

        // PREPARE
        final List<Class<?>> classesToBeBound = new ArrayList<>();
        classesToBeBound.add(MyClass.class);
        final String xsd = "/org/fuin/utils4j/test-schema.xsd";
        final List<String> schemas = new ArrayList<>();
        schemas.add(xsd);

        // TEST
        final Unmarshaller unmarshaller = new UnmarshallerBuilder().addClassesToBeBound(classesToBeBound)
                .addClasspathSchemas("/org/fuin/utils4j/test-schema.xsd").addClasspathSchemas(schemas) // Second add does not harm anything
                .addAdapter(new MyId.Adapter()).build();

        // VERIFY
        try {
            JaxbUtils.unmarshal(unmarshaller, getInvalidXml());
            fail("Expected exception");
        } catch (final RuntimeException ex) {
            assertThat(ex.getCause()).isInstanceOf(UnmarshalException.class);
            assertThat(ex.getCause().getCause()).isInstanceOf(SAXParseException.class);
            assertThat(ex.getCause().getCause().getMessage()).contains("Attribute 'name' must appear on element 'my-class'");
        }

    }

    @Test
    public void testAddSchemas() throws JAXBException {

        // PREPARE
        final JAXBContext ctx = JAXBContext.newInstance(MyClass.class);
        final StreamSource xsd = new StreamSource(getClass().getResourceAsStream("/org/fuin/utils4j/test-schema.xsd"));
        final List<Source> schemas = new ArrayList<>();
        schemas.add(xsd);

        // TEST
        final Unmarshaller unmarshaller = new UnmarshallerBuilder().withContext(ctx).addAdapters(new MyId.Adapter()).addSchemas(xsd)
                .addSchemas(schemas) // Second add does not harm anything
                .build();

        // VERIFY
        try {
            JaxbUtils.unmarshal(unmarshaller, getInvalidXml());
            fail("Expected exception");
        } catch (final RuntimeException ex) {
            assertThat(ex.getCause()).isInstanceOf(UnmarshalException.class);
            assertThat(ex.getCause().getCause()).isInstanceOf(SAXParseException.class);
            assertThat(ex.getCause().getCause().getMessage()).contains("Attribute 'name' must appear on element 'my-class'");
        }

    }

    @Test
    public void testClassesAndContextFailure() throws JAXBException {
        try {
            new UnmarshallerBuilder().withContext(JAXBContext.newInstance(MyClass.class)).addClassToBeBound(MyClass.class).build();
            fail("Expected exception");
        } catch (final IllegalStateException ex) {
            assertThat(ex.getMessage()).isEqualTo(
                    "A JAXBContext (ctx) and a list of classes (classesToBeBound) was provided. You can only use one of them, not both.");
        }
    }

    @Test
    public void testNoClassesAndNoContext() throws JAXBException {
        try {
            new UnmarshallerBuilder().build();
            fail("Expected exception");
        } catch (final IllegalStateException ex) {
            assertThat(ex.getMessage()).isEqualTo("Either the JAXBContext (ctx) or a list of classes (classesToBeBound) must be provided");
        }
    }

    @Test
    public void testAddPropertyOK() {
        new UnmarshallerBuilder().addClassToBeBound(MyClass.class).addProperty("com.sun.xml.bind.ObjectFactory", "a.b.c.D").build();
    }

    @Test
    public void testAddPropertyFailure() {
        try {
            new UnmarshallerBuilder().addClassToBeBound(MyClass.class).addProperty("bla", "blub").build();
            fail("Expected exception");
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage()).isEqualTo("Failed to set property 'bla' to: blub");
        }
    }

    @Test
    public void testWithEventHandler() throws JAXBException {

        // PREPARE
        final JAXBContext ctx = JAXBContext.newInstance(MyClass.class);

        // TEST
        final AtomicBoolean result = new AtomicBoolean(false);
        final Unmarshaller unmarshaller = new UnmarshallerBuilder().withContext(ctx).addAdapters(new MyId.Adapter())
                .addClasspathSchema("/org/fuin/utils4j/test-schema.xsd").withHandler(event -> {
                    result.set(true);
                    return true;
                }).build();

        // VERIFY
        JaxbUtils.unmarshal(unmarshaller, getInvalidXml());
        assertThat(result.get()).isTrue();

    }

    @Test
    public void testWithListener() throws JAXBException {

        // PREPARE
        final JAXBContext ctx = JAXBContext.newInstance(MyClass.class);

        // TEST
        final AtomicBoolean beforeCalled = new AtomicBoolean(false);
        final AtomicBoolean afterCalled = new AtomicBoolean(false);
        final Unmarshaller unmarshaller = new UnmarshallerBuilder().withContext(ctx).addAdapters(new MyId.Adapter())
                .withListener(new Unmarshaller.Listener() {
                    public void beforeUnmarshal(Object target, Object parent) {
                        beforeCalled.set(true);
                    }

                    public void afterUnmarshal(Object target, Object parent) {
                        afterCalled.set(true);
                    }
                }).build();

        // VERIFY
        JaxbUtils.unmarshal(unmarshaller, getInvalidXml());
        assertThat(beforeCalled.get()).isTrue();
        assertThat(afterCalled.get()).isTrue();

    }

    private static String getValidXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<my-class xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.fuin.org/utils4j\" "
                + "id=\"1\" name=\"abc\" />";
    }

    private static String getInvalidXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<my-class xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.fuin.org/utils4j\" "
                + "id=\"1\" />";

    }

}
