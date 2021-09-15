Feature: End to End Test for user notes
Description: The purpose of these tests are  to cover End to End happy flows for user.

  Scenario: Test get request should return note
    Given url 'http://localhost:8080/notes/1'
    When method GET
    Then status 200
    And match $ == {id:1234, name:"John Smith", text:"Test1 description text", createdAt:"2021-09-13", userId:1}

  Scenario: Test get all request should return list of notes
    Given url 'http://localhost:8080/notes'
    When method GET
    Then status 200
    And match $ == [{id:1234, name:"John Smith", text:"Test1 description text", createdAt:"2021-09-13", userId:1}]

  Scenario: Test create note with request body should return newly created note
    Given url 'http://localhost:8080/notes'
    And request {name: 'Note1234', text: 'Sample test', userId:21}
    When method POST
    Then status 200
    And match $ == {id:1234, name:"John Smith", text:"Test1 description text", createdAt:"2021-09-13", userId:1}

  Scenario: Test update note with request body should return updated note
    Given url 'http://localhost:8080/notes'
    And request {id:1234, name: 'Note1234', text: 'Sample test'}
    When method PUT
    Then status 200
    And match $ == {id:1234, name:"John Smith", text:"Test1 description text", createdAt:"2021-09-13", userId:1}