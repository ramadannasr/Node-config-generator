package pl.helwan.fci.file;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.helwan.fci.logger.CustomLoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Storage {
    private static Storage INSTANCE;
    
    public String storeFile(String destination, String context, String fileName) {
        return storeFile(destination, new ByteArrayInputStream(context.getBytes()), fileName);
    }

    private String storeFile(String destination, InputStream inputStream, String fileName) {
        Path fileStorageLocation = Paths.get(destination).toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception ex) {
            CustomLoggerFactory
                    .getInstance().getLogger().logError(ex,"Could not create the directory where the uploaded files will be stored.");
        }

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                CustomLoggerFactory.getInstance().getLogger().logError("Sorry! Filename contains invalid path sequence " + fileName);
                return null;
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return targetLocation.toString();
        } catch (IOException ex) {
            CustomLoggerFactory.getInstance().getLogger().logError(ex, "Could not store file " + fileName + ". Please try again!");
        }

        return null;
    }

    public static Storage getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Storage();
        }

        return INSTANCE;
    }

}
