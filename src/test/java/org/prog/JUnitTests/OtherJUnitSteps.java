package org.prog.JUnitTests;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.prog.dto.ResultsDto;
import org.prog.dto.UserDto;

import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.hasItems;

public class OtherJUnitSteps {
    private final static String REQUEST = "https://randomuser.me/api/?inc=gender,name,nat&noinfo&results=20";

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("This test trie to guess user's gender")
    public void criticalTest() {
        List<UserDto> users = getUsers();
        Assert.assertEquals("female", extractUser(users).getGender());
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("This test trie to guess user's gender")
    public void blockerTest() {
        List<UserDto> users = getUsers();
        Assert.assertEquals("female", extractUser(users).getGender());
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Description("This test trie to guess user's gender")
    public void minorTestOne() {
        List<UserDto> users = getUsers();
        Assert.assertEquals("female", extractUser(users).getGender());
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Description("This test trie to guess user's gender")
    public void minorTestTwo() {
        List<UserDto> users = getUsers();
        guessUserGender(users);
    }

    @Step("I generate 20 random users")
    private List<UserDto> getUsers() {
        Response response = RestAssured.get(REQUEST);
        ValidatableResponse validatableResponse = response.then();

        validatableResponse.assertThat().statusCode(200).body("results.gender", hasItems("male", "female"));

        return response.as(ResultsDto.class).getResults();
    }

    @Step("I pick one random user among 20")
    private UserDto extractUser(List<UserDto> users) {
        Random random = new Random();
        return users.get(random.nextInt(users.size()));
    }

    @Step("I try to guess user's gender")
    private void guessUserGender(List<UserDto> users) {
        Assert.assertEquals("female", extractUser(users).getGender());
    }
}


