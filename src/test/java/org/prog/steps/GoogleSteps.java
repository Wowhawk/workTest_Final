package org.prog.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.prog.dto.UserDto;
import org.prog.pages.GooglePage;
import org.prog.pages.locators.GooglePageSelectors;
import org.prog.util.DataHolder;

public class GoogleSteps {
    public static WebDriver driver;
    private GooglePage googlePage = new GooglePage(driver);

    @Given("I open Google")
    public void openGoogle() {
        googlePage.loadPage();
        googlePage.acceptCookiesIfPresent();
    }

    //робота з DataHolder
    @Then("I google for {string} from DataHolder")
    public void searchForPerson(String alias) {
        String name = (String) DataHolder.getInstance().get(alias);
        googlePage.setSearchValue(name);
        googlePage.performSearch();
    }

    @When("I google for person {string}")
    public void searchForRandomPerson(String alias) {
        UserDto userDto = (UserDto) DataHolder.getInstance().get(alias);
        googlePage.setSearchValue(getUserName(userDto));
        googlePage.performSearch();
    }

    @Then("I see that search results contain the {string} name")
    public void checkResultName(String alias) {
        String personName = (String) DataHolder.getInstance().get(alias);
        WebElement searchResults = driver.findElement(By.id("search"));
        String searchResultsText = searchResults.getText();
        try {
            assert searchResultsText.contains(personName);
            System.out.println("User name found in search results");
        } catch (AssertionError e) {
            System.err.println("Error: User name not found in search results");
            throw e;
        }
    }
    @Then("I click first search result")
    public void clickFirstResult() {
        googlePage.clickFirstSearchResult();
        googlePage.waitPageLoad();
    }

    @Then("I can see {string} name in search results")
    public void validateSearchResults(String alias) {
        UserDto userDto = (UserDto) DataHolder.getInstance().get(alias);
        String searchValue = getUserName(userDto);
        Assertions.assertTrue(googlePage.getSearchHeaders().stream()
                .anyMatch(header -> header.contains(searchValue)));
    }
    private String getUserName(UserDto randomUser) {
        return randomUser.getName().getFirst() +
                " " + randomUser.getName().getLast();
    }

    //звичайні операції
    @Then("I enter {string} into the search field")
    public void putSearchValue(String value) {
        googlePage.setSearchValue(value);
    }
    @When("I click the search button")
    public void putSearchButton() {
        googlePage.performSearch();
    }
    @Then("I see that search results contain the word {string}")
    public void checkResult(String expectedSearchResult) {
        WebElement searchResults = driver.findElement(By.id("search"));
        String searchResultsText = searchResults.getText();
        assert (searchResultsText.contains(expectedSearchResult));
    }

    //for SQL


    /*@When("I google for person {string}")
    public void searchForRandomPerson(String alias) {
        UserDto userDto = (UserDto) DataHolder.getInstance().get(alias);
        googlePage.setSearchValue(getUserName(userDto));
        googlePage.performSearch();
    }
*/

    @When("I set {} value to name of {string}")
    public void searchForName(GooglePageSelectors gps, String alias) {
        UserDto userDto = (UserDto) DataHolder.getInstance().get(alias);
        String searchValue = getUserName(userDto);
        driver.findElement(gps.getLocator()).sendKeys(searchValue);
    }

    @When("I send key {} to {}")
    public void clickButton(Keys keys, GooglePageSelectors gps) {
        driver.findElement(gps.getLocator()).sendKeys(keys);
    }

    @Then("I see {string} name in {}")
    public void checkSearchResults(String alias, GooglePageSelectors gps) {
        UserDto userDto = (UserDto) DataHolder.getInstance().get(alias);
        String searchValue = getUserName(userDto);
        Assertions.assertTrue(driver.findElements(gps.getLocator()).stream()
                .anyMatch(webElement -> webElement.getText().contains(searchValue)));
    }

   /* private String getUserName(UserDto randomUser) {
        return randomUser.getName().getFirst() +
                " " + randomUser.getName().getLast();
    }*/

//SQL off
  /*  @After
    public void tearDown() {
        driver.quit();
    }*/
}







