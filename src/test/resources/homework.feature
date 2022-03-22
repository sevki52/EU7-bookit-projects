Feature: Testing the whole information of a person with three layer of information


  Scenario: UI,API,DB Verification
    Given user logs in using "sbirdbj@fc2.com" "asenorval"
    When user is on the my self page
    And I get the current user information from UI
    And I logged Bookit api using "sbirdbj@fc2.com" and "asenorval"
    And I get the current user information from api
    And I get more information about user from API
    And I get the current user information from DataBase
    Then All five information from three environment should match

  @hw @db
  Scenario Outline: UI,API,DB Verification <email>
    Given user logs in using "<email>" "<password>"
    When user is on the my self page
    And I get the current user information from UI
    And I logged Bookit api using "<email>" and "<password>"
    And I get the current user information from api
    And I get more information about user from API
    And I get the current user information from DataBase
    Then All five information from three environment should match
    Examples:
      | email                     | password        |
      | sbirdbj@fc2.com           | asenorval       |
      | gmalkin7j@mail.ru         | christopheflory |
      | vtugman7k@joomla.org      | daryledikles    |
      | daldie7l@seattletimes.com | ruthannjohnes   |
      | rkernocke7m@cnet.com      | merrileeambler  |
