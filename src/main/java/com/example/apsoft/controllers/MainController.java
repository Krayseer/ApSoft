package com.example.apsoft.controllers;

import com.example.apsoft.services.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/upload/file")
public class MainController {
    private final MainService service;

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Map>> getJsonFile(@PathVariable Long id) throws IOException {
        return ResponseEntity.ok(service.getJsonById(id));
    }

    // В параметрах запроса должен быть аттрибут "file" с выбранным файлом пользователя
    @PostMapping("/data")
    public ResponseEntity<Map<String, Map>> createAndGetData(@RequestParam("file") MultipartFile fileUpload)
            throws IOException {
        return ResponseEntity.ok(service.createJsonAndGetData(fileUpload));
    }

    @PostMapping("/id")
    public ResponseEntity<Long> createAndGetId(@RequestParam("file") MultipartFile fileUpload)
            throws IOException {
        return ResponseEntity.ok(service.createJsonAndGetId(fileUpload));
    }
}
