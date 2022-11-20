package com.example.library.pro.controller;

import com.example.library.pro.dao.PublisherDao;
import com.example.library.pro.module.Library;
import com.example.library.pro.module.Publisher;
import com.example.library.pro.service.PublisherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/publisher")
public class PublisherController {

    @Autowired
    PublisherService publisherService;

    @ApiOperation(value = "查询发布者")
    @GetMapping("")
    public ResponseEntity<Publisher> getById(@RequestParam Long id){
        return ResponseEntity.ok(publisherService.getById(id));
    }


    @ApiOperation(value = "查询发布者列表")
    @GetMapping("/get/all")
    public ResponseEntity<List<Publisher>> getAll(){
        return ResponseEntity.ok(publisherService.findAll());
    }

    @ApiOperation(value = "添加一个发布者")
    @PostMapping("/add")
    public ResponseEntity<Publisher> add(@RequestBody Publisher publisher){
        return ResponseEntity.ok(publisherService.add(publisher));
    }

    @ApiOperation(value = "删除一个发布者")
    @GetMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam Long id){
        return ResponseEntity.ok(publisherService.deleteById(id));
    }
}
