package org.fuin.utils4j;

import org.jboss.jandex.CompositeIndex;
import org.jboss.jandex.IndexReader;
import org.jboss.jandex.IndexView;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Helps to read Jandex index files.
 */
public final class JandexIndexFileReader {

    private final List<File> files;

    private final List<String> resources;

    private JandexIndexFileReader() {
        this.files = new ArrayList<>();
        this.resources = new ArrayList<>();
    }

    /**
     * Loads the configured index files.
     *
     * @return Index created from all index files.
     * @throws IOException Failed to read index files or resources.
     */
    public IndexView load() throws IOException {
        final List<IndexView> indexes = new ArrayList<>();
        for (final File file : files) {
            indexes.add(loadFile(file));
        }
        for (final String resource : resources) {
            indexes.add(loadResources(resource));
        }
        return CompositeIndex.create(indexes);
    }

    /**
     * Loads the configured index files.
     * Wraps the possible {@link IOException} into a {@link RuntimeException}.
     *
     * @return Index created from all index files.
     */
    public IndexView loadR() {
        try {
            return load();
        } catch (final IOException ex) {
            throw new RuntimeException("Failed to read index files or resources", ex);
        }
    }

    private IndexView loadFile(final File file) throws IOException {
        try (final InputStream input = new FileInputStream(file)) {
            return new IndexReader(input).read();
        }
    }

    private IndexView loadResources(final String indexFilePathAndName) throws IOException {
        final Enumeration<URL> enu = Thread.currentThread().getContextClassLoader().getResources(indexFilePathAndName);
        final List<IndexView> indexes = new ArrayList<>();
        final List<URL> resources = Collections.list(enu);
        for (final URL url : resources) {
            try (final InputStream input = url.openStream()) {
                indexes.add(new IndexReader(input).read());
            }
        }
        return CompositeIndex.create(indexes);
    }

    /**
     * Creates a new instance of the outer class.
     */
    public static final class Builder {

        private JandexIndexFileReader delegate;

        /**
         * Default constructor.
         */
        public Builder() {
            this.delegate = new JandexIndexFileReader();
        }

        /**
         * Adds one or more files.
         *
         * @param files Index files to read (must exist).
         * @return Builder.
         */
        public Builder addFiles(final File...files) {
            return addFiles(Arrays.asList(files));
        }

        /**
         * Adds one or more files.
         *
         * @param files Index files to read (must exist).
         * @return Builder.
         */
        public Builder addFiles(final List<File> files) {
            for (final File file : Objects.requireNonNull(files, "files==null")) {
                if (!file.exists()) {
                    throw new IllegalStateException("Index file does not exist: " + file);
                }
                if (!file.isFile()) {
                    throw new IllegalStateException("No file: " + file);
                }
                delegate.files.add(Objects.requireNonNull(file, "file==null"));
            }
            return this;
        }

        /**
         * Adds a default resource named "META-INF/jandex.idx".
         *
         * @return Builder.
         */
        public Builder addDefaultResource() {
            addResources("META-INF/jandex.idx");
            return this;
        }

        /**
         * Adds one or more resources.
         *
         * @return Builder.
         */
        public Builder addResources(final String...resources) {
            return addResources(Arrays.asList(resources));
        }

        /**
         * Adds one or more resources.
         *
         * @return Builder.
         */
        public Builder addResources(final List<String> resources) {
            for (final String resource : Objects.requireNonNull(resources, "resources==null")) {
                delegate.resources.add(Objects.requireNonNull(resource, "resource==null"));
            }
            return this;
        }

        /**
         * Builds an instance of the outer class.
         *
         * @return New instance.
         */
        public JandexIndexFileReader build() {
            if (delegate.resources.isEmpty() && delegate.files.isEmpty()) {
                addDefaultResource();
            }
            final JandexIndexFileReader tmp = delegate;
            delegate = new JandexIndexFileReader();
            return tmp;
        }

    }

}
