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

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.junit.jupiter.api.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.*;

/**
 * Test for the {@link MarshallerBuilder} class.
 */
public class MarshallerBuilderTest {

    @Test
    public void testWithContext() throws JAXBException {

        // PREPARE
        final JAXBContext ctx = JAXBContext.newInstance(MyClass.class);
        final MyId myId = new MyId(1);
        final MyClass myClass = new MyClass(myId, "abc");

        // TEST
        final Marshaller marshaller = new MarshallerBuilder().withContext(ctx).addAdapters(new MyId.Adapter()).build();

        // VERIFY
        final String copyXml = JaxbUtils.marshal(marshaller, myClass);
        final Diff documentDiff = DiffBuilder.compare(getValidXml()).withTest(copyXml).ignoreWhitespace().build();
        assertThat(documentDiff.hasDifferences()).describedAs(documentDiff.toString()).isFalse();

    }

    @Test
    public void testWithClassesToBeBound() throws JAXBException {

        // PREPARE
        final Class<?>[] classesToBeBound = new Class[] { MyClass.class };
        final MyId myId = new MyId(1);
        final MyClass myClass = new MyClass(myId, "abc");

        // TEST
        final List<XmlAdapter<?, ?>> adapters = new ArrayList<>();
        adapters.add(new MyId.Adapter());
        final Marshaller marshaller = new MarshallerBuilder().addClassesToBeBound(classesToBeBound).addAdapters(adapters).build();

        // VERIFY
        final String copyXml = JaxbUtils.marshal(marshaller, myClass);
        final Diff documentDiff = DiffBuilder.compare(getValidXml()).withTest(copyXml).ignoreWhitespace().build();
        assertThat(documentDiff.hasDifferences()).describedAs(documentDiff.toString()).isFalse();

    }

    @Test
    public void testClasspathSchema() throws JAXBException {

        // PREPARE
        final List<Class<?>> classesToBeBound = new ArrayList<>();
        classesToBeBound.add(MyClass.class);
        final String xsd = "/org/fuin/utils4j/test-schema.xsd";
        final List<String> schemas = new ArrayList<>();
        schemas.add(xsd);
        final MyId myId = new MyId(1);
        final MyClass myClass = new MyClass(myId, "abc");

        // TEST
        final Marshaller marshaller = new MarshallerBuilder().addClassesToBeBound(classesToBeBound)
                .addClasspathSchemas("/org/fuin/utils4j/test-schema.xsd")
                .addClasspathSchemas(schemas) // Second add does not harm anything
                .addAdapter(new MyId.Adapter())
                .build();

        // VERIFY
        final String copyXml = JaxbUtils.marshal(marshaller, myClass);
        final Diff documentDiff = DiffBuilder.compare(getValidXml()).withTest(copyXml).ignoreWhitespace().build();
        assertThat(documentDiff.hasDifferences()).describedAs(documentDiff.toString()).isFalse();

    }

    @Test
    public void testAddSchemas() throws JAXBException {

        // PREPARE
        final JAXBContext ctx = JAXBContext.newInstance(MyClass.class);
        final StreamSource xsd = new StreamSource(getClass().getResourceAsStream("/org/fuin/utils4j/test-schema.xsd"));
        final List<Source> schemas = new ArrayList<>();
        schemas.add(xsd);
        final MyId myId = new MyId(1);
        final MyClass myClass = new MyClass(myId, "abc");

        // TEST
        final Marshaller marshaller = new MarshallerBuilder()
                .withContext(ctx)
                .addAdapters(new MyId.Adapter())
                .addSchemas(xsd)
                .addSchemas(schemas) // Second add does not harm anything
                .build();

        // VERIFY
        final String copyXml = JaxbUtils.marshal(marshaller, myClass);
        final Diff documentDiff = DiffBuilder.compare(getValidXml()).withTest(copyXml).ignoreWhitespace().build();
        assertThat(documentDiff.hasDifferences()).describedAs(documentDiff.toString()).isFalse();

    }

    @Test
    public void testClassesAndContextFailure() throws JAXBException {
        try {
            new MarshallerBuilder().withContext(JAXBContext.newInstance(MyClass.class)).addClassToBeBound(MyClass.class).build();
            fail("Expected exception");
        } catch (final IllegalStateException ex) {
            assertThat(ex.getMessage()).isEqualTo(
                    "A JAXBContext (ctx) and a list of classes (classesToBeBound) was provided. You can only use one of them, not both.");
        }
    }

    @Test
    public void testNoClassesAndNoContext() throws JAXBException {
        try {
            new MarshallerBuilder().build();
            fail("Expected exception");
        } catch (final IllegalStateException ex) {
            assertThat(ex.getMessage()).isEqualTo("Either the JAXBContext (ctx) or a list of classes (classesToBeBound) must be provided");
        }
    }

    @Test
    public void testAddPropertyOK() {
        new MarshallerBuilder().addClassToBeBound(MyClass.class).addProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true).build();
    }

    @Test
    public void testAddPropertyFailure() {
        assertThatThrownBy(() -> {
            new MarshallerBuilder().addClassToBeBound(MyClass.class).addProperty("bla", "blub").build();
        }).isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("Failed to set property 'bla' to: blub");
    }

    @Test
    public void testWithEventHandler() throws JAXBException {

        // PREPARE
        final JAXBContext ctx = JAXBContext.newInstance(MyClass.class);
        final MyId myId = new MyId(1);
        final MyClass myClass = new MyClass(myId, "abc");

        // TEST
        final AtomicBoolean result = new AtomicBoolean(false);
        final Marshaller marshaller = new MarshallerBuilder().withContext(ctx).addAdapters(new MyId.Adapter())
                .addClasspathSchema("/org/fuin/utils4j/test-schema.xsd")
                .withHandler(event -> {
                    result.set(true);
                    return true;
                }).build();

        // VERIFY
        final String copyXml = JaxbUtils.marshal(marshaller, myClass);
        final Diff documentDiff = DiffBuilder.compare(getValidXml()).withTest(copyXml).ignoreWhitespace().build();
        assertThat(documentDiff.hasDifferences()).describedAs(documentDiff.toString()).isFalse();

    }

    @Test
    public void testWithListener() throws JAXBException {

        // PREPARE
        final JAXBContext ctx = JAXBContext.newInstance(MyClass.class);
        final MyId myId = new MyId(1);
        final MyClass myClass = new MyClass(myId, "abc");

        // TEST
        final AtomicBoolean beforeCalled = new AtomicBoolean(false);
        final AtomicBoolean afterCalled = new AtomicBoolean(false);
        final Marshaller marshaller = new MarshallerBuilder().withContext(ctx).addAdapters(new MyId.Adapter())
                .withListener(new Marshaller.Listener() {
                    @Override
                    public void beforeMarshal(Object source) {
                        beforeCalled.set(true);
                    }
                    @Override
                    public void afterMarshal(Object source) {
                        afterCalled.set(true);
                    }
                }).build();

        // VERIFY
        JaxbUtils.marshal(marshaller, myClass);
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
