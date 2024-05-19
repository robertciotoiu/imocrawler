package com.robertciotoiu.imocrawler.publi24;

import com.robertciotoiu.imocrawler.scheduler.NaturalScheduled;
import com.robertciotoiu.imocrawler.storage.LocalFileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Publi24Crawler {
    private final static String ROOT_URL = "https://www.publi24.ro/anunturi/imobiliare/de-vanzare/apartamente/timis/timisoara/?minprice=10000&maxprice=45000";
    private final Publi24Controller publi24Controller = new Publi24Controller(ROOT_URL);
    private final LocalFileManager localFileManager;

    public Publi24Crawler(@Autowired LocalFileManager localFileManager) {
        this.localFileManager = localFileManager;
    }


    @NaturalScheduled(min_frequency = 1_000, max_frequency = 2_000, disabled = true)
    public void execute() throws InterruptedException {
        publi24Controller.openPage();
        publi24Controller.solveCookies();
    }

    public void indexWholeWebsite() throws InterruptedException {
        var arePagesLeft = true;
        var pageNumber = 1;

        // Open page and solve cookies
        publi24Controller.openPage();
        publi24Controller.solveCookies();

        // While there is a next page to parse
        while (arePagesLeft) {
            // Get and store the html
            var html = publi24Controller.getHtml();
            // composed out of pageNumber_timestamp
            var fileName = STR."\{pageNumber++}_\{LocalDateTime.now()}";

            localFileManager.store(fileName, html);

            System.out.println(STR."Successfully indexed page number: \{pageNumber} with URL: \{publi24Controller.getUrl()}");

            // Navigate to the next page
            arePagesLeft = publi24Controller.nextPage();
        }

        System.out.println(STR."Successfully indexed \{pageNumber - 1} pages from Publi24.com");
    }
}
