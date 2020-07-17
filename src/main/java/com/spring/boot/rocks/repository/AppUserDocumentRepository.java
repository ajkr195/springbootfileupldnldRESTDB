package com.spring.boot.rocks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.boot.rocks.model.AppUserDocument;

@Repository
public interface AppUserDocumentRepository extends JpaRepository<AppUserDocument, String> {

}
