package com.project.bookStore.controller;

import com.project.bookStore.dto.BookDto;
import com.project.bookStore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.List;
@RestController
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


    @GetMapping
    public ResponseEntity<List<BookDto>> getBooks() {

        BookDto book = BookDto.builder()
                .title("Project Hail Mary- v2")
                .build();

        List<BookDto> books = Collections.singletonList(book);

        List<BookDto> books1 = bookService.getBooks();

        return ResponseEntity.ok(books1);
    }

}
