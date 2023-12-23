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
package org.fuin.utils4j.examples;

import jakarta.xml.bind.JAXBContext;
import org.fuin.utils4j.jaxb.CDataXmlAdapter;
import org.fuin.utils4j.jaxb.CDataXmlStreamWriter;
import org.fuin.utils4j.jaxb.MyClassWithCData;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.StringWriter;

import static org.fuin.utils4j.jaxb.JaxbUtils.marshal;
import static org.fuin.utils4j.jaxb.JaxbUtils.unmarshal;

/**
 * Short example for using the {@link CDataXmlStreamWriter} together with the {@link CDataXmlAdapter}.
 */
// CHECKSTYLE:OFF
public class CDataJaxbExample {

    /**
     * Executes the example.
     * 
     * @param args
     *            Not used.
     */
    public static void main(String[] args) throws Exception {

        // Create writers
        final StringWriter writer = new StringWriter();
        final XMLStreamWriter xmlWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(writer);
        final CDataXmlStreamWriter cdataWriter = new CDataXmlStreamWriter(xmlWriter);

        // Create JAXB context with example class
        final JAXBContext ctx = JAXBContext.newInstance(MyClassWithCData.class);
        final MyClassWithCData testee = new MyClassWithCData("<whatever this=\"is\"/>");

        // Convert instance to XML
        marshal(ctx, testee, null, cdataWriter);
        final String xml = writer.toString();

        // Prints out the result
        System.out.println(xml);
        // <?xml version="1.0" ?><my-class-with-cdata><![CDATA[<whatever
        // this="is"/>]]></my-class-with-cdata>

        // Convert it back to object
        final MyClassWithCData copy = unmarshal(xml, MyClassWithCData.class);

        // Print out cdata content
        System.out.println(copy.getContent());
        // <whatever this="is"/>

    }

}
// CHECKSTYLE:ON
