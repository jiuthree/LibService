package com.example.library.pro.service;

import com.example.library.pro.dao.AuthorDao;
import com.example.library.pro.exception.RequestException;
import com.example.library.pro.module.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {


    @Autowired
    AuthorDao authorDao;

    public Author getById(Long id) {
        Optional<Author> res = authorDao.findById(id);
        if (!res.isPresent()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "该作者不存在");
        }
        return res.get();
    }

    public List<Author> findAll() {
        return authorDao.findAll();
    }

    public Author add(Author author) {
        return authorDao.save(author);
    }

    public Void deleteById(Long id) {
        if (!authorDao.existsById(id)) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "该作者不存在");
        }
        authorDao.deleteById(id);

        return null;
    }

}
