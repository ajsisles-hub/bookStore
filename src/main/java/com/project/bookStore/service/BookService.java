package com.project.bookStore.service;

import com.project.bookStore.dto.BookDto;
import com.project.bookStore.model.Book;
import com.project.bookStore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public List<BookDto> getBooks() {
        Iterable<Book> all = bookRepository.findAll();
        return StreamSupport.stream(all.spliterator(), false)
                .map(convertBookModelToBookDTO())
                .collect(Collectors.toList())
                ;

    }

    private @NonNull Function<Book, BookDto> convertBookModelToBookDTO() {
        return book -> modelMapper.map(book, BookDto.class);
    }

    public List<BookDto> getBooksUsingMapper(){
        Iterable<Book> all = bookRepository.findAll();
        return StreamSupport.stream(all.spliterator(),false)
                .map(convertBookModelToBookDTO())
                .collect(Collectors.toList());
    }
}
