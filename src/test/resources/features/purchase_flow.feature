@regression
Feature: Purchase flow
  A user logs in, adds items to the cart and completes a checkout.

  Background:
    Given the user is on the Sauce Demo login page

  @smoke
  Scenario: Log in, add items, check out and see the confirmation
    When the user logs in with username "standard_user" and password "secret_sauce"
    And the user adds the following items to the cart:
      | Sauce Labs Backpack     |
      | Sauce Labs Bolt T-Shirt |
    And the user opens the cart
    And the user completes checkout with:
      | firstName | lastName | postalCode |
      | John      | Doe      | 90210      |
    Then the purchase should be completed successfully
