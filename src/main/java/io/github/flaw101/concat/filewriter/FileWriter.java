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
package io.github.flaw101.concat.filewriter;

import io.github.flaw101.concat.ConcatParams;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Concatenates the given files to an output
 *
 * @author Darren Forsythe
 * @since 1.1.0
 */
public class FileWriter {

    private static final Charset CHARSET_UTF_8 = Charset.forName("UTF-8");

    private static final Logger logger = LoggerFactory.getLogger(FileWriter.class);

    public void write(final ConcatParams params) {
        logger.info("Starting write to output file - {}", params.getOutputFile());
        final File outputFile = params.getOutputFile();
        if (params.isDeleteTargetFile()) {
            logger.info("Delete target file is enabled. Deleting Target file - {}", params.getOutputFile());
            FileUtils.deleteQuietly(params.getOutputFile());
        }
        try {
            for (final File inputFile : params.getFiles()) {
                logger.info("Writing {} to output file.", inputFile);
                final String input = FileUtils.readFileToString(inputFile, CHARSET_UTF_8);
                FileUtils.writeStringToFile(params.getOutputFile(), input, CHARSET_UTF_8,
                        true);
                if (params.isAppendNewline()) {
                    logger.info("Appending new line");
                    String property = System.getProperty("line.separator");
                    String lineCharacter = property == null ? StringUtils.CR : property;
                    logger.debug("Using new line character - {}", lineCharacter);
                    FileUtils.writeStringToFile(outputFile,
                            lineCharacter, CHARSET_UTF_8, true);

                }
            }
        } catch (final IOException e) {
            throw new CannotWriteToOutputFileException(e);
        }
    }
}
