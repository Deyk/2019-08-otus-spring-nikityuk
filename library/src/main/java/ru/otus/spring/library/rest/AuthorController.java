package ru.otus.spring.library.rest;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.library.domain.Author;
import ru.otus.spring.library.rest.model.AuthorDto;
import ru.otus.spring.library.service.AuthorService;
import ru.otus.spring.library.service.LibraryServiceException;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public String showAuthors(Model model) {
        List<Author> allAuthors = authorService.getAllAuthors();
        model.addAttribute("authors", allAuthors.stream().map(AuthorDto::toDto).collect(Collectors.toList()));
        return "list";
    }

    @GetMapping("/authors/edit")
    public String editPage(
            @RequestParam("id") String id,
            Model model) throws LibraryServiceException {
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        return "edit";
    }

    @PostMapping("/authors/edit")
    public ResponseEntity<AuthorDto> editAuthors(
            AuthorRequest request) throws LibraryServiceException {
        Author author = authorService.updateAuthor(request.getId(), request.getName());
        return ResponseEntity.ok().body(AuthorDto.toDto(author));
    }

    @ExceptionHandler(LibraryServiceException.class)
    public ResponseEntity<String> handleNotEnoughFunds(LibraryServiceException ex) {
        return ResponseEntity.badRequest().body("Not found");
    }

    @Getter
    class AuthorRequest {
        private String id;
        private String name;
    }
}
