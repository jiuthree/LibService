package com.example.library.pro.controller;


import com.example.library.pro.module.Document;
import com.example.library.pro.module.Library;
import com.example.library.pro.service.LibraryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/api/library")
public class LibraryController {
    @Autowired
    LibraryService libraryService;

    @ApiOperation(value = "查询图书馆")
    @GetMapping("")
    public ResponseEntity<Library> getById(@RequestParam Long id){
        return ResponseEntity.ok(libraryService.getById(id));
    }


    @ApiOperation(value = "查询图书馆列表")
    @GetMapping("/get/all")
    public ResponseEntity<List<Library>> getAll(){
        return ResponseEntity.ok(libraryService.findAll());
    }



}
