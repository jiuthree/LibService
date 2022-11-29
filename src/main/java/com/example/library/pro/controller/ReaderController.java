package com.example.library.pro.controller;

import com.example.library.pro.dto.LoginDto;
import com.example.library.pro.module.LibDocuments;
import com.example.library.pro.module.Reader;
import com.example.library.pro.module.ReserveAndBorrowList;
import com.example.library.pro.service.ReaderService;
import com.example.library.pro.vo.ReaderVo;
import com.example.library.pro.vo.ReserveAndBorrowDetailVo;
import com.example.library.pro.vo.ReserveVo;
import com.example.library.pro.vo.ReturnVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/reader")
public class ReaderController {
    @Autowired
    ReaderService readerService;

    @ApiOperation(value = "新读者注册", httpMethod = "POST")
    @PostMapping("/register")
    public ResponseEntity<Reader> registerReader(@RequestBody ReaderVo readerVo) {
        return ResponseEntity.ok(readerService.registerReader(readerVo));
    }

    @ApiOperation(value = "管理员登录", httpMethod = "POST")
    @PostMapping("/admin/login")
    public ResponseEntity<Reader> adminLogin(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(readerService.login(loginDto.getId(), loginDto.getPassword()));
    }

    @ApiOperation(value = "读者登录", httpMethod = "GET")
    @GetMapping("/reader/login")
    public ResponseEntity<Reader> readerLogin(@RequestParam Long cardNumber) {
        return ResponseEntity.ok(readerService.readerLogin(cardNumber));
    }



    @ApiOperation(value = "老读者注销")
    @GetMapping("/cancel-register")
    public ResponseEntity<Void> cancelRegister(@RequestParam Long id) {
        readerService.cancelRegister(id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "查询读者")
    @GetMapping("")
    public ResponseEntity<Reader> getReaderById(Long id) {
        return ResponseEntity.ok(readerService.getReaderById(id));
    }

    @ApiOperation(value = "读者预定document")
    @PostMapping("/reserve/document")
    public ResponseEntity<ReserveVo> reserveDocument(@RequestBody ReserveVo reserveVo) {

        return  ResponseEntity.ok(readerService.reserveDocument(reserveVo));
    }

    @ApiOperation(value = "读者借用document")
    @GetMapping("/borrow/document")
    public ResponseEntity<ReserveAndBorrowList> borrowDocument(@RequestParam Long readerId,@RequestParam Long documentId,@RequestParam Long libId,@RequestParam Long number) {

        return  ResponseEntity.ok(readerService.borrowDocument(readerId,documentId,libId,number));
    }

    @ApiOperation(value = "读者还书document")
    @GetMapping("/return/document")
    public ResponseEntity<ReturnVo> returnDocument(@RequestParam Long readerId, @RequestParam Long documentId, @RequestParam Long libId, @RequestParam Long number) {

        return  ResponseEntity.ok(readerService.returnDocument(readerId,documentId,libId,number));
    }

    @ApiOperation(value = "查询读者借阅的文档列表")
    @GetMapping("/list/documents")
    public ResponseEntity<List<ReserveAndBorrowDetailVo>> getBorrowedDocumentsByReaderId(Long readerId) {
        return ResponseEntity.ok(readerService.getBorrowedDocumentsByReaderId(readerId));
    }

}
