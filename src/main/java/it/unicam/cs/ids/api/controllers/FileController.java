package it.unicam.cs.ids.api.controllers;

import it.unicam.cs.ids.entities.Certificate;
import it.unicam.cs.ids.repositories.CertificateRepository;
import it.unicam.cs.ids.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for file operations, particularly for serving certificate files.
 */
@RestController
@RequestMapping("/files")
public class FileController {
    
    private final StorageService storageService;
    private final CertificateRepository certificateRepository;
    
    @Autowired
    public FileController(StorageService storageService, CertificateRepository certificateRepository) {
        this.storageService = storageService;
        this.certificateRepository = certificateRepository;
    }
    
    /**
     * Serves a file by filename for download.
     * @param filename the name of the file to serve
     * @return ResponseEntity containing the file resource
     */
    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Resource file = storageService.loadAsResource(filename);
            
            if (file.exists() && file.isReadable()) {
                return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Serves a certificate file by certificate ID.
     * @param certificateId the ID of the certificate
     * @return ResponseEntity containing the certificate file resource
     */
    @GetMapping("/certificate/{certificateId}")
    public ResponseEntity<Resource> serveCertificateFile(@PathVariable Long certificateId) {
        try {
            Certificate certificate = certificateRepository.findById(certificateId)
                .orElse(null);
            
            if (certificate == null || certificate.getCertificateUrl() == null) {
                return ResponseEntity.notFound().build();
            }
            
            // Extract filename from URL (remove '/api/files/' prefix)
            String filename = certificate.getCertificateUrl().replace("/api/files/", "");
            
            Resource file = storageService.loadAsResource(filename);
            
            if (file.exists() && file.isReadable()) {
                return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}