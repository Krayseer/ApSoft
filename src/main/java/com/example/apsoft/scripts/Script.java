package com.example.apsoft.scripts;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

public class Script {

    // Функция для построения JSON из файла, используются Map вложенные в Map
    public static Map<String, Map> createJsonFromFile(File file) throws IOException {
        Map<String, Map> map = new HashMap<>();
        ArrayList<Map<String, Map>> hashTable = new ArrayList<>(List.of(map));
        BufferedReader reader = new BufferedReader(new FileReader(file));

        for(String str : reader.lines().toList()){
            int countGrid = getCountGrid(str);
            String text = getStringWithoutGrid(str);

            if(countGrid + 1 > hashTable.size())
                addNewChapterInStructure(hashTable, text);

            else if(countGrid == 0){
                hashTable.get(hashTable.size() - 1).put(text, new HashMap<>());
            }

            else if (countGrid < hashTable.size()){
                hashTable = new ArrayList<>(hashTable.subList(0, countGrid));
                addNewChapterInStructure(hashTable, text);
            }
        }

        reader.close();
        return map;
    }

    // Получить количество решеток в начале
    public static Integer getCountGrid(String value){
        int count = 0;
        for(Character ch : value.toCharArray()){
            if(ch == '#') count++;
            else break;
        }
        return count;
    }

    // Получить строку без решеток
    public static String getStringWithoutGrid(String string){
        return string.substring(getCountGrid(string));
    }

    // Добавление на последний элемент листа структуры "Map"
    public static void addNewChapterInStructure(ArrayList<Map<String, Map>> hashTable, String text){
        var lastElement = hashTable.size() - 1;
        hashTable.get(lastElement).put(text, new HashMap<>());
        hashTable.add(hashTable.get(lastElement).get(text));
    }

    // Конвертация MultipartFile в File
    public static File convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertFile);
        fos.write(file.getBytes());
        fos.close();
        return convertFile;
    }
}
