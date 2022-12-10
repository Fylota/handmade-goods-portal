package hu.bme.edu.handmade.web.dto.error;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(final String message) {
        super(message);
    }
}
