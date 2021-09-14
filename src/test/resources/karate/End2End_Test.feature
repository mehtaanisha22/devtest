Feature: End to End Test for user notes
Description: The purpose of these tests are  to cover End to End happy flows for customer.

  Scenario: Test GET request exact response
    Given url 'http://localhost:8080/notes/1'
    When method GET
    Then status 200
    And match $ == {id:1234, name:"John Smith", text:"Test1 description text", createdAt:"2021-09-13", userId:1}

  Scenario: Test GET all request exact response
    Given url 'http://localhost:8080/notes'
    When method GET
    Then status 200
    And match $ == [{id:1234, name:"John Smith", text:"Test1 description text", createdAt:"2021-09-13", userId:1}]

  Scenario: Testing a POST endpoint with request body
    Given url 'http://localhost:8080/notes'
    And request {name: 'Note1234', text: 'Sample test', userId:21}
    When method POST
    Then status 200
    And match $ == {id:1234, name:"John Smith", text:"Test1 description text", createdAt:"2021-09-13", userId:1}

  Scenario: Testing a PUT endpoint with request body
    Given url 'http://localhost:8080/notes'
    And request {id:1234, name: 'Note1234', text: 'Sample test'}
    When method PUT
    Then status 200
    And match $ == {id:1234, name:"John Smith", text:"Test1 description text", createdAt:"2021-09-13", userId:1}