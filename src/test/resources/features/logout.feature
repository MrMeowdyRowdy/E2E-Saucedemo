@regression
Feature: Logout

  Scenario: Logging out returns to the login page
    Given the user is logged in as "standard_user"
    When the user logs out
    Then the login page should be shown
