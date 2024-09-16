Feature: Booking API Tests

  Scenario: Create a new booking
    Given I create a new booking with firstname "Alice", lastname "Johnson", totalprice 500
    Then I verify the booking was created successfully with status code 200

  Scenario: Retrieve booking by valid ID
    Given I retrieve a booking by ID 2
    Then I verify the booking firstname is "Alice"
    And I verify the booking lastname is "Johnson"
    And I verify the total price is 500

  Scenario: Attempt to retrieve a non-existent booking by ID
    Given I retrieve a booking by ID 9999
    Then I verify the response status code is 404

  Scenario: Update an existing booking
    Given I update the booking with ID 2 to have firstname "Bob"
    Then I verify the response status code is 200
    And I verify the booking firstname is "Bob"

  Scenario: Delete a booking by ID
    Given I delete the booking with ID 2
    Then I verify the response status code is 201
