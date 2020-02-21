package ru.otus.spring.library.rest;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.rest.model.AuthorDto;
import ru.otus.spring.library.service.AuthorService;
import ru.otus.spring.library.service.LibraryServiceException;

import java.util.List;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public Mono<List<AuthorDto>> getAllAuthors() {
        return authorService.getAllAuthors().map(AuthorDto::toDto).collectList();
    }

    @PostMapping("/authors/add")
    public AuthorDto addAuthor(@RequestParam("authorName") String authorName) {
        Author author = authorService.addAuthor(authorName);
        return new AuthorDto(author);
    }

    @PostMapping("/authors/edit")
    public Publisher<ResponseEntity<AuthorDto>> editAuthors(@RequestBody AuthorDto authorDto) {
        return Mono.just(authorDto)
                .flatMap(a -> authorService.updateAuthor(a.getId(), a.getName()))
                .map(a -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .build());
    }

    @DeleteMapping("/authors/delete")
    public ResponseEntity deleteAuthor(@RequestParam("id") String id) {
        authorService.deleteAuthorById(id);
        return ResponseEntity.ok().body(true);
    }

    @ExceptionHandler(LibraryServiceException.class)
    public ResponseEntity<String> handleNotEnoughFunds(LibraryServiceException ex) {
        return ResponseEntity.badRequest().body("Not found author");
    }
}
