package com.example.library.pro.controller;

import com.example.library.pro.module.Book;
import com.example.library.pro.module.ConferenceProceeding;
import com.example.library.pro.module.Document;
import com.example.library.pro.module.LibDocuments;
import com.example.library.pro.service.DocumentService;
import com.example.library.pro.vo.BookVo;
import com.example.library.pro.vo.ConferenceProceedingVo;
import com.example.library.pro.vo.DocumentDetailVo;
import com.example.library.pro.vo.JournalVo;
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
    public ResponseEntity<Document> getById(@RequestParam Long id) {
        return ResponseEntity.ok(documentService.getById(id));
    }

    @ApiOperation(value = "查询所有的在线文档")
    @GetMapping("/all")
    public ResponseEntity<List<Document>> findAll() {
        return ResponseEntity.ok(documentService.findAll());
    }

    @ApiOperation(value = "根据标题模糊查询所有的在线文档")
    @GetMapping("/findByTitleLike")
    public ResponseEntity<List<Document>> findByTitle(@RequestParam String title) {
        return ResponseEntity.ok(documentService.findByTitleLike(title));
    }

    @ApiOperation(value = "根据发布者名称模糊查询所有的在线文档")
    @GetMapping("/findByPublisherName")
    public ResponseEntity<List<Document>> findByPublisherName(@RequestParam String publisherName) {
        return ResponseEntity.ok(documentService.findByPublisherName(publisherName));
    }

    @ApiOperation(value = "查询文档全部详情")
    @GetMapping("/detail")
    public ResponseEntity<DocumentDetailVo> getDetailById(@RequestParam Long id) {
        return ResponseEntity.ok(documentService.getDetailById(id));
    }


    @ApiOperation(value = "查询文档在图书馆的馆藏情况")
    @GetMapping("/get/library/inventory")
    public ResponseEntity<List<LibDocuments>> getLibDocumentsById(@RequestParam Long libId, @RequestParam Long documentId) {
        return ResponseEntity.ok(documentService.getLibDocumentsById(libId, documentId));
    }




    @ApiOperation(value = "添加一个会议纪要")
    @PostMapping("/add/conference-proceeding")
    public ResponseEntity<ConferenceProceeding> addConferenceProceeding(@RequestBody ConferenceProceedingVo conferenceProceedingVo) {
        return ResponseEntity.ok(documentService.addConferenceProceedingVo(conferenceProceedingVo));
    }


    @ApiOperation(value = "添加一个书籍")
    @PostMapping("/add/book")
    public ResponseEntity<Book> addBook(@RequestBody BookVo bookVo) {
        return ResponseEntity.ok(documentService.addBookVo(bookVo));
    }

    @ApiOperation(value = "添加一个journal")
    @PostMapping("/add/journal")
    public ResponseEntity<JournalVo> addJournal(@RequestBody JournalVo journalVo) {
        return ResponseEntity.ok(documentService.addJournalVo(journalVo));
    }





}
