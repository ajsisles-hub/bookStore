package com.project.bookStore.repository;

import com.project.bookStore.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.StreamSupport;

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
        List<Book> theFirm = bookRepository.findBooksByTitle("The Firm");
        Assertions.assertEquals(1, theFirm.size());
    }

}
