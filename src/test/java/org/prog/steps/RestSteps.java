package org.prog.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.prog.dto.ResultsDto;
import org.prog.dto.UserDto;
import org.prog.util.DataHolder;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasItems;

public class RestSteps {
    private final static String REQUEST =
            "https://randomuser.me/api/?inc=gender,name,nat&noinfo&results=20";

    //степи для створення списків і фільтрації:
    @Given("I generate a random users list {string}")
    public void generateRandomUsers(String listName) {
        ResultsDto resultsDto = getGeneratedUsersList();
        List<UserDto> generatedUsers = resultsDto.getResults();
        DataHolder.getInstance().put(listName, generatedUsers);
        System.out.println(generatedUsers);
    }

    @Given("I filter users list {string} by gender {string}")
    public void genderFilter(String listName, String gender) {
        ResultsDto filteredResults = getFilteredResults(gender);
        List<UserDto> filteredUsers = filteredResults.getResults();
        DataHolder.getInstance().put(listName, filteredUsers);
        System.out.println(filteredUsers);
    }

    @Then("I filter users list {string} by gender {string} and save the user {string} to DataHolder")
    public void filterUserListAndSaveName(String listName, String gender, String alias) {
        String userName = getFilteredUserName(gender);
        DataHolder.getInstance().put(alias, userName);
        System.out.println("First name from " + listName + " filtered by " + gender + " is " + userName);
    }


    private ResultsDto getGeneratedUsersList() {
        Response response = RestAssured.get(REQUEST);
        ValidatableResponse validatableResponse = response.then();
        validatableResponse.assertThat()
                .statusCode(200)
                .body("results.gender", hasItems("male", "female"));
        return response.as(ResultsDto.class);
    }

    private ResultsDto getFilteredResults(String gender) {
        ResultsDto resultsDto = getGeneratedUsersList();
        List<UserDto> filteredUsers = resultsDto.getResults().stream()
                .filter(user -> user.getGender().equals(gender))
                .collect(Collectors.toList());
        ResultsDto filteredResults = new ResultsDto();
        filteredResults.setResults(filteredUsers);
        return filteredResults;
    }

    //якщо нам треба строка, яку ми будем гуглити, то можна зробити список імен у вигляді строк
    // і вилучити перше
    private String getFilteredUserName(String gender) {
        ResultsDto resultsDto = getGeneratedUsersList();
        List<String> filteredUserNames = resultsDto.getResults().stream()
                .filter(user -> user.getGender().equals(gender))
                .map(user -> user.getName().getFirst() + " " + user.getName().getLast())
                .collect(Collectors.toList());
        String nameOfFirstUser = filteredUserNames.get(0);
        return nameOfFirstUser;
    }

    //через БД:
    @Given("I generate a random {string}")
    public void generateRandomUser(String alias) {
        DataHolder.getInstance().put(alias, getUserName());
    }

    private UserDto getUserName() {
        Response response = RestAssured.get(REQUEST);
        ValidatableResponse validatableResponse = response.then();

        validatableResponse.assertThat()
                .statusCode(200)
                .body("results.gender", hasItems("male", "female"));

        ResultsDto resultsDto = response.as(ResultsDto.class);
        return resultsDto.getResults().stream().findFirst().get();
    }

}

