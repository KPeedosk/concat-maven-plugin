/**
 * MIT License
 * 
 * Copyright for portions of project concat-maven-plugin are held by Darren Forsythe, 2018 as part of project Bar. All other copyright for project Foo are held
 * by Karl Peedosk, 2019.
 * 
 * Copyright (c) 2019 Karl Peedosk
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.kpeedosk.concat;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

import io.github.kpeedosk.concat.service.ConcatService;
import io.github.kpeedosk.concat.service.ConcatenationType;

/**
 * Goal which concatenates several files and creates a new file as specified.
 *
 * @author Karl Peedosk
 * @version 1.1.0
 *          @Mojo( name = "concat" )
 * @goal concat
 *       @Mojo( defaultPhase = "process-sources" )
 * @phase process-sources
 * @since 1.1.0
 */
public class ConcatMojo extends AbstractMojo {

    private static final Logger logger = LoggerFactory.getLogger(ConcatMojo.class);

    /**
     * Type of concatenation to perform
     *
     * @parameter
     * @required
     */
    private ConcatenationType concatenationType;

    /**
     * The resulting file
     *
     * @parameter
     * @required
     */
    private File outputFile;

    /**
     * Files to concatenate if using {@link ConcatenationType#FILE_LIST}.
     *
     * @parameter
     */
    private List<File> concatFiles;

    /**
     * If using the {@link ConcatenationType#DIRECTORY} provide a directory of which all
     * files contained within it will be concatenated in natural ordering.
     *
     * @parameter
     */
    private String directory;

    /**
     * Scan directory recursively.
     *
     * @parameter default-value="false"
     */
    private boolean recursive;

    /**
     * Append newline after each concatenation
     *
     * @parameter default-value="false"
     */
    private boolean appendNewline;

    /**
     * Deletes the target file before concatenation
     *
     * @parameter default-value="false"
     */
    private boolean deleteTargetFile;

    /**
     * If using {@link ConcatenationType#DIRECTORY} you can specify the file you wish to
     * start, the rest will follow in natural order.
     *
     * @parameter
     */
    private File startingFile;

    /*
     * (non-Javadoc)
     * @see org.apache.maven.plugin.AbstractMojo#execute()
     */
    @Override
    public void execute() throws MojoExecutionException {
        final ConcatParams params =
                new ConcatParams(directory, concatFiles, outputFile, deleteTargetFile, appendNewline, recursive, concatenationType, startingFile);
        logger.info("Starting Concatenation");
        logger.debug("Concatenating with params - {}", params);

        final Injector injector = Guice.createInjector(new ConcatModule());
        final ConcatService concatService = injector.getInstance(ConcatService.class);
        try {
            concatService.concat(params);
        } catch (final Exception e) {
            logger.error("Failed to execute concat plugin!", e);
            throw new MojoExecutionException("Failed to concat", e);
        }
    }

}
