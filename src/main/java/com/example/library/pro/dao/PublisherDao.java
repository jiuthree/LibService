package com.example.library.pro.dao;

import com.example.library.pro.module.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface PublisherDao extends JpaRepository<Publisher,Long> {

    List<Publisher> findByNameLike(String name);

}
