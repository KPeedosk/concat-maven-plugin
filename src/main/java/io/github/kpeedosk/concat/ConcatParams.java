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
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import io.github.kpeedosk.concat.service.ConcatenationType;

/**
 * Paramerters required for building the output file and validation.
 *
 * @author Karl Peedosk
 * @since 1.2.0
 */
public class ConcatParams {

    private final String directory;
    private final File outputFile;
    private final boolean deleteTargetFile;
    private final boolean appendNewline;
    private final boolean recursive;
    private final Collection<File> files = new LinkedList<File>();
    private final ConcatenationType concatenationType;
    private final File startingFile;

    public ConcatParams(String directory, Collection<File> files, File outputFile, boolean deleteTargetFile, boolean appendNewline, boolean recursive,
                        ConcatenationType concatenationType, File startingFile) {
        this.directory = directory;
        if (files != null) {
            this.files.addAll(files);
        }
        this.concatenationType = concatenationType;
        this.outputFile = outputFile;
        this.deleteTargetFile = deleteTargetFile;
        this.appendNewline = appendNewline;
        this.recursive = recursive;
        this.startingFile = startingFile;
    }

    public boolean isAppendNewline() {
        return appendNewline;
    }

    public boolean isRecursive() {
        return recursive;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public boolean isDeleteTargetFile() {
        return deleteTargetFile;
    }

    public ConcatenationType getConcatenationType() {
        return concatenationType;
    }

    public Collection<File> getFiles() {
        return Collections.unmodifiableCollection(files);
    }

    public void addAll(final Collection<File> files) {
        this.files.clear();
        this.files.addAll(files);
    }

    public void add(final File file) {
        this.files.add(file);
    }

    public String getDirectory() {
        return directory;
    }

    public File getStartingFile() {
        return startingFile;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("ConcatParams [directory=").append(directory).append(", outputFile=").append(outputFile).append(", deleteTargetFile=")
                .append(deleteTargetFile).append(", appendNewline=").append(appendNewline).append(", files=").append(files).append(", concatenationType=")
                .append(concatenationType).append(", startingFile=").append(startingFile).append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (appendNewline ? 1231 : 1237);
        result = prime * result + ((concatenationType == null) ? 0 : concatenationType.hashCode());
        result = prime * result + (deleteTargetFile ? 1231 : 1237);
        result = prime * result + ((directory == null) ? 0 : directory.hashCode());
        result = prime * result + ((files == null) ? 0 : files.hashCode());
        result = prime * result + ((outputFile == null) ? 0 : outputFile.hashCode());
        result = prime * result + ((startingFile == null) ? 0 : startingFile.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ConcatParams)) {
            return false;
        }
        final ConcatParams other = (ConcatParams) obj;
        if (appendNewline != other.appendNewline) {
            return false;
        }
        if (concatenationType != other.concatenationType) {
            return false;
        }
        if (deleteTargetFile != other.deleteTargetFile) {
            return false;
        }
        if (directory == null) {
            if (other.directory != null) {
                return false;
            }
        } else if (!directory.equals(other.directory)) {
            return false;
        }
        if (other.files != null) {
            return false;

        } else if (!files.equals(other.files)) {
            return false;
        }
        if (outputFile == null) {
            if (other.outputFile != null) {
                return false;
            }
        } else if (!outputFile.equals(other.outputFile)) {
            return false;
        }
        if (startingFile == null) {
            if (other.startingFile != null) {
                return false;
            }
        } else if (!startingFile.equals(other.startingFile)) {
            return false;
        }
        return true;
    }
}
