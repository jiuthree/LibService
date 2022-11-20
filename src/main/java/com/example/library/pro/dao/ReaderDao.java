package com.example.library.pro.dao;

import com.example.library.pro.module.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReaderDao extends JpaRepository<Reader,Long> {

    Optional<Reader> findByPhoneNumber(String phoneNumber);


}
