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
package io.github.flaw101.concat.validate;

import java.io.File;

import org.codehaus.plexus.util.StringUtils;

import io.github.flaw101.concat.ConcatParams;

/**
 * Validates required params for File List Type.
 * 
 * @author Darren Forsythe
 * @since 1.1.0
 *
 */
public class FileListValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.github.flaw101.concat.validate.Validator#validate(io.github.flaw101.concat
	 * .ConcatParams)
	 */
	@Override
	public void validate(ConcatParams concatParams) throws ValidationFailedException {
		if (concatParams.getOutputFile() == null) {
			throw new ValidationFailedException("Please specify a correct output file");
		} else if (concatParams.getFiles().isEmpty()) {
			throw new ValidationFailedException("No Files Provided to Concatenate");
		} else if (StringUtils.isNotEmpty(concatParams.getDirectory())) {
			throw new ValidationFailedException(
					"Directory param is set, when File List conccat type is set. These are mutually exclusive");
		}

		for (File file : concatParams.getFiles()) {
			if (!file.exists()) {
				throw new ValidationFailedException(String.format("%s does not exist!", file.getAbsolutePath()));
			}
		}
	}

}