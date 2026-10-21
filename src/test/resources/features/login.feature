@regression
Feature: Login
  Failed logins show the right error and keep the user on the login page.

  Background:
    Given the user is on the Sauce Demo login page

  Scenario Outline: Login is rejected
    When the user logs in with username "<username>" and password "<password>"
    Then the error message should be "<error>"

    @smoke
    Examples: locked out user
      | username        | password     | error                                                                     |
      | locked_out_user | secret_sauce | Epic sadface: Sorry, this user has been locked out.                       |

    Examples: other failures
      | username      | password       | error                                                                     |
      | standard_user | wrong_password | Epic sadface: Username and password do not match any user in this service |
      |               | secret_sauce   | Epic sadface: Username is required                                        |
      | standard_user |                | Epic sadface: Password is required                                        |
