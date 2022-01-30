Feature: Purpose of this feature is to cover /houses endpoint with both positive and negative scenarios

  Scenario: User should be able to filter houses by city and defined price range
    When user tries to filter houses in price range by city
    Then all houses in price range for that city will be shown

  Scenario: User should be able to filter houses by city and only lower limit of price range
    When user tries to filter houses with lower limit of price range by city
    Then all houses in lower limit price range for that city will be shown

  Scenario: User should be able to filter houses by city and only upper limit of price range
    When user tries to filter houses with upper limit of price range by city
    Then all houses in upper limit price range for that city will be shown

  Scenario: An empty list should be returned when user tries to filter houses with out range prices
    When user tries to filter houses with out range prices
    Then an empty list should be returned

  Scenario: An empty list should be returned when user tries to filter houses with not existing city
    When user tries to filter houses with not existing city
    Then an empty list should be returned

  Scenario: User should not be able to filter houses in case when price parameter is not number
    When user tries to filter house with invalid price parameter
    Then an error response should be returned with status code 400
    And an error message "The price value must be an integer!"

  Scenario: User should not be able to filter houses in case when price parameter is negative number
    When user tries to filter house with negative price parameter
    Then an error response should be returned with status code 400
    And an error message "The price value must be a positive number!"

  Scenario: User should not be able to filter houses in case when price parameter is an empty String
    When user tries to filter house with an empty string parameter
    Then an error response should be returned with status code 400
    And an error message "The price value must be an integer!"

