Feature: karate test

Background:
  * url baseUrl

  And text query =
    """
    mutation save {
        saveGenre(input: {
            genreCode: "GA001",
            genreMainCategory: "main",
            genreSubCategory: "sub"
        }) {
            genreCode
            genreMainCategory
            genreSubCategory
        },
        saveBook(input: {
            bookId: "B001",genreCode: "GA001",bookName: "TEST2"
        }) {
            bookId
            genreCode
            genreMainCategory
            genreSubCategory
            bookName
            updatedAt
            createdAt
        }
    }
    """

  Scenario: simple graphql save
    Given path "/graphql"
    And request { query : '#(query)' }

    When method post

    Then status 200
    Then match response.data != '#null'
    Then match response.errors == '#notpresent'

    * print 'response: ', response