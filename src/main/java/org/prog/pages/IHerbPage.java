package org.prog.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class IHerbPage extends AbstractPage {
    private final static String URL = "https://iherb.com";
    private String searchValue;

    private List<WebElement> searchResults;

    public IHerbPage(WebDriver driver) {
        super(URL, driver);
    }

    public void waitPageLoad() {
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
    }

    public void settingsChangeLang() {
        driver.findElement(By.className("selected-country-wrapper")).click();
        driver.findElement(By.xpath("//*[contains(@class, 'select-language gh-dropdown')]")).click();
        driver.findElement(By.xpath("/html/body/header/div[2]/div/div/div/div[5]/div/label/input")).sendKeys("English");
        driver.findElement(By.xpath("/html/body/header/div[2]/div/div/div/div[5]/div/label/input")).sendKeys(Keys.ENTER);
        //зберегти
        driver.findElement(By.xpath("//*[contains(@class, 'save-selection gh-btn gh-btn-primary')]")).click();
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public List<WebElement> searchFor(String searchValue) {
        driver.findElement(By.id("txtSearch")).sendKeys(searchValue);
        driver.findElement(By.id("txtSearch")).sendKeys(Keys.ENTER);
        try {
            if (driver.findElements(By.className("no-results-found-heading")).size() > 0) {
                System.out.println("ERROR: No search results found.");
                throw new NoSuchElementException("No search results found.");
            }
        } catch (NoSuchElementException e) {
        }
        searchResults = driver.findElements(By.xpath("//*[contains(@class,'product-title')]"));
        return searchResults;
    }
    public List<String> getSearchResults() {
        return searchResults
                .stream()
                .map(WebElement::getText)
                //.map(text -> text.replaceAll("\\|.*", "").trim())
                //.map(text -> text.replaceAll("[^\\p{L}\\p{Nd}]+", "").toLowerCase().trim())
                .collect(Collectors.toList());
    }
    public List<String> showSearchResults() {
        List<String> results = getSearchResults();
        System.out.print("Всі результати пошуку: " + results);
        System.out.println(" ");
        System.out.println("Перший результат пошуку: " + results.get(0));
        return results;
    }
}

