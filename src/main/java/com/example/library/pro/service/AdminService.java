package com.example.library.pro.service;

import com.example.library.pro.dao.ReaderDao;
import com.example.library.pro.module.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    ReaderDao readerDao;

    public List<Reader> getAllReaders() {
        return readerDao.findAll();
    }
}
