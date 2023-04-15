package org.prog.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.prog.pages.locators.GooglePageSelectors;
import org.prog.pages.util.DataHolder;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
//варіант з використанням локаторів
public class GooglePage extends AbstractPage {
    private final static String URL = "https://google.com/";
    private final static String REQUEST =
            "https://randomuser.me/api/?inc=gender,name,nat&noinfo&results=20";
    public GooglePage(WebDriver driver) {
        super(URL, driver);
    }

    public void acceptCookiesIfPresent() {
        List<WebElement> btns = driver.findElements(By.tagName("button"));
        if (btns.size() > 1) {
            btns.get(3).click();
        } else {
            System.out.println("no cookies are displayed");
        }
    }

    //метод пошуку в гуглі
    public void setSearchValue(String value) {
        clearSearchInput();
        driver.findElement(GooglePageSelectors.SEARCH.getLocator())
                .sendKeys(value);
    }
        //очистити строку пошуку
    public void clearSearchInput() {
        driver.findElement(GooglePageSelectors.SEARCH.getLocator())
                .clear();
    }
//натиснути кнопку пошуку
    public void performSearch() {
        driver.findElement(GooglePageSelectors.SEARCH.getLocator())
                .sendKeys(Keys.ENTER);
    }
    //натиснути "Мені пощастить"
    public void feelingLuckySearch() {//це для того, щоб клікнуть на активну кнопку, бо деякі приховані
        driver.findElements(By.name("btnI")).stream()
                .filter(WebElement::isDisplayed)
                .findFirst().ifPresent(WebElement::click);
    }

    //Зміна мови через вспливаюче вікно
    public void alertChangeLang() {
        driver.findElement(GooglePageSelectors.ALERT_LANG.getLocator())
                .click();
    }
//Зміна мови через швидкі налаштування:
    //Відкрити швидкі налаштування (доступні після пошуку):
    public void openQuickSettings() {
        driver.findElement(GooglePageSelectors.FAST_SETTINGS.getLocator())
                .click();
    }
        //Натиснути на строку зміни мови (працює в повному вікні):
    public void putChangeLangBtn() {
        driver.findElement(GooglePageSelectors.CHANGE_LANG_BTN.getLocator())
                .click();
    }
    //обрати мову
    public void selectLang(String value) {
        driver.findElement(By.xpath("//*[contains(@class, 'jfk-radiobutton-label') and text() =  '" + value + "']"))
                .click();
    }
    //зберегти налаштування
    public void saveSettingsResults() {
        driver.findElement(GooglePageSelectors.SAVE_SETTINGS.getLocator())
                .click();
//клацнути алерт ОК
        new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
        System.out.println("New language accepted.");
            }

       public void wait10Sec() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    public void waitPageLoad() {
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    public List<String> getSearchHeaders() {
        return driver.findElements(By.tagName("h3")).stream()
                .map(WebElement::getText)//перетворюємо веб елемент на текст)
                .map(header -> header.replaceAll("\\|.*", "").trim())//очищаємо від зайвого
                .collect(Collectors.toList());
           }
    public void checkSearchResult(String text) {
        WebElement searchResults = driver.findElement(By.id("search"));
        String searchResultsText = searchResults.getText();
        try {
            assert searchResultsText.contains(text);
            System.out.println("Searching text " + text + " successful found in search results");
        } catch (AssertionError e) {
            System.err.println("Error: Searching text "+ text + " not found in search results");
            throw e;
        }
    }
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public WebElement clickFirstSearchResult() {
        WebElement firstElement = driver.findElements(By.tagName("h3"))
                .stream()
                .findFirst()
                .orElse(null);
        if (firstElement != null) {
            firstElement.click();
            return firstElement;
        } else {
            return null;
        }
    }
}
