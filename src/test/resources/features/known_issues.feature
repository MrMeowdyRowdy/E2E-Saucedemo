@known-issue
Feature: Known issues of problem_user
  saucedemo.com plants bugs in the problem_user account on purpose. These scenarios assert the
  buggy behaviour as it is today, so they pass while the bug exists and fail if the site is fixed.
  They are excluded from the default run: use -Dcucumber.filter.tags="@known-issue".

  Background:
    Given the user is logged in as "problem_user"

  Scenario: Every product shows the same image
    Then every product should show the same image

  Scenario: Sorting by name Z to A does not reorder the products
    When the user sorts the products by "Name (Z to A)"
    Then the product names should be sorted ascending

  Scenario: The last name cannot be entered at checkout
    When the user adds the following items to the cart:
      | Sauce Labs Backpack |
    And the user opens the cart
    And the user enters the checkout details and continues:
      | firstName | lastName | postalCode |
      | John      | Doe      | 90210      |
    Then the error message should be "Error: Last Name is required"
