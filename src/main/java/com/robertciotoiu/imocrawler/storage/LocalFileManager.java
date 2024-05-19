package com.robertciotoiu.imocrawler.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
public class LocalFileManager implements HtmlManager {
    @Value("${local.storage.file.path}")
    String filePath;

    @Override
    public void store(String fileName, String html) {
        var path = Path.of(filePath, fileName);

        try {
            FileWriter fileWriter = new FileWriter(path.toString());
            fileWriter.write(html);
            fileWriter.close();
            System.out.println(STR."HTML file saved successfully to: \{path}");
        } catch (IOException e) {
            System.out.println(STR."An error occurred while saving the HTML file.\{e}");
        }
    }

    @Override
    public String retrieve(String fileName) {
        var path = Path.of(filePath, fileName);
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            System.out.println(STR."An error occurred while reading the HTML file.\{e}");
            throw new RuntimeException("An error occurred while reading the HTML file.", e);
        }
    }

    @Override
    public List<String> retrieveAll() {
        List<String> htmlFilesContent = new ArrayList<>();
        File folder = new File(filePath);
        File[] files = folder.listFiles((_, name) -> name.toLowerCase().endsWith(".html"));

        if (files != null) {
            for (File file : files) {
                try {
                    String fileContent = new String(Files.readAllBytes(file.toPath()));
                    htmlFilesContent.add(fileContent);
                } catch (IOException e) {
                    System.out.println(STR."An error occurred while reading HTML file: \{file.getName()}. \{e}");
                }
            }
        }
        return htmlFilesContent;
    }
}
