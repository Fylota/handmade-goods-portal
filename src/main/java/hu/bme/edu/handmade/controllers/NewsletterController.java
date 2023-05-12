package hu.bme.edu.handmade.controllers;

import hu.bme.edu.handmade.models.Newsletter;
import hu.bme.edu.handmade.models.response.ResponseFile;
import hu.bme.edu.handmade.models.response.ResponseMessage;
import hu.bme.edu.handmade.services.INewsletterStorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins="http://localhost:4200", maxAge=3600)
@RequestMapping("/newsletter")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class NewsletterController {
    private final INewsletterStorageService storageService;

    public NewsletterController(INewsletterStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message;
        try {
            storageService.store(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = storageService.loadAll().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/newsletter/files/")
                    .path(dbFile.getId().toString())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getTitle(),
                    fileDownloadUri,
                    dbFile.getContent().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        Newsletter fileDB = storageService.load(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getTitle() + "\"")
                .body(fileDB.getContent());
    }
}
