package io.github.flaw101.concat.filewriter.setup;

import io.github.flaw101.concat.ConcatParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class StartingFileHandler {

    private static final Logger logger = LoggerFactory.getLogger(StartingFileHandler.class);

    public void setStartingFileToStartOfFiles(ConcatParams params, List<File> listFiles) {
        if (params.getStartingFile() != null) {
            Iterator<File> fileIterator = listFiles.iterator();
            File file;
            logger.debug("Starting File not null. Placing - {} at start of list",
                    params.getStartingFile());
            while (fileIterator.hasNext()) {
                file = fileIterator.next();
                if (file.getAbsolutePath()
                        .equals(params.getStartingFile().getAbsolutePath())) {
                    logger.debug("Removing file to file place at start of list - {}",
                            file);
                    fileIterator.remove();
                }
            }
            listFiles.add(0, params.getStartingFile());
        }
    }
}
