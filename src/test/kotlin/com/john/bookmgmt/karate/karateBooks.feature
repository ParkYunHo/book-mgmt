Feature: karate test

Background:
  * url baseUrl

  And text saveQuery =
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

#  And text query =
#    """
#    {
#      findBook(bookId: "B001"){
#          bookId
#          genreCode,
#          genreMainCategory,
#          genreSubCategory,
#          bookName
#          updatedAt
#          createdAt
#      }
#    }
#    """

  Scenario: simple graphql save
    Given path "/graphql"
    And request { query : '#(saveQuery)' }
    When method post
    Then status 200

    * print 'response: ', response

#  Scenario: simple graphql find
#    Given path "/graphql"
#    And request { query : '#{query)' }
#    When method post
#    Then status 200
#
#    * print 'response: ', response