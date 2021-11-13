package org.fuin.utils4j.jaxb;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.fuin.utils4j.Utils4J;
import org.xml.sax.SAXException;

/**
 * Builder that helps creating an {@link Unmarshaller} instance.
 */
public final class UnmarshallerBuilder {

    private final Set<XmlAdapter<?, ?>> adapters;

    private final Set<Source> schemaSources;

    private final Map<String, Object> properties;

    private final Set<Class<?>> classesToBeBound;

    private ValidationEventHandler handler;

    private JAXBContext ctx;

    private Unmarshaller.Listener listener;

    /**
     * Default constructor.
     */
    public UnmarshallerBuilder() {
        adapters = new HashSet<>();
        schemaSources = new HashSet<>();
        properties = new HashMap<>();
        classesToBeBound = new HashSet<>();
    }

    /**
     * Use a predefined context. An alternative would be to let the build create a JAXB context internally by using method
     * {@link #addClassToBeBound(Class)}, {@link #addClassesToBeBound(Class...) or {@link #addClassesToBeBound(Collection)}.
     * 
     * @param ctx
     *            Context to use for unmarshalling.
     * 
     * @return The builder.
     */
    public UnmarshallerBuilder withContext(final JAXBContext ctx) {
        this.ctx = ctx;
        return this;
    }

    /**
     * Sets a listener for the unmarshaller.
     * 
     * @param listener
     *            Listener to attach.
     * 
     * @return The builder.
     */
    public UnmarshallerBuilder withListener(final Unmarshaller.Listener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * Sets a validation event handler for the unmarshaller.
     * 
     * @param handler
     *            Handler to attach.
     * 
     * @return The builder.
     */
    public UnmarshallerBuilder withHandler(final ValidationEventHandler handler) {
        this.handler = handler;
        return this;
    }

    /**
     * Adds a class to the internal {@link JAXBContext}.
     * 
     * @param clasz
     *            Class to add.
     * 
     * @return The builder.
     */
    public UnmarshallerBuilder addClassToBeBound(Class<?> clasz) {
        Utils4J.checkNotNull("clasz", clasz);
        classesToBeBound.add(clasz);
        return this;
    }

    /**
     * Adds an array of classes to the internal {@link JAXBContext}.
     * 
     * @param classes
     *            Classes to add.
     * 
     * @return The builder.
     */
    public UnmarshallerBuilder addClassesToBeBound(Class<?>... classes) {
        Utils4J.checkNotNull("classes", classes);
        Arrays.asList(classes).forEach(this::addClassToBeBound);
        return this;
    }

    /**
     * Adds a list of classes to the internal {@link JAXBContext}.
     * 
     * @param classes
     *            Classes to add.
     * 
     * @return The builder.
     */
    public UnmarshallerBuilder addClassesToBeBound(final Collection<Class<?>> classes) {
        Utils4J.checkNotNull("classes", classes);
        classes.forEach(this::addClassToBeBound);
        return this;
    }

    /**
     * Adds an XML adapter to the internal {@link JAXBContext}.
     * 
     * @param adapter
     *            Adapter to add.
     * 
     * @return The builder.
     */
    public UnmarshallerBuilder addAdapter(final XmlAdapter<?, ?> adapter) {
        Utils4J.checkNotNull("adapter", adapter);
        adapters.add(adapter);
        return this;
    }

    /**
     * Adds an array of XML adapters to the internal {@link JAXBContext}.
     * 
     * @param adapters
     *            Adapters to add.
     * 
     * @return The builder.
     */
    public UnmarshallerBuilder addAdapters(final XmlAdapter<?, ?>... adapters) {
        Utils4J.checkNotNull("adapters", adapters);
        Arrays.asList(adapters).forEach(this::addAdapter);
        return this;
    }

    /**
     * Adds a list of XML adapters to the internal {@link JAXBContext}.
     * 
     * @param adapters
     *            Adapters to add.
     * 
     * @return The builder.
     */
    public UnmarshallerBuilder addAdapters(final Collection<XmlAdapter<?, ?>> adapters) {
        Utils4J.checkNotNull("adapters", adapters);
        adapters.forEach(this::addAdapter);
        return this;
    }

    /**
     * Adds a schema to validate while unmarshalling.
     * 
     * @param source
     *            Source to add.
     * 
     * @return The builder.
     */
    public UnmarshallerBuilder addSchema(Source source) {
        Utils4J.checkNotNull("source", source);
        schemaSources.add(source);
        return this;
    }

    /**
     * Adds an array of schemas to validate while unmarshalling.
     * 
     * @param sources
     *            Sources to add.
     * 
     * @return The builder.
     */
    public UnmarshallerBuilder addSchemas(Source... sources) {
        Utils4J.checkNotNull("sources", sources);
        Arrays.asList(sources).forEach(this::addSchema);
        return this;
    }

    /**
     * Adds a list of schemas to validate while unmarshalling.
     * 
     * @param sources
     *            Sources to add.
     * 
     * @return The builder.
     */
    public UnmarshallerBuilder addSchemas(final Collection<Source> sources) {
        Utils4J.checkNotNull("sources", sources);
        sources.forEach(this::addSchema);
        return this;
    }

    /**
     * Adds a schema in the classpath to validate while unmarshalling.
     * 
     * @param xsdPath
     *            Schema path and name to add.
     * 
     * @return The builder.
     */
    public UnmarshallerBuilder addClasspathSchema(final String xsdPath) {
        Utils4J.checkNotNull("xsdPath", xsdPath);
        addSchema(new StreamSource(getClass().getResourceAsStream(xsdPath)));
        return this;
    }

    /**
     * Adds an array of schemas in the classpath to validate while unmarshalling.
     * 
     * @param xsdPaths
     *            Schema paths and names to add.
     * 
     * @return The builder.
     */
    public UnmarshallerBuilder addClasspathSchemas(String... xsdPaths) {
        Utils4J.checkNotNull("xsdPaths", xsdPaths);
        Arrays.asList(xsdPaths).forEach(this::addClasspathSchema);
        return this;
    }

    /**
     * Adds a list of schemas in the classpath to validate while unmarshalling.
     * 
     * @param xsdPaths
     *            Schema paths and names to add.
     * 
     * @return The builder.
     */
    public UnmarshallerBuilder addClasspathSchemas(final Collection<String> xsdPaths) {
        Utils4J.checkNotNull("xsdPaths", xsdPaths);
        xsdPaths.forEach(this::addClasspathSchema);
        return this;
    }

    /**
     * Adds a property to the unmarshaller.
     * 
     * @param name
     *            Name to add. Cannot be <code>null</code>.
     * @param value
     *            Value to add. Cannot be <code>null</code>.
     * 
     * @return The builder.
     */
    public UnmarshallerBuilder addProperty(String name, Object value) {
        Utils4J.checkNotNull("name", name);
        Utils4J.checkNotNull("value", value);
        properties.put(name, value);
        return this;
    }

    private static void setProperties(final Unmarshaller unmarshaller, final Map<String, Object> map) {
        map.forEach((name, value) -> {
            try {
                unmarshaller.setProperty(name, value);
            } catch (final PropertyException ex) {
                throw new IllegalArgumentException("Failed to set property '" + name + "' to: " + value, ex);
            }
        });
    }

    private Schema createSchema() {
        final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
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

    private static Unmarshaller createUnmarshaller(final JAXBContext ctx) {
        try {
            return ctx.createUnmarshaller();
        } catch (final JAXBException ex) {
            throw new RuntimeException("Failed to create unmarshaller", ex);
        }
    }

    private static void setEventHandler(final Unmarshaller unmarshaller, final ValidationEventHandler handler) {
        try {
            unmarshaller.setEventHandler(handler);
        } catch (final JAXBException ex) {
            throw new RuntimeException("Failed to set unmarshaller's event handler", ex);
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
    public Unmarshaller build() {
        final Unmarshaller unmarshaller = createUnmarshaller(getCtx());
        adapters.forEach(unmarshaller::setAdapter);
        if (handler != null) {
            setEventHandler(unmarshaller, handler);
        }
        if (!schemaSources.isEmpty()) {
            unmarshaller.setSchema(createSchema());
        }
        if (listener != null) {
            unmarshaller.setListener(listener);
        }
        if (!properties.isEmpty()) {
            setProperties(unmarshaller, properties);
        }
        return unmarshaller;
    }

}
