package com.example.library.pro.controller;

import com.example.library.pro.module.LibDocuments;
import com.example.library.pro.module.Reader;
import com.example.library.pro.service.AdminService;
import com.example.library.pro.service.DocumentService;
import com.example.library.pro.vo.LibDocumentDetailVo;
import com.example.library.pro.vo.LibraryTop10Documents;
import com.example.library.pro.vo.LibraryTop10Readers;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    DocumentService documentService;

    @ApiOperation(value = "查询所有读者")
    @GetMapping("/get/all/readers")
    public ResponseEntity<List<Reader>> getAllReaders() {
        return ResponseEntity.ok(adminService.getAllReaders());
    }

    @ApiOperation(value = "查询时间信息")
    @GetMapping("/time")
    public ResponseEntity<LocalDateTime> getTime() {
        return ResponseEntity.ok(LocalDateTime.now());
    }

    @ApiOperation(value = "添加一个新的图书馆库存")
    @GetMapping("/update/library/inventory")
    public ResponseEntity<LibDocuments> addLibDocuments(@RequestParam Long libId, @RequestParam Long documentId) {
        return ResponseEntity.ok(documentService.addLibDocuments(libId, documentId));
        //需要自动生成新的文档编号  传参是图书馆id和文档id，number是自动生成的，totalNumber也是查询出来的

    }

    @ApiOperation(value = "查询图书馆库存及其状态")
    @GetMapping("/search/documentCopy")
    public ResponseEntity<List<LibDocumentDetailVo>> searchDocumentCopyStatus(@RequestParam Long documentId, @RequestParam Long number) {
        return ResponseEntity.ok(adminService.searchDocumentCopyStatus(documentId,number));

    }

    @ApiOperation(value = "打印每个图书馆分区排行前10的借书者和他接取书籍的数目")
    @GetMapping("/library/top10/readerAndDocumentNums")
    public ResponseEntity<List<LibraryTop10Readers>> findTop10Readers() {
        return ResponseEntity.ok(adminService.findTop10Readers());

    }

    @ApiOperation(value = "打每个图书馆分区排行前10的借的书籍")
    @GetMapping("/library/top10/documents")
    public ResponseEntity<List<LibraryTop10Documents>> findTop10Documents() {
        return ResponseEntity.ok(adminService.findTop10Documents());

    }


}
