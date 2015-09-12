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

import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.fuin.utils4j.JaxbUtils.XML_PREFIX;
import static org.fuin.utils4j.JaxbUtils.marshal;
import static org.fuin.utils4j.JaxbUtils.unmarshal;
import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//CHECKSTYLE:OFF for tests
public class SimpleVariableTest {

    private static final String NAME = "a";

    private static final String VALUE = "1";

    private SimpleVariable testee;

    @Before
    public void setup() {
        testee = new SimpleVariable(NAME, VALUE);
    }

    @After
    public void teardown() {
        testee = null;
    }

    @Test
    public void testConstructorNameValue() {

        // TEST
        final SimpleVariable testee = new SimpleVariable(NAME, VALUE);

        // VERIFY
        assertThat(testee.getName()).isEqualTo(NAME);
        assertThat(testee.getValue()).isEqualTo(VALUE);
        assertThat(testee.getURL()).isNull();
        assertThat(testee.getEncoding()).isNull();
        assertThat(testee.getEncodingOrDefault()).isEqualTo("utf-8");

    }

    @Test
    public void testConstructorUrl() {

        // PREPARE
        final URL url = Utils4J.url("classpath:org/fuin/utils4j/test.properties");

        // TEST
        final SimpleVariable testee = new SimpleVariable(NAME, url);

        // VERIFY
        assertThat(testee.getName()).isEqualTo(NAME);
        assertThat(testee.getValue()).isEqualTo("one=1\r\ntwo=2\r\nthree=3\r\n");
        assertThat(testee.getURL()).isEqualTo(url);
        assertThat(testee.getEncoding()).isNull();
        assertThat(testee.getEncodingOrDefault()).isEqualTo("utf-8");

    }

    @Test
    public void testConstructorUrlEncoding() {

        // PREPARE
        final URL url = Utils4J.url("classpath:org/fuin/utils4j/test.properties");

        // TEST
        final SimpleVariable testee = new SimpleVariable(NAME, url, "ISO-8859-1");

        // VERIFY
        assertThat(testee.getName()).isEqualTo(NAME);
        assertThat(testee.getValue()).isEqualTo("one=1\r\ntwo=2\r\nthree=3\r\n");
        assertThat(testee.getURL()).isEqualTo(url);
        assertThat(testee.getEncoding()).isEqualTo("ISO-8859-1");
        assertThat(testee.getEncodingOrDefault()).isEqualTo("ISO-8859-1");

    }

    @Test
    public void testEqualsHashCode() throws MalformedURLException {
        EqualsVerifier
                .forClass(SimpleVariable.class)
                .withPrefabValues(URL.class, new URL("http://www.fuin.org/text1.txt"),
                        new URL("http://www.fuin.org/text2.txt"))
                .suppress(Warning.NONFINAL_FIELDS, Warning.NULL_FIELDS).verify();
    }

    @Test
    public void testNullName() {
        try {
            new SimpleVariable(null, VALUE);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'name' cannot be null");
        }
    }

    @Test
    public void testEmptyName() {
        try {
            new SimpleVariable("", VALUE);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'name' cannot be empty");
        }
    }

    @Test
    public void testNullValue() {
        try {
            new SimpleVariable(NAME, (String) null);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'value' cannot be null");
        }
    }

    @Test
    public void testNullURL() {
        try {
            new SimpleVariable(NAME, (URL) null);
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'url' cannot be null");
        }
    }

    @Test
    public void testNullURL2() {
        try {
            new SimpleVariable(NAME, (URL) null, "utf-8");
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'url' cannot be null");
        }
    }

    @Test
    public void testEmptyEncoding() throws MalformedURLException {
        try {
            new SimpleVariable(NAME, new URL("http://www.fuin.org/test.txt"), "");
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage()).isEqualTo("The argument 'encoding' cannot be empty");
        }
    }

    @Test
    public final void testMarshalUnmarshalXML() throws Exception {

        // PREPARE
        final SimpleVariable original = new SimpleVariable(NAME, VALUE);

        // TEST
        final String xml = marshal(original, createXmlAdapter(), SimpleVariable.class);

        // VERIFY
        XMLUnit.setIgnoreWhitespace(true);
        XMLAssert.assertXMLEqual(XML_PREFIX + "<variable name=\"a\" value=\"1\"/>", xml);
        final SimpleVariable copy = unmarshal(xml, createXmlAdapter(), SimpleVariable.class);
        assertThat(copy.getName()).isEqualTo("a");
        assertThat(copy.getValue()).isEqualTo("1");
    }

    @Test
    public final void testMarshalUnmarshalEquals() throws Exception {

        // PREPARE
        final SimpleVariable original = testee;

        // TEST
        final String xml = marshal(original, createXmlAdapter(), SimpleVariable.class);

        final SimpleVariable copy = unmarshal(xml, createXmlAdapter(), SimpleVariable.class);

        // VERIFY
        assertThat(copy).isEqualTo(original);
    }

    private XmlAdapter<?, ?>[] createXmlAdapter() {
        // Not necessary now - Add XML adapter if needed later on...
        return new XmlAdapter[] {};
    }

}
// CHECKSTYLE:ON
