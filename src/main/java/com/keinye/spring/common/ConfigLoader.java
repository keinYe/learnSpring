package com.keinye.spring.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ConfigLoader {
    public static String loadConf(String path) throws FileNotFoundException {
        final BufferedReader reader;
        if (path.startsWith("classpath:")) {
            String parsedPath = path.replace("classpath:", "");
            InputStream is = ConfigLoader.class.getResourceAsStream(parsedPath);
            InputStreamReader isr = new InputStreamReader(is);
            reader = new BufferedReader(isr);
        } else {
            reader = new BufferedReader(new FileReader(new File(path)));
        }

        return reader.lines().collect(Collectors.joining("\n"));
    }
}
