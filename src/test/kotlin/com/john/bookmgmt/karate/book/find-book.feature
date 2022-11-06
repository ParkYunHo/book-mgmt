Feature: karate test

Background:
  * url baseUrl

  And text query =
    """
    query find {
        findBook(bookId: "B001") {
            bookId
            genreCode,
            genreMainCategory,
            genreSubCategory,
            bookName
            updatedAt
            createdAt
        }
    }
    """

  Scenario: simple graphql find
    Given path "/graphql"
    And request { query : '#(query)' }

    When method post

    Then status 200
    Then match response.data != '#null'
    Then match response.errors == '#notpresent'

    * print 'response: ', response