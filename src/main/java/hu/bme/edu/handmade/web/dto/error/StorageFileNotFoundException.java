package hu.bme.edu.handmade.web.dto.error;

public class StorageFileNotFoundException extends RuntimeException {
    public StorageFileNotFoundException(final String message) {
        super(message);
    }
}
