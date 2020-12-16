// CHECKSTYLE:OFF
package org.fuin.utils4j;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Example class with CDATA field.
 */
@XmlRootElement(name = "my-class-with-cdata")
public final class MyClassWithCData {

    @XmlValue
    @XmlJavaTypeAdapter(CDataXmlAdapter.class)
    private String content;

    /**
     * Default constructor for JAXB.
     */
    protected MyClassWithCData() {
        super();
    }

    /**
     * Constructor with mandatory data.
     * 
     * @param content
     *            CDATA content.
     */
    public MyClassWithCData(final String content) {
        super();
        this.content = content;
    }

    /**
     * Returns the content.
     * 
     * @return Content.
     */
    public String getContent() {
        return content;
    }

}
// CHECKSTYLE:ON
