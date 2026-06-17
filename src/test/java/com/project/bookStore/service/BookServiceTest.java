package com.project.bookStore.service;

import com.project.bookStore.dto.BookDto;
import com.project.bookStore.model.Book;
import com.project.bookStore.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper mapper;


    @Test
    void shouldReturnListOfBookDtoWhenGetBooksCalled() {
        List<Book> books = new ArrayList<>();
        Book book = getBook();
        books.add(book);
        BookDto bookDto = getBookDto();

        when(bookRepository.findAll()).thenReturn(books);
        when(mapper.map(book, BookDto.class)).thenReturn(bookDto);
        List<BookDto> bookDtos = bookService.getBooks();


        assertThat(1).isEqualTo(bookDtos.size());
        assertThat(bookDtos.getFirst())
                .isNotNull()
                .hasFieldOrPropertyWithValue("title", "book title")
                .hasFieldOrPropertyWithValue("description", "book description")
                .hasFieldOrPropertyWithValue("releaseYear", 2026);

    }

    @Test
    void shouldReturnBooksByTitleIgnoreCase() {
        List<Book> books = new ArrayList<>();
        Book book = getBook();
        BookDto bookDto = getBookDto();
        books.add(book);
        when(bookRepository.findBooksByTitleIgnoreCase(anyString())).thenReturn(books);
        when(mapper.map(book, BookDto.class)).thenReturn(bookDto);

        List<BookDto> bookDtoList = bookService.getBooksByTitle("test title");

        assertThat(bookDtoList.size()).isEqualTo(1);
    }


    private Book getBook() {
        return Book.builder()
                .id(UUID.randomUUID())
                .title("test title")
                .description("test description")
                .releaseYear(2026)
                .build();
    }

    private BookDto getBookDto() {
        return BookDto.builder()
                .id(UUID.randomUUID())
                .title("test title")
                .description("test description")
                .releaseYear(2026)
                .build();
    }
}
