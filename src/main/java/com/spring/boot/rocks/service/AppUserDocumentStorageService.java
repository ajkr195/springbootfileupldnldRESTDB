package com.spring.boot.rocks.service;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.spring.boot.rocks.exception.FileStorageException;
import com.spring.boot.rocks.exception.MyFileNotFoundException;
import com.spring.boot.rocks.model.AppUserDocument;
import com.spring.boot.rocks.repository.AppUserDocumentRepository;

@Service
public class AppUserDocumentStorageService {

    @Autowired
    private AppUserDocumentRepository dbFileRepository;

    public AppUserDocument storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            AppUserDocument dbFile = new AppUserDocument(fileName, file.getContentType(), "MyNameGoesHere", new Date(),file.getBytes());

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public AppUserDocument getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }
}
