package com.example.library.pro.service;

import com.example.library.pro.dao.PublisherDao;
import com.example.library.pro.exception.RequestException;
import com.example.library.pro.module.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {
    @Autowired
    PublisherDao publisherDao;

    public Publisher getById(Long id) {
        Optional<Publisher> res = publisherDao.findById(id);
        if (!res.isPresent()) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "该发布者不存在");
        }
        return res.get();
    }

    public List<Publisher> findAll() {
        return publisherDao.findAll();
    }

    public Publisher add(Publisher publisher) {
        return publisherDao.save(publisher);
    }

    public Void deleteById(Long id) {
        if (!publisherDao.existsById(id)) {
            throw new RequestException(HttpStatus.BAD_REQUEST, "该发布者不存在");
        }
        publisherDao.deleteById(id);

        return null;
    }
}
