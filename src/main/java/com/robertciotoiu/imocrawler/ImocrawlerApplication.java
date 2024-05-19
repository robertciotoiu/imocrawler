package com.robertciotoiu.imocrawler;

import com.robertciotoiu.imocrawler.publi24.Publi24Crawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImocrawlerApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(ImocrawlerApplication.class, args);
    }

    @Autowired
    private Publi24Crawler publi24Crawler;

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("Application started. Executing whole website indexer...");
        try {
            publi24Crawler.indexWholeWebsite();
        } catch (Exception e) {
            System.err.println(STR."Exception occurred: \{e}");
            System.exit(-1);
        }
    }
}
