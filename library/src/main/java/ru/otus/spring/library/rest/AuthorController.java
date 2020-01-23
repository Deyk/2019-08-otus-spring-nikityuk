package ru.otus.spring.library.rest;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.rest.model.AuthorDto;
import ru.otus.spring.library.service.AuthorService;
import ru.otus.spring.library.service.LibraryServiceException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public List<AuthorDto> getAllAuthors() {
        List<Author> allAuthors = authorService.getAllAuthors();
        return allAuthors.stream().map(AuthorDto::toDto).collect(Collectors.toList());
    }

    @PostMapping("/authors/add")
    public AuthorDto addAuthor(@RequestParam("authorName") String authorName) {
        Author author = authorService.addAuthor(authorName);
        return new AuthorDto(author);
    }

    @PostMapping("/authors/edit")
    public AuthorDto editAuthors(@RequestBody AuthorRequest request) throws LibraryServiceException {
        Author author = authorService.updateAuthor(request.getId(), request.getAuthorName());
        return new AuthorDto(author);
    }

    @DeleteMapping("/authors/delete")
    public Boolean deleteAuthor(@RequestParam("id") String id) {
        try {
            authorService.deleteAuthorById(id);
        } catch (LibraryServiceException e) {
            return false;
        }
        return true;
    }

    @ExceptionHandler(LibraryServiceException.class)
    public ResponseEntity<String> handleNotEnoughFunds(LibraryServiceException ex) {
        return ResponseEntity.badRequest().body("Not found author");
    }

    @Data
    class AuthorRequest {
        private String id;
        private String authorName;
    }
}
