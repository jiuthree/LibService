package com.example.library.pro.dao;

import com.example.library.pro.module.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentDao extends JpaRepository<Document,Long> {
}
