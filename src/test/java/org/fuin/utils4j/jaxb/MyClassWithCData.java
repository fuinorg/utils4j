// CHECKSTYLE:OFF
package org.fuin.utils4j.jaxb;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.fuin.utils4j.jaxb.CDataXmlAdapter;

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
