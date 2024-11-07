package com.notificationsmicroserviceapi.util;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Slf4j
public class FileUtilTools {

    public static File downloadS3ObjectToFile(S3ObjectInputStream s3ObjectInputStream, String fileName) throws IOException {

        File directory = new File("src/main/resources/static");
        File outputFile = new File(directory, fileName);

        try{
            FileUtils.copyInputStreamToFile(s3ObjectInputStream, outputFile);
            log.info("File created for: [{}]", fileName);
            return outputFile;
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        } finally {
            IOUtils.closeQuietly(s3ObjectInputStream);
        }
    }

    public static void deleteFile(String absolutePath) {
        Optional.ofNullable(absolutePath)
                .map(Path::of)
                .filter(Files::exists)
                .ifPresent(path -> {
                    try {
                        Files.deleteIfExists(path);
                        log.info("File Deleted: [{}]", path.getFileName());
                    } catch (IOException e) {
                        log.error("File not Deleted: [{}]", path.getFileName(), e);
                    }
                });
    }

    public static String readFileContent(String filePath) {
        try {
            File file = new File(filePath);
            return FileUtils.readFileToString(file);
        } catch (IOException e) {
            log.error("File buffered error: {}", filePath);
            return null;
        }
    }

    public static String getFileName(String filePath) {
        File file = new File(filePath);
        return file.getName();
    }

}
