package com.example.library.pro.controller;

import com.example.library.pro.module.Document;
import com.example.library.pro.module.LibDocuments;
import com.example.library.pro.service.DocumentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/document")
public class DocumentController {

    @Autowired
    DocumentService documentService;

    @ApiOperation(value = "查询文档")
    @GetMapping("")
    public ResponseEntity<Document> getById(@RequestParam Long id){
        return ResponseEntity.ok(documentService.getById(id));
    }

    @ApiOperation(value = "查询文档在图书馆的馆藏情况")
    @GetMapping("/get/library/inventory")
    public ResponseEntity<List<LibDocuments>> getLibDocumentsById(@RequestParam Long libId, @RequestParam Long documentId){
        return ResponseEntity.ok(documentService.getLibDocumentsById(libId,documentId));
    }


    @ApiOperation(value = "添加一个新的图书馆库存")
    @GetMapping("/update/library/inventory")
    public ResponseEntity<LibDocuments> addLibDocuments(@RequestParam Long libId, @RequestParam Long documentId ){
        return ResponseEntity.ok(documentService.addLibDocuments(libId,documentId));
       //需要自动生成新的文档编号  传参是图书馆id和文档id，number是自动生成的，totalNumber也是查询出来的

    }

}
