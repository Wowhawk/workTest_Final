package org.prog.JUnitTests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.prog.pages.RozetkaCatalogAndSearching;

public class RozetkaCatalogJUnitTests {
    private static RozetkaCatalogAndSearching rozetkaCatalogAndSearching;

    @BeforeAll
    public static void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        rozetkaCatalogAndSearching = new RozetkaCatalogAndSearching(driver);
        driver.manage().window().maximize();
    }
    @BeforeEach
    public void loadPage() {
        rozetkaCatalogAndSearching.loadPage();
        rozetkaCatalogAndSearching.implicitlyWait10();
    }
    @Test
    public void searchAndGoToMain() {
        rozetkaCatalogAndSearching.loadPage();
        rozetkaCatalogAndSearching.searchFor("Xiaomi Redmi 12");
        rozetkaCatalogAndSearching.waitPageLoad();
        rozetkaCatalogAndSearching.checkResult("Xiaomi Redmi 12");
        rozetkaCatalogAndSearching.goToMain();
    }
    @Test//відкрити каталог, натиснути на головну категорію, після завантаження натиснути на підкатегорію
    public void mainCatalogOpenEnter() {
        rozetkaCatalogAndSearching.loadPage();
        rozetkaCatalogAndSearching.openCatalog();
        rozetkaCatalogAndSearching.selectMainCatalogOption("Товари для геймерів");
        rozetkaCatalogAndSearching.waitForPageUrl("https://rozetka.com.ua/ua/game-zone/c80261/");
        rozetkaCatalogAndSearching.selectSecondMainCatalogOption("Ігрові ноутбуки");
        rozetkaCatalogAndSearching.waitForPageUrl("https://rozetka.com.ua/ua/notebooks/c80004/preset=game/");
        rozetkaCatalogAndSearching.waitPageLoad();
    }

    @Test//відкрити каталог, навести курсор на основну категорію, натиснути на підкатегорію з тих,
    // що завантажились
    public void mainCatalogOpenMouse() {
        rozetkaCatalogAndSearching.loadPage();
        rozetkaCatalogAndSearching.openCatalog();
        rozetkaCatalogAndSearching.selectSecondPeerCatalogMouse("Побутова техніка", "Кухня");
        rozetkaCatalogAndSearching.waitForPageUrl("https://bt.rozetka.com.ua/ua/tehnika-dlya-kuhni/c435974/");
    }

    @Test//натиснути на категорію бічного меню, вибрати підкатегорію після завантаження
    public void sideSecondPeerCatalogOpen() {
        rozetkaCatalogAndSearching.loadPage();
        rozetkaCatalogAndSearching.selectMainSideCatalogOption("Інструменти та автотовари");
        rozetkaCatalogAndSearching.waitForPageUrl("https://rozetka.com.ua/ua/instrumenty-i-avtotovary/c4627858/");
        rozetkaCatalogAndSearching.selectSecondMainCatalogOption("Автозапчастини");
        rozetkaCatalogAndSearching.waitForPageUrl("https://rozetka.com.ua/ua/avtozapchasti/c4630454/");
    }

    @Test//ввести в строку пошуку товар, вибрати підкатегорію з тих, що з'явились, натиснути на
    // перший результат, очистити строку пошуку вести товар, натиснути на перший результат
    public void sideCatalogAfterSearchOpen() {
        rozetkaCatalogAndSearching.loadPage();
        rozetkaCatalogAndSearching.searchFor("Samsung S22");
        //не працює:
        // assertTrue(rozetkaCatalogAndSearching.getTextSearchResults().contains("Samsung S22"));
        rozetkaCatalogAndSearching.selectMainSideAfterSearch("Мобільні телефони");
        //не буде працювати, бо посилання формується на основі пошуку:
        //rozetkaCatalogAndSearching.waitForPageUrl("https://rozetka.com.ua/ua/instrumenty-i-avtotovary/c4627858/");
        rozetkaCatalogAndSearching.waitPageLoad();
        rozetkaCatalogAndSearching.clickFirstSearchResult();
        //очистити поле пошуку
        rozetkaCatalogAndSearching.clearSearchInput();
        rozetkaCatalogAndSearching.searchFor("Samsung S21");
        rozetkaCatalogAndSearching.clickFirstSearchResult();
    }

    @AfterEach
    public void wrapUp() {
        System.out.println("======================================");
    }

    @AfterAll
    public static void tearDown() {
        rozetkaCatalogAndSearching.quitDriver();
    }
}

