# language: ko
기능: 도서정보 조회
모든 사용자는 도서정보를 조회할 수 있다.

  @find-book
  시나리오 개요: 도서정보를 조회한다.
    먼저 도서정보를 구분하기 위한 "<id>" 가 있다
    만약 도서정보와 함께 도서 조회를 "<url>" 요청한다면
    그러면 조회된 도서정보 결과 <code>"<status>" 를 확인한다

    예:
      | id      | url        | code  | status       |
      | B001    | /api/book  | 200   | OK           |
      | GA001   | /api/genre | 200   | OK           |
      | B002    | /api/book  | 400   | BAD_REQUEST  |
      | GA002   | /api/genre | 400   | BAD_REQUEST  |

#  @save-book
#  시나리오 개요: 도서정보를 저장한다.
#    먼저 도서정보를 저장하기 위한 "<bookId>""<genreCode>""<bookName>" 이 있다
#    만약 도서정보와 함께 도서 저장을 "<url>" 요청한다면
#    그러면 저장된 도서정보 결과 <code>"<status>""<bookId>" 를 확인한다
#
#    예:
#      | bookId      | genreCode     | bookName    |
#      | B001        | GA001         | test1       |

