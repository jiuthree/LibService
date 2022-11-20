package com.example.library.pro.dao;

import com.example.library.pro.module.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDao extends JpaRepository<Book,Long> {
}
