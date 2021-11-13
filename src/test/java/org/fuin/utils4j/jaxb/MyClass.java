package org.fuin.utils4j.jaxb;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "my-class", namespace = "http://www.fuin.org/utils4j")
public class MyClass {

    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(value = MyId.Adapter.class)
    private MyId id;

    private String name;

    protected MyClass() {
        super();
    }

    public MyClass(MyId id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public MyId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
