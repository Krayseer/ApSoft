package com.example.apsoft;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.apsoft.Pattern.equalFileInputWithExpected;
import static com.example.apsoft.scripts.Script.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
public class ScriptTest {
    @Test
    void getCountGridTest(){
        assertThat(getCountGrid("a")).isEqualTo(0);
        assertThat(getCountGrid("#a")).isEqualTo(1);
        assertThat(getCountGrid("##a")).isEqualTo(2);
        assertThat(getCountGrid("####a####")).isEqualTo(4);
        assertThat(getCountGrid("#")).isEqualTo(1);
        assertThat(getCountGrid("")).isEqualTo(0);
    }

    @Test
    void getStringWithoutGridTest(){
        assertThat(getStringWithoutGrid("##a")).isEqualTo("a");
        assertThat(getStringWithoutGrid("#abab")).isEqualTo("abab");
        assertThat(getStringWithoutGrid("without")).isEqualTo("without");
        assertThat(getStringWithoutGrid("")).isEqualTo("");
    }

    @Test
    void createJsonFromEmptyFile() throws IOException {
        List<String> input = new ArrayList<>();
        HashMap<String, Map> expected = new HashMap<>();
        assertThat(equalFileInputWithExpected(input, expected)).isTrue();
    }

    @Test
    void createJsonFromFileTest() throws IOException {
        var input = new ArrayList<>(List.of("#a"));
        HashMap<String, Map> expected = new HashMap<>();
        expected.put("a", new HashMap<>());
        assertThat(equalFileInputWithExpected(input, expected)).isTrue();
    }

    @Test
    void createJsonFromFileTest2() throws IOException {
        var input = new ArrayList<>(List.of("#a", "#b"));
        HashMap<String, Map> expected = new HashMap<>();
        expected.put("a", new HashMap<>());
        expected.put("b", new HashMap<>());
        assertThat(equalFileInputWithExpected(input, expected)).isTrue();
    }

    @Test
    void createJsonFromFileTest3() throws IOException {
        var input = new ArrayList<>(List.of("#a", "##b"));
        HashMap<String, Map> expected = new HashMap<>(), aMap = new HashMap<>(), bMap = new HashMap<>();
        expected.put("a", aMap);
        aMap.put("b", bMap);
        assertThat(equalFileInputWithExpected(input, expected)).isTrue();
    }

    @Test
    void createJsonFromFileTest4() throws IOException {
        var input = new ArrayList<>(List.of("#a", "##b", "###c", "##d"));
        HashMap<String, Map> expected = new HashMap<>(), aMap = new HashMap<>(), bMap = new HashMap<>(),
                cMap = new HashMap<>(), dMap = new HashMap<>();
        expected.put("a", aMap);
        aMap.put("b", bMap);
        bMap.put("c", cMap);
        aMap.put("d", dMap);
        assertThat(equalFileInputWithExpected(input, expected)).isTrue();
    }

    @Test
    void createJsonFromFileTestWithWordWithoutGrid() throws IOException {
        var input = new ArrayList<>(List.of("#a", "b", "##c", "#d"));
        HashMap<String, Map> expected = new HashMap<>(), aMap = new HashMap<>(), bMap = new HashMap<>(),
                cMap = new HashMap<>(), dMap = new HashMap<>();
        expected.put("a", aMap);
        aMap.put("b", bMap);
        aMap.put("c", cMap);
        expected.put("d", dMap);
        assertThat(equalFileInputWithExpected(input, expected)).isTrue();
    }
}
