package com.example.library.pro.dao;

import com.example.library.pro.module.LibDocuments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibDocumentsDao extends JpaRepository<LibDocuments,Long> {
    List<LibDocuments> findByLibIdAndDocumentId(Long libId,Long documentId );
}
