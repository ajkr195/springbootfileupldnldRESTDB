package com.spring.boot.rocks.controller;

import com.spring.boot.rocks.model.AppUserDocument;
import com.spring.boot.rocks.payload.AppUserDocumentUploadResponse;
import com.spring.boot.rocks.service.AppUserDocumentStorageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AppUserDocumentRestController {

    private static final Logger logger = LoggerFactory.getLogger(AppUserDocumentRestController.class);

    @Autowired
    private AppUserDocumentStorageService appUserDocumentStorageService;

    
    @PostMapping("/uploadFile")
    public AppUserDocumentUploadResponse uploadFile(@RequestParam("file") MultipartFile file) {
        AppUserDocument dbFile = appUserDocumentStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        return new AppUserDocumentUploadResponse(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<AppUserDocumentUploadResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        AppUserDocument appUserDocument = appUserDocumentStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(appUserDocument.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + appUserDocument.getFileName() + "\"")
                .body(new ByteArrayResource(appUserDocument.getData()));
    }

}
