package org.fuin.utils4j.jaxb;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "my-class", namespace = "http://www.fuin.org/utils4j")
public class MyClass {

    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(value = MyId.Adapter.class)
    private MyId id;

    @NotNull
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
