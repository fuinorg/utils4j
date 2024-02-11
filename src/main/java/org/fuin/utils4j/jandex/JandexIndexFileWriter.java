package org.fuin.utils4j.jandex;

import org.jboss.jandex.*;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * Helps to write Jandex index files.
 */
public final class JandexIndexFileWriter {

    /**
     * Writes the index to a file.
     * Wraps the possible {@link IOException} into a {@link RuntimeException}.
     *
     * @param file File to write to.
     * @param index Index to save.
     */
    public void writeIndexR(final File file, final Index index) {
        try {
            writeIndex(file, index);
        } catch (final IOException ex) {
            throw new RuntimeException("Failed to write index to: " + file, ex);
        }
    }

    /**
     * Writes the index to a file.
     *
     * @param file File to write to.
     * @param index Index to save.
     * @throws IOException Failed to write to the file.
     */
    public void writeIndex(final File file, final Index index) throws IOException {
        try (final OutputStream out = new FileOutputStream(file)) {
            new IndexWriter(out).write(index);
        }
    }

}
