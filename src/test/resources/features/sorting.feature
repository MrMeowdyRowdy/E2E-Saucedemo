@regression
Feature: Sorting
  The products page can be sorted and the whole list follows the chosen order.

  Background:
    Given the user is logged in as "standard_user"

  Scenario: Sort by price, low to high
    When the user sorts the products by "Price (low to high)"
    Then the product prices should be sorted ascending

  Scenario: Sort by name, Z to A
    When the user sorts the products by "Name (Z to A)"
    Then the product names should be sorted descending
