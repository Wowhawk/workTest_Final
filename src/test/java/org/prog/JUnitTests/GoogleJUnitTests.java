package org.prog.JUnitTests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.prog.pages.GooglePage;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class GoogleJUnitTests {

    private static GooglePage googlePage;

    @BeforeAll
    public static void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        googlePage = new GooglePage(driver);
        driver.manage().window().maximize();
    }
    @BeforeEach
    public void loadPage() {
        googlePage.loadPage();
        googlePage.acceptCookiesIfPresent();
        googlePage.wait10Sec();
    }
    @Test
    public void googlePageTest() {
        googlePage.setSearchValue("aaaaaa");
        googlePage.performSearch();
        googlePage.clickFirstSearchResult();
        googlePage.loadPage();
        googlePage.clearSearchInput();
        googlePage.setSearchValue("test");
        googlePage.performSearch();
        googlePage.checkSearchResult("test");
    }

    @Test
    public void luckySearchTest() {
        googlePage.setSearchValue("aaaaaa");
        googlePage.clearSearchInput();
        googlePage.setSearchValue("test");
        googlePage.feelingLuckySearch();
        Assertions.assertTrue(googlePage.getCurrentUrl().equals("https://www.speedtest.net/"));
    }
    @Test
    public void googlePageTest1() {
        googlePage.setSearchValue("test");
        googlePage.performSearch();
        googlePage.alertChangeLang();//якщо цього не додати, то
        // Assertions.assertTrue(googlePage.getSearchHeaders().contains("Test")); буде false
        System.out.println(googlePage.getSearchHeaders());
        //Assertions.assertTrue(googlePage.getSearchHeaders().contains("Test"));
        System.out.println(googlePage.getSearchHeaders().contains("Test"));
        System.out.println("Search headers contain \"Test\": " + googlePage.getSearchHeaders().contains("Test"));
        System.out.println("Formatted search headers: " + googlePage.getSearchHeaders().toString());
        List<String> searchHeaders = googlePage.getSearchHeaders();
        assertTrue(searchHeaders.contains("Test"));
    }
    //Змінити мову через швидкі налаштування (після пошуку)
    @Test
    public void changeLang() {
        googlePage.setSearchValue("test");
        googlePage.performSearch();
        googlePage.wait10Sec();
        googlePage.openQuickSettings();
        googlePage.putChangeLangBtn();
        googlePage.selectLang("English");
        googlePage.saveSettingsResults();
    }
    @AfterEach
    public void wrapUp() {
        System.out.println("======================================");
    }

    @AfterAll
    public static void tearDown() {
        googlePage.quitDriver();
    }
}


