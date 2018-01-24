/**
 * MIT License
 *
 * Copyright (c) 2018 Darren Forsythe
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.flaw101.concat;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import com.google.inject.Guice;
import com.google.inject.Injector;

import io.github.flaw101.concat.service.ConcantenationType;
import io.github.flaw101.concat.service.ConcatService;

/**
 * Goal which concatenates several files and creates a new file as specified.
 *
 * @Mojo( name = "concat" )
 *
 * @goal concat
 *
 * 		@Mojo( defaultPhase = "process-sources" )
 * @phase process-sources
 *
 * @author Darren Forsythe
 * @since 1.0.0
 * @version 1.0.0
 */
public class ConcatMojo extends AbstractMojo {

	/**
	 * Type of concatenation to perform
	 *
	 * @parameter
	 */
	private final ConcantenationType concatenationType = ConcantenationType.FILE_LIST;

	/**
	 * The resulting file
	 *
	 * @parameter
	 * @required
	 */
	private File outputFile;

	/**
	 * Files to concatenate if using {@link ConcantenationType#FILE_LIST}.
	 *
	 * @parameter
	 */
	private List<File> concatFiles;

	/**
	 * If using the {@link ConcantenationType#DIRECTORY} provide a directory of
	 * which all files contained within it will be concatenated in natural ordering.
	 *
	 * @parameter
	 */
	private String directory;

	/**
	 * Append newline after each concatenation
	 *
	 * @parameter
	 */
	private boolean appendNewline = false;

	/**
	 * Deletes the target file before concatenation
	 *
	 * @parameter
	 */
	private boolean deleteTargetFile = false;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.maven.plugin.AbstractMojo#execute()
	 */
	@Override
	public void execute() throws MojoExecutionException {
		final ConcatParams params = new ConcatParams(directory, concatFiles, outputFile, deleteTargetFile,
				appendNewline, concatenationType);

		final Injector injector = Guice.createInjector(new ConcatModule());
		final ConcatService concatService = injector.getInstance(ConcatService.class);
		try {
			concatService.concat(params);
		} catch (final Exception e) {
			getLog().error(e);
			throw new MojoExecutionException("Failed to concat", e);
		}
	}

}
