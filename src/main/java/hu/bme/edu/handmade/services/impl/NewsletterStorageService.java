package hu.bme.edu.handmade.services.impl;

import hu.bme.edu.handmade.models.Newsletter;
import hu.bme.edu.handmade.repositories.NewsletterRepository;
import hu.bme.edu.handmade.services.INewsletterStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class NewsletterStorageService implements INewsletterStorageService {

    private final NewsletterRepository newsletterRepository;

    public NewsletterStorageService(NewsletterRepository newsletterRepository) {
        this.newsletterRepository = newsletterRepository;
    }

    @Override
    public Newsletter store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Newsletter newsletter = new Newsletter(fileName, file.getBytes());

        return newsletterRepository.save(newsletter);
    }

    @Override
    public Stream<Newsletter> loadAll() {
        Spliterator<Newsletter> spliterator = newsletterRepository.findAll().spliterator();
        return StreamSupport.stream(spliterator, false);
    }

    @Override
    public Newsletter load(Long id) {
        return newsletterRepository.findById(id).get();
    }
}
