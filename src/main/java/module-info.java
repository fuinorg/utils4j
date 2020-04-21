module org.fuin.utils4j {

    requires java.base;
    requires java.prefs;
    requires java.xml;
    requires static transitive java.xml.bind;
    requires static transitive org.jboss.jandex;
    
    exports org.fuin.utils4j;
    exports org.fuin.utils4j.classpath;
    exports org.fuin.utils4j.fileprocessor;
    exports org.fuin.utils4j.filter;

}
