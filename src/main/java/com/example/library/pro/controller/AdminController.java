package com.example.library.pro.controller;

import com.example.library.pro.module.Reader;
import com.example.library.pro.service.AdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

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



}
