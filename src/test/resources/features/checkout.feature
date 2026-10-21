@regression
Feature: Checkout
  The checkout form validates its fields and the overview adds up.

  Background:
    Given the user is logged in as "standard_user"
    And the user adds the following items to the cart:
      | Sauce Labs Backpack     |
      | Sauce Labs Bolt T-Shirt |
    And the user opens the cart

  Scenario Outline: Checkout form rejects missing data
    When the user enters the checkout details and continues:
      | firstName   | lastName   | postalCode   |
      | <firstName> | <lastName> | <postalCode> |
    Then the error message should be "<error>"

    Examples:
      | firstName | lastName | postalCode | error                          |
      |           | Doe      | 90210      | Error: First Name is required  |
      | John      | Doe      |            | Error: Postal Code is required |

  Scenario: Overview prices add up
    When the user enters the checkout details and continues:
      | firstName | lastName | postalCode |
      | John      | Doe      | 90210      |
    Then the item total should equal the sum of the item prices
    And the total should equal the item total plus tax
