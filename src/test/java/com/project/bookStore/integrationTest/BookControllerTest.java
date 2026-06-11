package com.project.bookStore.integrationTest;

import com.project.bookStore.BookStoreApplication;
import com.project.bookStore.dto.BookDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = BookStoreApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {


    @LocalServerPort
    private int port;



    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    // This guarantees cleanup.sql runs FIRST, then the insert script runs SECOND
    @Sql(scripts = {"classpath:cleanup.sql", "classpath:InsertInitialBookRecordForTest.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnBooksWhenBookApiCalled(){
        BookDto[] listOfBooks = testRestTemplate.getForObject("http://localhost:" + port + "/api/v1/books", BookDto[].class);
        assertThat(listOfBooks).isNotNull();
        assertThat(listOfBooks.length).isEqualTo(1);
    }

    @Test
    @Sql(scripts = {"classpath:cleanup.sql", "classpath:InsertInitialBookRecordForTest.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnBooksWhenBookApiCalled1(){
        BookDto[] listOfBooks = testRestTemplate.getForObject("http://localhost:" + port + "/api/v1/books", BookDto[].class);
        assertThat(listOfBooks).isNotNull();
        assertThat(listOfBooks.length).isEqualTo(1);
    }

}

