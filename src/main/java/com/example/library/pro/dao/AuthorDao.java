package com.example.library.pro.dao;

import com.example.library.pro.module.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorDao extends JpaRepository<Author,Long> {
}
