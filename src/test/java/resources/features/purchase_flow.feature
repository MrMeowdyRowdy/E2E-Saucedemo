Feature: Purchase flow on www.saucedemo.com
  As a user
  I want to login, add items to cart, and complete a checkout
  So that I reach the purchase success page.

  Background:
    Given the user is on the Sauce Demo login page

  Scenario Outline: Login, add items, view cart and complete purchase
    When the user logs in with username "<username>" and password "<password>"
    And the user adds the following items to the cart:
      | <item1> |
      | <item2> |
    And the user opens the cart
    And the user proceeds to checkout with:
      | firstName | lastName | postalCode |
      | <firstName> | <lastName> | <postalCode> |
    Then the purchase should be completed successfully

    Examples:
      | username      | password     | firstName | lastName | postalCode | item1                | item2                  |
      | standard_user | secret_sauce | John      | Doe      | 90210      | Sauce Labs Backpack  | Sauce Labs Bolt T Shirt |
