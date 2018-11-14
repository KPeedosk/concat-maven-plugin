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
package io.github.flaw101.concat.validate;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import io.github.flaw101.concat.ConcatParams;
import io.github.flaw101.concat.service.ConcatenationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validates the possible types of {@link ConcatenationType}'s.
 *
 * @author Darren Forsythe
 * @since 1.1.0
 */
public class ValidatorService {

    private static final Logger logger = LoggerFactory.getLogger(ValidatorService.class);

    private final Validator directoryValidator;
    private final Validator fileListValidator;

    @Inject
    public ValidatorService(@Named("Directory") final Validator directoryValidator,
                            @Named("FileList") final Validator fileListValidator) {
        this.directoryValidator = directoryValidator;
        this.fileListValidator = fileListValidator;
    }

    public void validate(final ConcatParams concatParams)
            throws ValidationFailedException {
        ConcatenationType concatenationType = concatParams.getConcatenationType();
        logger.info("Validating params for concatenation type - {}", concatenationType);
        switch (concatenationType) {
            case DIRECTORY:
            case SEMVER:
                directoryValidator.validate(concatParams);
                break;
            case FILE_LIST:
                fileListValidator.validate(concatParams);
                break;
            default:
                throw new IllegalArgumentException("Concatenation Type not implemented");
        }
    }
}
