package com.project.bookStore.repository;

import com.project.bookStore.dto.BookDto;
import com.project.bookStore.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DataJpaTest // for jpa only - transactional
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @Sql(scripts = {"classpath:InsertInitialBookRecordForTest.sql"})
    void shouldReturnAllBooks() {
        Iterable<Book> all = bookRepository.findAll();
        Long totalBookCount = StreamSupport.stream(all.spliterator(), false).count();
        Assertions.assertEquals(totalBookCount, 2);
    }

    @Test
    @Sql(scripts = {"classpath:InsertInitialBookRecordForTest.sql"})
    void shouldReturnOneBookWhenTitleIsTheFirm() {
        List<Book> theFirm = bookRepository.findBooksByTitle("The Firm"); // this function is case-sensitive
        Assertions.assertEquals(1, theFirm.size());
    }

    @Test
    void createBook() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("The Firm");
        bookDto.setDescription("The Firm Description");
    }

    @Test
    @Sql(scripts = {"classpath:InsertInitialBookRecordForTest.sql"})
    void shouldReturnOneBookWhenTitleIsTheFirmIgnorecase() {
        List<Book> theFirm = bookRepository.findBooksByTitleIgnoreCase("the firm");
        Assertions.assertEquals(1, theFirm.size());
    }



}
