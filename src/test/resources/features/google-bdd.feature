Feature: Generate a random Person and Google Search

  Scenario: Google search Cats
    Given I open Google
    Then I enter "Cats" into the search field
    When I click the search button
    Then I see that search results contain the word "Cats"

  Scenario: Generate a list of random Person and filter it by gender with Data Holder
    Given I generate a random users list "List1"
    Given I generate a random users list "List2"
    Given I filter users list "List1" by gender "female"
    Given I filter users list "List1" by gender "male"
    Given I filter users list "List2" by gender "female"
    Then I filter users list "List1" by gender "female" and save the user "Person 1" to DataHolder
    Then I filter users list "List1" by gender "male" and save the user "Person 2" to DataHolder
    Then I filter users list "List2" by gender "female" and save the user "Person 3" to DataHolder

  Scenario: Generate a list of random Person, filter it by gender and google him or her with Data Holder
    Given I generate a random users list "List1"
    Given I filter users list "List1" by gender "female"
    Then I filter users list "List1" by gender "female" and save the user "Person 1" to DataHolder
    And I open Google
    Then I google for "Person 1" from DataHolder
    And I see that search results contain the "Person 1" name
    Then I click first search result


  Scenario: Generate a random Person and google him or her with Data Holder
    Given I generate a random "person_1"
    Given I generate a random "person_2"
    Given I generate a random "person_3"
    And I open Google
    When I google for person "person_1"
    Then I can see "person_1" name in search results
    And I open Google
    When I google for person "person_2"
    Then I can see "person_2" name in search results
    And I open Google
    When I google for person "person_3"
    Then I can see "person_3" name in search results

#  Scenario: Generate a random Person and save person to DB
#    Given I generate a random "person_1"
#    Given I save person "person_1" to DB
#    Given I retrieve person with "FirstName" = "Francis" as "person_1"
#    And I open Google
#    When I google for person "person_1"
#    Then I can see "person_1" name in search results
