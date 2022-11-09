# book-mgmt
cucumber,graphql 활용 도서관리 시스템


## 목차

 * [개발환경](#개발환경)
 * [테이블 설계](#테이블설계)
 * [REST API 명세](#RESTAPI명세)
 * [graphql 명세서](#graphql명세서)
 

## 개발환경

1. BE
   * kotlin 1.5
   * Spring boot 2.7.5
   * graphql
   * jpa (querydsl)
   * gradle

2. DB
   * h2

3. TC
   * TDD+BDD
   * cucumber 7.5.0
   * karate 1.0.1
   * graphqlTester
   * mockmvc

4. DOCS
   * springdoc-openapi 1.6.9
   * spring rest docs 2.0.6
   * javadoc



## 테이블&nbsp;설계

1. 도서관리 테이블
```
   create table book 
   (
      book_id varchar(255) not null, 
      book_name varchar(255), 
      created_at timestamp not null, 
      updated_at timestamp, 
      genre_code varchar(255) not null, 
      primary key (book_id)
   )
```

2. 장르관리 테이블
```
   create table genre 
   (
      genre_code varchar(255) not null, 
      genre_main_category varchar(255) not null, 
      genre_sub_category varchar(255) not null, 
      primary key (genre_code)
   )
```


## REST&nbsp;API&nbsp;명세

1. 도서정보 조회
   1. 설명 : 도서 정보를 조회한다
   2. Request
      ~~~curl
      GET /api/{type}
      ~~~
      **parameter**
   
      | 키 | 설명 | 필수 | 기본값 | 타입 |
      |---|---|---|---|---|
      |type | 조회할 대상 (book: 도서, genre: 장르) | O | - | Path Parameter |
      |id | unique 키값 | O | - | Query Parameter |

   3. Response
   
      | 키 | 설명 | 타입 | 비고 |
      |---|---|---|---|
      | status | 결과값 | String | - |
      | message | 메세지 | String | - |
      | data | 결과Object | Object | book, genre에 따라 결과값 형태가 다름 |
   
      **book response data**
   
      | 키 | 설명 | 타입 | 비고 |
      |---|---|---|---|
      | bookId | 도서ID | String | - |
      | genreCode | 장르코드 | String | - |
      | genreMainCategory | 장르코드 대분류 | String | - |
      | genreSubCategory | 장르코드 소분류 | String | - |
      | bookName | 도서명 | String | - |
      | updatedAt | 업데이트 일자 | String | - |
      | createdAt | 생성 일자 | String | - |

      **genre response data**

      | 키 | 설명 | 타입 | 비고 |
      |---|---|---|---|
      | genreCode | 장르코드 | String | - |
      | genreMainCategory | 장르코드 대분류 | String | - |
      | genreSubCategory | 장르코드 소분류 | String | - |

2. 도서정보 삭제
    1. 설명 : 도서 정보를 삭제한다
    2. Request
       ~~~curl
       DELETE /api/{type}
       ~~~
       **parameter**

       | 키 | 설명 | 필수 | 기본값 | 타입 |
       |---|---|---|---|---|
       |type | 조회할 대상 (book: 도서, genre: 장르) | O | - | Path Parameter |
       |id | unique 키값 | O | - | Query Parameter |

    3. Response

       | 키 | 설명 | 타입 | 비고 |
       |---|---|---|---|
       | status | 결과값 | String | - |
       | message | 메세지 | String | - |

3. 도서정보 저장
    1. 설명 : 도서 정보를 저장한다
    2. Request
       ~~~curl
       POST /api/book
       ~~~
       **parameter**

       | 키 | 설명 | 필수 | 기본값 | 타입 |
       |---|---|---|---|---|
       |bookId | 도서ID | O | - | JSON |
       |genreCode | 장르코드 | O | - | JSON |
       |bookName | 도서명 | O | - | JSON |

    3. Response

        | 키 | 설명 | 타입 | 비고 |
        |---|---|---|---|
        | status | 결과값 | String | - |
        | message | 메세지 | String | - |
        | data | 결과Object | Object | book, genre에 따라 결과값 형태가 다름 |

        **book response data**

        | 키 | 설명 | 타입 | 비고 |
        |---|---|---|---|
        | bookId | 도서ID | String | - |
        | genreCode | 장르코드 | String | - |
        | genreMainCategory | 장르코드 대분류 | String | - |
        | genreSubCategory | 장르코드 소분류 | String | - |
        | bookName | 도서명 | String | - |
        | updatedAt | 업데이트 일자 | String | - |
        | createdAt | 생성 일자 | String | - |

4. 장르정보 저장
    1. 설명 : 장르 정보를 저장한다
    2. Request
       ~~~curl
       POST /api/genre
       ~~~
       **parameter**

       | 키 | 설명 | 필수 | 기본값 | 타입 |
       |---|---|---|---|---|
       |genreCode | 장르코드 | O | - | JSON |
       |genreMainCategory | 장르코드 대분류 | O | - | JSON |
       |genreSubCategory | 장르코드 소분류 | O | - | JSON |

    3. Response

       | 키 | 설명 | 타입 | 비고 |
       |---|---|---|---|
       | status | 결과값 | String | - |
       | message | 메세지 | String | - |
       | data | 결과Object | Object | book, genre에 따라 결과값 형태가 다름 |

       **genre response data**

       | 키 | 설명 | 타입 | 비고 |
       |---|---|---|---|
       | genreCode | 장르코드 | String | - |
       | genreMainCategory | 장르코드 대분류 | String | - |
       | genreSubCategory | 장르코드 소분류 | String | - |



## graphql&nbsp;명세서

```
type Query {
     findBook(bookId: String): Book
     findGenre(genreCode: String): Genre
}

type Mutation {
     saveBook(input: BookInput): Book
     saveGenre(input: GenreInput): Genre
     deleteBook(bookId: String): Boolean
     deleteGenre(genreCode: String): Boolean
}

input BookInput {
     bookId: String
     genreCode: String
     bookName: String
}

input GenreInput {
     genreCode: String
     genreMainCategory: String
     genreSubCategory: String
}

type Book {
     bookId: String
     genreCode: String
     genreMainCategory: String
     genreSubCategory: String
     bookName: String
     updatedAt: String
     createdAt: String
}

type Genre {
     genreCode: String
     genreMainCategory: String
     genreSubCategory: String
}
```
