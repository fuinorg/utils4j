package org.fuin.utils4j.jaxb;

import java.util.Objects;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class MyId {

    private Integer id;

    public MyId(Integer id) {
        super();
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MyId other = (MyId) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "" + id;
    }

    public static class Adapter extends XmlAdapter<Integer, MyId> {

        @Override
        public MyId unmarshal(Integer v) throws Exception {
            return new MyId(v);
        }

        @Override
        public Integer marshal(MyId v) throws Exception {
            return v.getId();
        }

    }

}
