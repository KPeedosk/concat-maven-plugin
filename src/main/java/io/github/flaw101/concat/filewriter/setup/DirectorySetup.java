/**
 * MIT License
 * <p>
 * Copyright (c) 2018 Darren Forsythe
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.flaw101.concat.filewriter.setup;

import java.io.File;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

import io.github.flaw101.concat.ConcatParams;
import org.eclipse.aether.internal.impl.slf4j.Slf4jLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sets up the params for Directory concatenation.
 *
 * @author Darren Forsythe
 * @since 1.1.0
 */
public class DirectorySetup implements OutputSetup {

    private static final Logger logger = LoggerFactory.getLogger(DirectorySetup.class);

    @Override
    public void setup(final ConcatParams params) {

        logger.info("Setting Up Directory Concatenation.");

        final File directory = new File(params.getDirectory());

        final List<File> listFiles = new ArrayList<File>();
        listFiles.addAll(
                FileUtils.listFiles(directory, FileFilterUtils.fileFileFilter(), null));

        Collections.sort(listFiles, new Comparator<File>() {
            @Override
            public int compare(final File o1, final File o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });


        logger.info("Found Files - {} to contact", listFiles);

        if (params.getStartingFile() != null) {
            Iterator<File> fileIterator = listFiles.iterator();
            File file;
            logger.debug("Starting File not null. Placing - {} at start of list", params.getStartingFile());
            while (fileIterator.hasNext()) {
                file = fileIterator.next();
                if (file.getAbsolutePath().equals(params.getStartingFile().getAbsolutePath())) {
                    logger.debug("Removing file to file place at start of list - {}", file);
                    fileIterator.remove();
                }
            }
            listFiles.add(0, params.getStartingFile());
        }


        logger.info("Using files to contact - {}", listFiles);

        params.addAll(listFiles);
    }

}
