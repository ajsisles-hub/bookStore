package com.project.bookStore.controller;

import com.project.bookStore.dto.BookDto;
import com.project.bookStore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;@RestController
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
// Completed your description string here
@Tag(name = "Book Controller", description = "APIs for managing the Book Store inventory")
public class BookController {

    private final BookService bookService;

    @Operation(
            summary = "Retrieve all books",
            description = "Fetches a comprehensive list of all book titles available in the store database."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the list of books",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class) // Declares what the payload looks like
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error occurred while fetching books",
                    content = @Content
            )
    })
    @GetMapping
    public ResponseEntity<List<BookDto>> getBooks() {
        List<BookDto> books1 = bookService.getBooks();
        return ResponseEntity.ok(books1);
    }
}
