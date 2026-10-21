@regression
Feature: Cart
  Items added on the products page show up in the cart and on the cart badge.

  Background:
    Given the user is logged in as "standard_user"

  Scenario: Adding items updates the badge and the cart
    When the user adds the following items to the cart:
      | Sauce Labs Backpack     |
      | Sauce Labs Bolt T-Shirt |
    Then the cart badge should show 2
    When the user opens the cart
    Then the cart should contain exactly:
      | Sauce Labs Backpack     |
      | Sauce Labs Bolt T-Shirt |

  Scenario: Removing an item updates the badge
    When the user adds the following items to the cart:
      | Sauce Labs Backpack     |
      | Sauce Labs Bolt T-Shirt |
    And the user removes the following items from the cart:
      | Sauce Labs Backpack |
    Then the cart badge should show 1

  Scenario: Removing the last item hides the badge
    When the user adds the following items to the cart:
      | Sauce Labs Onesie |
    And the user removes the following items from the cart:
      | Sauce Labs Onesie |
    Then the cart badge should show 0
