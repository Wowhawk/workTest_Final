package org.prog.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.prog.pages.AbstractPage;
import org.prog.pages.locators.RozetkaPageSelectors;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class RozetkaCatalogAndSearching extends AbstractPage {
    private final static String url = "https://rozetka.com.ua/ua/";

    public RozetkaCatalogAndSearching(WebDriver driver) {
        super(url, driver);
    }

    private List<WebElement> searchResults;

    private final By searchingSideItemsMenu = By.className("categories-filter__link");//бокове меню після пошуку

    public void implicitlyWait10() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public void waitPageLoad() {
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    public void openCatalog() {
        new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.visibilityOfElementLocated(RozetkaPageSelectors.catalogBtn.getLocator()))
                .click();
        new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.visibilityOfElementLocated(RozetkaPageSelectors.dropDownCatalogContainer.getLocator()));
    }

    //ПОШУК
    //Надрукувати value в строку пошуку розетки і натиснути ENTER
    public List<WebElement> searchFor(String searchValue) {
        driver.findElement(RozetkaPageSelectors.SEARCH.getLocator()).sendKeys(searchValue);
        driver.findElement(RozetkaPageSelectors.SEARCH.getLocator()).sendKeys(Keys.ENTER);
        try {
            if (driver.findElements(By.className("catalog-empty")).size() > 0) {
                System.out.println("ERROR: No search results found.");
                throw new NoSuchElementException("No search results found.");
            }
        } catch (NoSuchElementException e) {
        }
        return searchResults;
    }

    public void checkResult(String searchValue) {
        WebElement searchResults = driver.findElement(RozetkaPageSelectors.SEARCH_RESULTS.getLocator());
        String searchResultsText = searchResults.getText();
        try {
            assert searchResultsText.contains(searchValue);
            System.out.println("Item was found in search results");
        } catch (AssertionError e) {
            System.err.println("Error: Item not found in search results");
            throw e;
        }
    }

    public void goToMain() {
        driver.findElement(RozetkaPageSelectors.MAIN_LOGO.getLocator()).click();
    }

    //очистити строку пошуку
    public void clearSearchInput() {
        driver.findElement(RozetkaPageSelectors.SEARCH.getLocator()).clear();
    }

    //клікнути на перший результат пошуку:
    public void clickFirstSearchResult() {
        new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.visibilityOfElementLocated(RozetkaPageSelectors.itemsContainer.getLocator()));
        driver.findElements(RozetkaPageSelectors.SEARCH_RESULTS.getLocator()).stream()
                .findFirst().get().click();
    }

    //КАТАЛОГ (вибір категорій)
//Вибір (натискання) на головну категорію каталогу, і бічного списку (не після пошуку)
    public void selectMainCatalogOption(String category) {
        new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.visibilityOfElementLocated(RozetkaPageSelectors.dropDownCatalogContainer.getLocator()));
        driver.findElements(RozetkaPageSelectors.catalogMainItems.getLocator()).stream()
                .filter(we -> we.getText().contains(category))
                //можна було б всі категорії зробити списками
                .findFirst().get().click();
    }

    //Вибір підкатегорій (2го рівня) з основного каталогу (після натискання на кнопку каталогу і
    // натискання на головну категорію товарів)
    public void selectSecondMainCatalogOption(String category) {
        new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.visibilityOfElementLocated(RozetkaPageSelectors.secondCatalogContainer.getLocator()));
        driver.findElements(RozetkaPageSelectors.catalogSecondPeerItemsEnter.getLocator()).stream()
                .filter(we -> we.getText().contains(category))
                .findFirst().get().click();
    }

    //Вибір основної категорії з бокового меню (НЕ після пошуку)
    public void selectMainSideCatalogOption(String category) {
        new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.visibilityOfElementLocated(RozetkaPageSelectors.sideCatalogContainer.getLocator()));
        driver.findElements(RozetkaPageSelectors.catalogMainItems.getLocator()).stream()
                .filter(we -> we.getText().contains(category))
                .findFirst().get().click();
    }

    public boolean waitForPageUrl(String url) {
        return new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.urlToBe(url));
    }

    //Вибір підкатегорії з каталогу мишкою:
    public void selectSecondPeerCatalogMouse(String category, String subCategory) {
        Actions actions = new Actions(driver);
        Optional<WebElement> mainItem = driver.findElements(RozetkaPageSelectors.catalogMainItems.getLocator()).stream()
                .filter(we -> we.getText().contains(category))
                .findFirst();
        if (mainItem.isPresent()) {
            actions.moveToElement(mainItem.get()).perform();
        }
        driver.findElements(RozetkaPageSelectors.catalogSecondPeerItemsMouse.getLocator()).stream()
                .filter(we -> we.getText().contains(subCategory))
                .findFirst().get().click();
    }

    //Вибіг категорії бічного меню після пошуку:
    public void selectMainSideAfterSearch(String category) {
        new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.visibilityOfElementLocated(RozetkaPageSelectors.SideCatalogContainerAfterSearch.getLocator()));
        driver.findElements(RozetkaPageSelectors.searchingSideItemsMenu.getLocator()).stream()
                .filter(we -> we.getText().contains(category))
                .findFirst().get().click();
    }
}