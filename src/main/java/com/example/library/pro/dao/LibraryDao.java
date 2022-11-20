package com.example.library.pro.dao;

import com.example.library.pro.module.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryDao extends JpaRepository<Library,Long> {
}
