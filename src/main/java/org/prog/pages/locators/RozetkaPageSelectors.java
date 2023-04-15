package org.prog.pages.locators;

import lombok.Getter;
import org.openqa.selenium.By;

public enum RozetkaPageSelectors {
   //Головне лого:
    MAIN_LOGO(By.className("header__logo")),
    //Пошук:
    SEARCH(By.name("search")),
    SEARCH_RESULTS(By.className("content_type_catalog")),
    //Каталог:
    catalogBtn(By.id("fat-menu")),//кнопка відкриття каталогу
    dropDownCatalogContainer(By.className("menu-wrapper")),//розгорнутий основний каталог
    sideCatalogContainer(By.className("menu-wrapper_state_static")),//бічний каталог (контейнер)
    secondCatalogContainer(By.className("portal-section")),//Контейнер категорій 2-го рівня
    SideCatalogContainerAfterSearch(By.className("sidebar-block")),//Бічний контейнер після пошуку
    itemsContainer(By.className("catalog-grid")),//на сторінці вибору товару


    //Перелік товарів після пошуку
    catalogMainItems(By.className("menu-categories__link")),
    //Рівні категорій товарів, ВИБІР за допомогою мишки
    catalogSecondPeerItemsMouse(By.className("menu__hidden-title")),//другий рівень каталогу
    catalogThirdPeerItemsMouse(By.className("menu__hidden-list-item")),//третій рівень каталогу

    // ВИБІР після натискання на головний пункт
    catalogSecondPeerItemsEnter(By.className("tile-cats__heading")),//другий рівень каталогу

    //Після пошуку формується спеціальне бокове меню, категорії(Працює і з головною категорією, і субкатегорією):
    searchingSideItemsMenu(By.className("categories-filter__link"));//бокове меню після пошуку

    @Getter
    private By locator;

    RozetkaPageSelectors(By locator) {
        this.locator = locator;
    }
}
