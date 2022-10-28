# language: ko
기능: 도서관리API 호출
모든 사용자는 도서관리API를 호출한다

  @book
  시나리오 개요: 도서관리API 호출
    먼저 도서관리API 호출을 위한 "<type>""<bookId>""<genreCode>""<genreMainCategory>""<genreSubCategory>""<bookName>" 가 있다
    만약 도서정보와 함께 도서관리API를 "<method>""<url>" 요청하면
    그러면 도서관리API 호출결과 <code>"<status>" 를 확인한다

    예:
      | type  | bookId  | genreCode | genreMainCategory | genreSubCategory  | bookName  | method  | url         | code | status       |
      # book #
      # book-find
      | book  | B001    |           |                   |                   |           | GET     | /api/book   | 200  | OK           |
      | book  |         |           |                   |                   |           | GET     | /api/book   | 400  | BAD_REQUEST  |
      # book-save
      | book  | B001    | GA001     | poem              | children poem     | test      | POST    | /api/book   | 200  | OK           |
      | book  |         | GA001     | poem              | children poem     | test      | POST    | /api/book   | 400  | BAD_REQUEST  |
      # book-delete
      | book  | B001    |           |                   |                   |           | DELETE  | /api/book   | 200  | OK           |
      | book  |         |           |                   |                   |           | DELETE  | /api/book   | 400  | BAD_REQUEST  |
      # genre #
      # genre-find
      | genre |         | GA001     |                   |                   |           | GET     | /api/genre  | 200  | OK           |
      | genre |         |           |                   |                   |           | GET     | /api/genre  | 400  | BAD_REQUEST  |
      # genre-save
      | genre |         | GA001     | poem              | children poem     |           | POST    | /api/genre  | 200  | OK           |
      | genre |         |           | poem              | children poem     |           | POST    | /api/genre  | 400  | BAD_REQUEST  |
      # genre-delete
      | genre |         | GA001     |                   |                   |           | DELETE  | /api/genre  | 200  | OK           |
      | genre |         |           |                   |                   |           | DELETE  | /api/genre  | 400  | BAD_REQUEST  |
