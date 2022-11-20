package com.example.library.pro.controller;

import com.example.library.pro.dto.LoginDto;
import com.example.library.pro.module.Reader;
import com.example.library.pro.service.ReaderService;
import com.example.library.pro.vo.ReaderVo;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "登录", httpMethod = "POST")
    @PostMapping("/login")
    public ResponseEntity<Reader> registerReader(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(readerService.login(loginDto.getPhoneNumber(),loginDto.getPassword()));
    }


    @ApiOperation(value = "老读者注销")
    @GetMapping("/cancel-register")
    public ResponseEntity<Void> cancelRegister(@RequestParam Long id) {
        readerService.cancelRegister(id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "查询读者")
    @GetMapping("")
    public ResponseEntity<Reader> getReaderById(Long id){
        return ResponseEntity.ok(readerService.getReaderById(id));
    }


}
