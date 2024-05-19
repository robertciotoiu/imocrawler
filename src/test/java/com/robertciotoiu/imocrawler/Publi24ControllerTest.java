package com.robertciotoiu.imocrawler;

import com.robertciotoiu.imocrawler.publi24.Publi24Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Publi24ControllerTest {
    private static final String ROOT_URL = "https://www.publi24.ro/anunturi/imobiliare/de-vanzare/apartamente/timis/timisoara/?minprice=10000&maxprice=45000&pagesize=50";

    private static Publi24Controller publi24Controller;


    @BeforeEach
    void setUp() {
        publi24Controller = new Publi24Controller(ROOT_URL);
    }

    @Test
    void getHtml_shouldReturnHtmlPage_AndContainCookieText() throws InterruptedException {
        var html = publi24Controller.openPage();
        assertFalse(html.isEmpty());
        assertTrue(html.contains("Ne pasă de confidențialitatea ta"));
    }

    @Test
    void solveCookies_shouldMakeThePopUpDissappear() throws InterruptedException {
        var html = publi24Controller.openPage();
        assertFalse(html.isEmpty());
        publi24Controller.solveCookies();
        var htmlAfterAcceptCookies = publi24Controller.openPage();
        assertFalse(htmlAfterAcceptCookies.isEmpty());
        assertFalse(htmlAfterAcceptCookies.contains("Ne pasă de confidențialitatea ta"));
    }

    @Test
    void nextPage() throws InterruptedException {
        var html = publi24Controller.openPage();
        assertFalse(html.isEmpty());
        publi24Controller.solveCookies();
        var htmlAfterAcceptCookies = publi24Controller.openPage();
        assertFalse(htmlAfterAcceptCookies.isEmpty());
        assertFalse(htmlAfterAcceptCookies.contains("Ne pasă de confidențialitatea ta"));
        publi24Controller.nextPage();
    }

    @Test
    void whenAtLastPage_andLastPageIsClickedAgain_shouldThrownException() throws InterruptedException {
        var publi24Controller = new Publi24Controller("https://www.publi24.ro/anunturi/imobiliare/de-vanzare/apartamente/timis/timisoara/?minprice=10000&maxprice=45000&pag=24&_gl=1*1vvcv8e*_up*MQ..*_ga*NTIwNTY5MjYzLjE3MTU4MDA2NjQ.*_ga_RBV9YWH7LB*MTcxNTgwMDY2My4xLjAuMTcxNTgwMDY2My4wLjAuMA..");
        var html = publi24Controller.openPage();
        assertFalse(html.isEmpty());
        publi24Controller.solveCookies();
        var htmlAfterAcceptCookies = publi24Controller.openPage();
        assertFalse(htmlAfterAcceptCookies.isEmpty());
        assertFalse(htmlAfterAcceptCookies.contains("Ne pasă de confidențialitatea ta"));
        publi24Controller.nextPage();
    }

}