package org.fuin.utils4j.jaxb;

import jakarta.xml.bind.*;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import org.fuin.utils4j.Utils4J;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.util.*;

/**
 * Builder that helps creating an {@link Marshaller} instance.
 */
public class MarshallerBuilder {

    private final Set<XmlAdapter<?, ?>> adapters;

    private final Set<Source> schemaSources;

    private final Set<Class<?>> classesToBeBound;

    private final Map<String, Object> properties;

    private Marshaller.Listener listener;

    private ValidationEventHandler handler;

    private JAXBContext ctx;


    /**
     * Default constructor.
     */
    public MarshallerBuilder() {
        adapters = new HashSet<>();
        schemaSources = new HashSet<>();
        classesToBeBound = new HashSet<>();
        properties = new HashMap<>();
    }

    /**
     * Use a predefined context. An alternative would be to let the build create a JAXB context internally by using method
     * {@link #addClassToBeBound(Class)}, {@link #addClassesToBeBound(Class...)} or {@link #addClassesToBeBound(Collection)}.
     *
     * @param ctx Context to use for unmarshalling.
     * @return The builder.
     */
    public MarshallerBuilder withContext(final JAXBContext ctx) {
        this.ctx = ctx;
        return this;
    }

    /**
     * Sets a listener for the marshaller.
     *
     * @param listener Listener to attach.
     * @return The builder.
     */
    public MarshallerBuilder withListener(final Marshaller.Listener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * Sets a validation event handler for the marshaller.
     *
     * @param handler
     *            Handler to attach.
     *
     * @return The builder.
     */
    public MarshallerBuilder withHandler(final ValidationEventHandler handler) {
        this.handler = handler;
        return this;
    }

    /**
     * Adds a class to the internal {@link JAXBContext}.
     *
     * @param clasz Class to add.
     * @return The builder.
     */
    public MarshallerBuilder addClassToBeBound(Class<?> clasz) {
        Utils4J.checkNotNull("clasz", clasz);
        classesToBeBound.add(clasz);
        return this;
    }

    /**
     * Adds an array of classes to the internal {@link JAXBContext}.
     *
     * @param classes Classes to add.
     * @return The builder.
     */
    public MarshallerBuilder addClassesToBeBound(Class<?>... classes) {
        Utils4J.checkNotNull("classes", classes);
        Arrays.asList(classes).forEach(this::addClassToBeBound);
        return this;
    }

    /**
     * Adds a list of classes to the internal {@link JAXBContext}.
     *
     * @param classes Classes to add.
     * @return The builder.
     */
    public MarshallerBuilder addClassesToBeBound(final Collection<Class<?>> classes) {
        Utils4J.checkNotNull("classes", classes);
        classes.forEach(this::addClassToBeBound);
        return this;
    }

    /**
     * Adds an XML adapter to the internal {@link JAXBContext}.
     *
     * @param adapter Adapter to add.
     * @return The builder.
     */
    public MarshallerBuilder addAdapter(final XmlAdapter<?, ?> adapter) {
        Utils4J.checkNotNull("adapter", adapter);
        adapters.add(adapter);
        return this;
    }

    /**
     * Adds an array of XML adapters to the internal {@link JAXBContext}.
     *
     * @param adapters Adapters to add.
     * @return The builder.
     */
    public MarshallerBuilder addAdapters(final XmlAdapter<?, ?>... adapters) {
        Utils4J.checkNotNull("adapters", adapters);
        Arrays.asList(adapters).forEach(this::addAdapter);
        return this;
    }

    /**
     * Adds a list of XML adapters to the internal {@link JAXBContext}.
     *
     * @param adapters Adapters to add.
     * @return The builder.
     */
    public MarshallerBuilder addAdapters(final Collection<XmlAdapter<?, ?>> adapters) {
        Utils4J.checkNotNull("adapters", adapters);
        adapters.forEach(this::addAdapter);
        return this;
    }

    /**
     * Adds a schema to validate while unmarshalling.
     *
     * @param source Source to add.
     * @return The builder.
     */
    public MarshallerBuilder addSchema(Source source) {
        Utils4J.checkNotNull("source", source);
        schemaSources.add(source);
        return this;
    }

    /**
     * Adds an array of schemas to validate while unmarshalling.
     *
     * @param sources Sources to add.
     * @return The builder.
     */
    public MarshallerBuilder addSchemas(Source... sources) {
        Utils4J.checkNotNull("sources", sources);
        Arrays.asList(sources).forEach(this::addSchema);
        return this;
    }

    /**
     * Adds a list of schemas to validate while unmarshalling.
     *
     * @param sources Sources to add.
     * @return The builder.
     */
    public MarshallerBuilder addSchemas(final Collection<Source> sources) {
        Utils4J.checkNotNull("sources", sources);
        sources.forEach(this::addSchema);
        return this;
    }

    /**
     * Adds a schema in the classpath to validate while unmarshalling.
     *
     * @param xsdPath Schema path and name to add.
     * @return The builder.
     */
    public MarshallerBuilder addClasspathSchema(final String xsdPath) {
        Utils4J.checkNotNull("xsdPath", xsdPath);
        addSchema(new StreamSource(getClass().getResourceAsStream(xsdPath)));
        return this;
    }

    /**
     * Adds an array of schemas in the classpath to validate while unmarshalling.
     *
     * @param xsdPaths Schema paths and names to add.
     * @return The builder.
     */
    public MarshallerBuilder addClasspathSchemas(String... xsdPaths) {
        Utils4J.checkNotNull("xsdPaths", xsdPaths);
        Arrays.asList(xsdPaths).forEach(this::addClasspathSchema);
        return this;
    }

    /**
     * Adds a list of schemas in the classpath to validate while unmarshalling.
     *
     * @param xsdPaths Schema paths and names to add.
     * @return The builder.
     */
    public MarshallerBuilder addClasspathSchemas(final Collection<String> xsdPaths) {
        Utils4J.checkNotNull("xsdPaths", xsdPaths);
        xsdPaths.forEach(this::addClasspathSchema);
        return this;
    }

    /**
     * Sets the marshaller to pretty-print the output.
     *
     * @return The builder.
     */
    public MarshallerBuilder prettyPrint() {
        addProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return this;
    }

    /**
     * Adds a property to the marshaller.
     *
     * @param name  Name to add. Cannot be <code>null</code>.
     * @param value Value to add. Cannot be <code>null</code>.
     * @return The builder.
     */
    public MarshallerBuilder addProperty(String name, Object value) {
        Utils4J.checkNotNull("name", name);
        Utils4J.checkNotNull("value", value);
        properties.put(name, value);
        return this;
    }

    private static void setProperties(final Marshaller marshaller, final Map<String, Object> map) {
        map.forEach((name, value) -> {
            try {
                marshaller.setProperty(name, value);
            } catch (final PropertyException ex) {
                throw new IllegalArgumentException("Failed to set property '" + name + "' to: " + value, ex);
            }
        });
    }

    private Schema createSchema() {
        final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            return factory.newSchema(schemaSources.toArray(new Source[schemaSources.size()]));
        } catch (final SAXException ex) {
            throw new RuntimeException("Failed to create schema", ex);
        }
    }

    private JAXBContext getCtx() {
        if (!classesToBeBound.isEmpty()) {
            if (ctx != null) {
                throw new IllegalStateException("A JAXBContext (ctx) and a list of classes (classesToBeBound) was provided. "
                        + "You can only use one of them, not both.");
            }
            return createContext(classesToBeBound);
        }
        if (ctx != null) {
            return ctx;
        }
        throw new IllegalStateException("Either the JAXBContext (ctx) or a list of classes (classesToBeBound) must be provided");
    }

    private static Marshaller createMarshaller(final JAXBContext ctx) {
        try {
            return ctx.createMarshaller();
        } catch (final JAXBException ex) {
            throw new RuntimeException("Failed to create marshaller", ex);
        }
    }

    private static void setEventHandler(final Marshaller marshaller, final ValidationEventHandler handler) {
        try {
            marshaller.setEventHandler(handler);
        } catch (final JAXBException ex) {
            throw new RuntimeException("Failed to set marshaller's event handler", ex);
        }
    }

    private static JAXBContext createContext(final Set<Class<?>> classesToBeBound) {
        try {
            return JAXBContext.newInstance(classesToBeBound.toArray(new Class<?>[classesToBeBound.size()]));
        } catch (final JAXBException ex) {
            throw new RuntimeException("Failed to create JAXB context with classes: " + classesToBeBound, ex);
        }
    }

    /**
     * Builds the final instance based on the builder's setting.
     *
     * @return New instance.
     */
    public Marshaller build() {
        final Marshaller marshaller = createMarshaller(getCtx());
        adapters.forEach(marshaller::setAdapter);
        if (handler != null) {
            setEventHandler(marshaller, handler);
        }
        if (!schemaSources.isEmpty()) {
            marshaller.setSchema(createSchema());
        }
        if (!properties.isEmpty()) {
            setProperties(marshaller, properties);
        }
        if (listener != null) {
            marshaller.setListener(listener);
        }
        return marshaller;
    }

}
