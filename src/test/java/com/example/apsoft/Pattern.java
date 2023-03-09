package com.example.apsoft;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.example.apsoft.scripts.Script.createJsonFromFile;

public class Pattern {
    public static boolean equalFileInputWithExpected(List<String> input, Map<String, Map> expected) throws IOException {
        File tempFile = new File("tempFile");
        FileOutputStream fos = new FileOutputStream(tempFile);
        input.forEach(x -> {
            try {
                fos.write(x.getBytes());
                fos.write("\n".getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        fos.flush();
        fos.close();

        var received = createJsonFromFile(tempFile);
        tempFile.delete();
        return received.equals(expected);
    }
}
