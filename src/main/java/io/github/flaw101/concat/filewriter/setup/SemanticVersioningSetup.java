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

import io.github.flaw101.concat.ConcatParams;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Setup File output in line with a basic Semantic Versioning strategy by splitting on file names on the
 * first 3 or 4 <code>.</code> if they are integers and ordering those files with anything after the split natural
 *
 * @author Darren Forsythe
 * @since 1.5.0
 */
public class SemanticVersioningSetup implements OutputSetup {

    private static final Logger logger = LoggerFactory.getLogger(SemanticVersioningSetup.class);


    @Override
    public void setup(ConcatParams params) {
        logger.info("Setting up Semantic Versioning Params");


        final File directory = new File(params.getDirectory());

        final List<File> listFiles = new ArrayList<File>();
        listFiles.addAll(
                FileUtils.listFiles(directory, FileFilterUtils.fileFileFilter(), null));

        Collections.sort(listFiles, new SemanticVersioningCompator());

        logger.info("Found Files - {} to contact", listFiles);

        StartingFileHandler.setStartingFileToStartOfFiles(params, listFiles, logger);

        logger.info("Using files to contact - {}", listFiles);

        params.addAll(listFiles);
    }

}
