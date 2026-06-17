package com.project.bookStore.integrationTest;

import com.project.bookStore.BookStoreApplication;
import com.project.bookStore.controller.BookController;
import com.project.bookStore.dto.BookDto;
import com.project.bookStore.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(
        classes = BookStoreApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {


    @LocalServerPort
    private int port;



    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockitoBean
    private BookService bookService;

    @Test
    // This guarantees cleanup.sql runs FIRST, then the insert script runs SECOND
    @Sql(scripts = {"classpath:cleanup.sql","classpath:InsertInitialBookRecordForTest.sql"})
    void shouldReturnBooksWhenBookApiCalled(){
        BookDto[] listOfBooks = testRestTemplate.getForObject("http://localhost:" + port + "/api/v1/books", BookDto[].class);
        assertThat(listOfBooks).isNotNull();
        assertThat(listOfBooks.length).isEqualTo(1);
    }

    @Test
    void shouldReturnBookDtoListWhenGetBooksTitleCalled(){
        List<BookDto> bookDtos = new ArrayList<>();
        bookDtos.add(getBooksDto());
        when(bookService.getBooksByTitle(anyString())).thenReturn(bookDtos);

        ResponseEntity<BookDto[]> response = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/books/test title",
                BookDto[].class
        );
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isEqualTo(1);
    }

    private BookDto getBooksDto() {
        return BookDto.builder()
                .id(UUID.randomUUID())
                .title("test title")
                .description("test description")
                .releaseYear(2026)
                .build();
    }

}

