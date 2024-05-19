package com.robertciotoiu.imocrawler.storage;

import java.util.List;

public interface HtmlManager {
    void store(String fileName, String html);

    String retrieve(String fileName);

    List<String> retrieveAll();

}
