package com.example.library.pro.dao;

import com.example.library.pro.module.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentDao extends JpaRepository<Document,Long> {
     List<Document> findByTitleLike(String title);
}
