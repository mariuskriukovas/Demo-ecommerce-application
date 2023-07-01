Feature: Retrieve products

  Scenario: Successfully retrieve one product by name
    Given property with key as "name" and value as "Apple MacBook Air (M2)"
    Given property with key as "priceFrom" and value as 1000.99
    Given property with key as "priceTo" and value as 2000.99
    When the client calls allProducts query
    Then response status code is 200
    And returned key as "$.data.allProducts" has size as 1
    And returned key as "$.data.allProducts[0].name" should be as "Apple MacBook Air (M2)"

  Scenario: Successfully retrieve products by price
    Given property with key as "priceFrom" and value as 1000.99
    Given property with key as "priceTo" and value as 2000.99
    When the client calls allProducts query
    Then response status code is 200
    And returned key as "$.data.allProducts" has size as 6
    And returned key as "$.data.allProducts[0].name" should be as "Apple MacBook Air (M2)"
    And returned key as "$.data.allProducts[5].name" should be as "Apple iPad Air (2022)"