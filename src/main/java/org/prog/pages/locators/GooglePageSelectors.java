package org.prog.pages.locators;

import lombok.Getter;
import org.openqa.selenium.By;

public enum GooglePageSelectors {
    SEARCH(By.name("q")),               //locator = By.name("q"), строка пошуку
    SEARCH_HEADERS(By.tagName("h3")),   //locator = By.tagName("h3")
    SETTINGS(By.className("CcNe6e")),   //налаштування
    FAST_SETTINGS(By.className("fLciMb")),   //швидкі налаштування (з'являються після пошуку)
    CHANGE_LANG_BTN(By.xpath("//*[contains(@class, 'ZI7elf UCGAnb')]")),
   SAVE_SETTINGS(By.xpath("//*[contains(@class, 'goog-inline-block jfk-button jfk-button-action')]")), //кнопка зберегти
   CURRENT_LANG(By.cssSelector("span.kQEH5b")), //назва поточної мови
    ALERT_LANG(By.linkText("Change to English")); //Кнопа зміни мови через вспливаюче вікно


    @Getter
    private By locator;

    GooglePageSelectors(By locator) {
        this.locator = locator;
    }
}
