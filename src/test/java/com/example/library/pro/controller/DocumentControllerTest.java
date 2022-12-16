package com.example.library.pro.controller;

import com.example.library.pro.module.Book;
import com.example.library.pro.vo.BookVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@SpringBootTest
public class DocumentControllerTest {

    @Autowired
    DocumentController documentController;

    @Test
    public void insertBook(){
        BookVo bookVo = new BookVo();
        bookVo.setAuthorId(385l);
        bookVo.setISBN("isbn467356");
        bookVo.setTitle("书籍3");
        bookVo.setPublisherId(386l);
        bookVo.setPublicationDate(LocalDateTime.now());
        ResponseEntity<Book> res = documentController.addBook(bookVo);
    }

}
