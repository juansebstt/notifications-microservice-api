package com.notificationsmicroserviceapi.util;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;

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

    

}
