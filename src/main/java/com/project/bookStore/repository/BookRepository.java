package com.project.bookStore.repository;

import com.project.bookStore.model.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends CrudRepository<Book, UUID> {

    List<Book> findBooksByTitle(String title);

    List<Book> findBooksByTitleIgnoreCase(String title);


}
