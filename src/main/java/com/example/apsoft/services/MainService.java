package com.example.apsoft.services;

import com.example.apsoft.entities.Schema;
import com.example.apsoft.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

import static com.example.apsoft.scripts.Script.convert;
import static com.example.apsoft.scripts.Script.createJsonFromFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainService {
    private final FileRepository repository;

    // Сохранить в базу данных полученные из файла данные и вернуть
    // полученную в результате обработанную информацию (Json)
    public Map<String, Map> createJsonAndGetData(MultipartFile file) throws IOException {
        var convertFile = convert(file);
        var json = createJsonFromFile(convertFile);
        convertFile.delete();
        log.info("Create json from file: " + file.getOriginalFilename());
        return json;
    }

    // Сохранить в базу данных данные полученные из файла и вернуть
    // id - под которым можно получить эту запись из БД
    public Long createJsonAndGetId(MultipartFile file) throws IOException {
        var schema = new Schema(file.getBytes());
        repository.save(schema);
        log.info("Create json from file: " + file.getOriginalFilename());
        return schema.getId();
    }

    // Получить по id обработанный Json из БД
    @Cacheable(value = "json")
    public Map<String, Map> getJsonById(Long id) throws IOException {
        var tempFile = new File("tempFile");
        FileUtils.writeByteArrayToFile(tempFile, repository.findById(id).get().getContext());
        var resultJson = createJsonFromFile(tempFile);
        tempFile.delete();
        log.info("Get json by id: " + id);
        return resultJson;
    }
}
