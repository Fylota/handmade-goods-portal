package hu.bme.edu.handmade.services;

import hu.bme.edu.handmade.models.Newsletter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

public interface INewsletterStorageService {

    Newsletter store(MultipartFile file) throws IOException;

    Stream<Newsletter> loadAll();

    Newsletter load(Long id);

}
