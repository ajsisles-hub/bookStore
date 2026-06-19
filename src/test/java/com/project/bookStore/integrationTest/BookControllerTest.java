package com.project.bookStore.integrationTest;

import com.project.bookStore.BookStoreApplication;
import com.project.bookStore.config.JwtUtil;
import com.project.bookStore.dto.BookDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(
        classes = BookStoreApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;


    void setupHeaders() {
        String token = jwtUtil.generateToken(new
                User(
                "janine@gmail.com",
                passwordEncoder.encode("password"),
                new ArrayList<>()
        ));

        testRestTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders().add("Authorization", "Bearer " + token);
                    return execution.execute(request, body);
                })
        );
    }

    @Test
// This guarantees cleanup.sql runs FIRST, then the insert script runs SECOND
    @Sql(scripts = {"classpath:cleanup.sql", "classpath:InsertInitialBookRecordForTest.sql"})
    void shouldReturnBooksWhenBookApiCalled() {
        setupHeaders();
        BookDto[] listOfBooks = testRestTemplate.getForObject("http://localhost:" + port + "/api/v1/books", BookDto[].class);
        assertThat(listOfBooks).isNotNull();
        assertThat(listOfBooks.length).isEqualTo(18);
    }


    @Test
    @Sql(scripts = {"classpath:cleanup.sql", "classpath:InsertInitialBookRecordForTest.sql"})
    void shouldReturnBookDtoListWhenGetBooksTitleCalled() {
        setupHeaders();
        // REMOVED: Mockito's 'when(...)' is gone.
        // This call will now hit the real controller, real service, and real DB
        // containing the row inserted by InsertInitialBookRecordForTest.sql.

        ResponseEntity<BookDto[]> response = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/books/the firm",
                BookDto[].class
        );

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isEqualTo(1);

        // Optional Bonus: verify the returned book actually matches the title searched
        assertThat(response.getBody())
                .extracting(BookDto::getTitle)
                .anyMatch(title -> title.equalsIgnoreCase("the firm"));

    }

//    private BookDto getBooksDto() {
//        return BookDto.builder()
//                .id(UUID.randomUUID())
//                .title("test title")
//                .description("test description")
//                .releaseYear(2026)
//                .build();
//    }

}

