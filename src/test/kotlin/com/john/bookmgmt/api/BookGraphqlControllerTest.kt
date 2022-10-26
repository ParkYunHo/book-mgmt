package com.john.bookmgmt.api

import com.john.bookmgmt.common.Constants
import com.john.bookmgmt.entity.Book
import com.john.bookmgmt.entity.Genre
import com.john.bookmgmt.repository.BookRepository
import com.john.bookmgmt.repository.GenreRepository
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime

/**
 * @author yoonho
 * @since 2022.10.28
 */
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureGraphQlTester
@TestMethodOrder(MethodOrderer.DisplayName::class)
class BookGraphqlControllerTest {

    @Autowired
    protected lateinit var graphQlTester: GraphQlTester

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var genreRepository: GenreRepository

    @BeforeEach
    fun init_dump() {
        var genre = Genre(
            genreCode = Constants.TEST_GENRE_CODE.code,
            genreMainCategory = Constants.TEST_GENRE_MAIN_CATEGORY.code,
            genreSubCategory = Constants.TEST_GENRE_SUB_CATEGORY.code
        )

        genreRepository.save(genre)
        bookRepository.save(
            Book(
                bookId = Constants.TEST_BOOK_ID.code,
                genre = genre,
                bookName = Constants.TEST_BOOK_NAME.code,
                updatedAt = LocalDateTime.now()
            )
        )
    }

    @Test
    @DisplayName("1. 도서조회 테스트")
    fun TEST_findBook() {
        this.graphQlTester.documentName("book/book-query")
            .variable("bookId", Constants.TEST_BOOK_ID.code)
            .execute()
            .path("findBook.bookId").entity(String::class.java).isEqualTo(Constants.TEST_BOOK_ID.code)
            .path("findBook.genreCode").entity(String::class.java).isEqualTo(Constants.TEST_GENRE_CODE.code)
    }

    @Test
    @DisplayName("2. 장르조회 테스트")
    fun TEST_findGenre() {
        this.graphQlTester.documentName("genre/genre-query")
            .variable("genreCode", Constants.TEST_GENRE_CODE.code)
            .execute()
            .path("findGenre.genreCode").entity(String::class.java).isEqualTo(Constants.TEST_GENRE_CODE.code)
            .path("findGenre.genreMainCategory").entity(String::class.java).isEqualTo(Constants.TEST_GENRE_MAIN_CATEGORY.code)
            .path("findGenre.genreSubCategory").entity(String::class.java).isEqualTo(Constants.TEST_GENRE_SUB_CATEGORY.code)
    }

    @Test
    @DisplayName("3. 도서저장 테스트")
    fun TEST_saveBook() {
        var input = mapOf(
            "bookId" to Constants.TEST_BOOK_ID.code,
            "genreCode" to Constants.TEST_GENRE_CODE.code,
            "bookName" to Constants.TEST_BOOK_NAME.code
        )

        this.graphQlTester.documentName("book/book-mutation-save")
            .variable("input", input)
            .execute()
            .path("saveBook.bookId").entity(String::class.java).isEqualTo(Constants.TEST_BOOK_ID.code)
            .path("saveBook.genreCode").entity(String::class.java).isEqualTo(Constants.TEST_GENRE_CODE.code)
    }

    @Test
    @DisplayName("4. 장르저장 테스트")
    fun TEST_saveGenre() {
        var input = mapOf(
            "genreCode" to Constants.TEST_GENRE_CODE.code,
            "genreMainCategory" to Constants.TEST_GENRE_MAIN_CATEGORY.code,
            "genreSubCategory" to Constants.TEST_GENRE_SUB_CATEGORY.code
        )

        this.graphQlTester.documentName("genre/genre-mutation-save")
            .variable("input", input)
            .execute()
            .path("saveGenre.genreCode").entity(String::class.java).isEqualTo(Constants.TEST_GENRE_CODE.code)
            .path("saveGenre.genreMainCategory").entity(String::class.java).isEqualTo(Constants.TEST_GENRE_MAIN_CATEGORY.code)
            .path("saveGenre.genreSubCategory").entity(String::class.java).isEqualTo(Constants.TEST_GENRE_SUB_CATEGORY.code)
    }

    @Test
    @DisplayName("5. 도서삭제 테스트")
    fun TEST_deleteBook() {
        this.graphQlTester.documentName("book/book-mutation-delete")
            .variable("bookId", Constants.TEST_BOOK_ID.code)
            .execute()
            .path("deleteBook").entity(Boolean::class.java).isEqualTo(true)
    }

    @Test
    @DisplayName("6. 장르삭제 테스트")
    fun TEST_deleteGenre() {
        bookRepository.deleteById(Constants.TEST_BOOK_ID.code)

        this.graphQlTester.documentName("genre/genre-mutation-delete")
            .variable("genreCode", Constants.TEST_GENRE_CODE.code)
            .execute()
            .path("deleteGenre").entity(Boolean::class.java).isEqualTo(true)
    }
}