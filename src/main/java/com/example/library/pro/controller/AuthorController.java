package com.example.library.pro.controller;

import com.example.library.pro.module.Author;
import com.example.library.pro.service.AuthorService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/author")
public class AuthorController {


    @Autowired
    AuthorService authorService;


    @ApiOperation(value = "查询作者")
    @GetMapping("")
    public ResponseEntity<Author> getById(@RequestParam Long id) {
        return ResponseEntity.ok(authorService.getById(id));
    }


    @ApiOperation(value = "查询作者列表")
    @GetMapping("/get/all")
    public ResponseEntity<List<Author>> getAll() {
        return ResponseEntity.ok(authorService.findAll());
    }

    @ApiOperation(value = "添加一个作者")
    @PostMapping("/add")
    public ResponseEntity<Author> add(@RequestBody Author author) {
        return ResponseEntity.ok(authorService.add(author));
    }

    @ApiOperation(value = "删除一个作者")
    @GetMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam Long id) {
        return ResponseEntity.ok(authorService.deleteById(id));
    }
}
