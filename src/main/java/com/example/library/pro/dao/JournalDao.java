package com.example.library.pro.dao;

import com.example.library.pro.module.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalDao extends JpaRepository<Journal,Long> {
}
