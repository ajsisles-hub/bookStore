package com.project.bookStore.controller;

import com.project.bookStore.dto.BookDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookController {



    @GetMapping
    public ResponseEntity<List<BookDto>> getBooks() {

        BookDto book = BookDto.builder()
                .title("Project Hail Mary")
                .build();

        List<BookDto> books = Collections.singletonList(book);

        return ResponseEntity.ok(books);
    }

}
