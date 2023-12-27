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
import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLOutputFactory;
import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fuin.utils4j.jaxb.JaxbUtils.marshal;
import static org.fuin.utils4j.jaxb.JaxbUtils.unmarshal;

// CHECKSTYLE:OFF
public class CDataJaxbTest {

    private static final String DATA = "<whatever this=\"is\"/>";

    @Test
    public final void testMarshal() throws Exception {

        final StringWriter writer = new StringWriter();
        try (final CDataXmlStreamWriter sw = new CDataXmlStreamWriter(XMLOutputFactory.newInstance().createXMLStreamWriter(writer));) {

            // PREPARE
            final JAXBContext ctx = JAXBContext.newInstance(MyClassWithCData.class);
            final MyClassWithCData testee = new MyClassWithCData(DATA);

            // TEST
            marshal(ctx, testee, null, sw);
            final String result = writer.toString();

            // VERIFY
            assertThat(result)
                    .isEqualTo("<?xml version=\"1.0\" ?><my-class-with-cdata>" + "<![CDATA[" + DATA + "]]>" + "</my-class-with-cdata>");

        }
    }

    @Test
    public final void testUnmarshal() throws Exception {

        // TEST
        final MyClassWithCData testee = unmarshal("<my-class-with-cdata>" + "<![CDATA[" + DATA + "]]>" + "</my-class-with-cdata>",
                MyClassWithCData.class);

        // VERIFY
        assertThat(testee).isNotNull();
        assertThat(testee.getContent()).isEqualTo(DATA);

    }

}
// CHECKSTYLE:ON
