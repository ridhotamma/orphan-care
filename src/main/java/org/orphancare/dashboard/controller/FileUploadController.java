package org.orphancare.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.orphancare.dashboard.dto.UploadResponse;
import org.orphancare.dashboard.service.FileUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/public/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<UploadResponse> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        UploadResponse response = fileUploadService.uploadFile(file);
        return ResponseEntity.ok(response);
    }
}
