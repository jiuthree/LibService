package com.example.library.pro.service;

import com.example.library.pro.dao.LibraryDao;
import com.example.library.pro.exception.RequestException;
import com.example.library.pro.module.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    @Autowired
    LibraryDao libraryDao;


    public Library getById(Long id) {
        Optional<Library> res = libraryDao.findById(id);
        if (!res.isPresent()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "该图书馆不存在");
        }
        return res.get();
    }

    public List<Library> findAll() {
        return libraryDao.findAll();
    }
}
