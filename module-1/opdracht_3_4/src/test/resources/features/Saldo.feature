Feature: Saldo
  Scenario: Add saldo
    Given the user has an account
    And the user has a rekening
    And the rekening is not blocked
    And the saldo is 100
    When the user adds 50 to his saldo
    Then the saldo is updated to 150

  Scenario: Remove saldo
    Given the user has an account
    And the user has a rekening
    And the rekening is not blocked
    And the saldo is 150
    When the user removes 50 from his saldo
    Then the saldo is updated to 100