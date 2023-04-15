package org.prog.pages;

import org.openqa.selenium.WebDriver;
import org.prog.pages.locators.GooglePageSelectors;
import org.prog.pages.util.DataHolder;
public abstract class AbstractPage {

    protected final WebDriver driver;
    private final String url;

    public AbstractPage(String url, WebDriver driver) {
        this.driver = driver;
        this.url = url;
        DataHolder dataHolder = DataHolder.getInstance();
    }

    public void loadPage() {
        driver.get("about:blank");
        driver.get(url);
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
