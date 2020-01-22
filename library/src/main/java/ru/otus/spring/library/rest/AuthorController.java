package ru.otus.spring.library.rest;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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

//    @PostMapping("/authors/add")
//    public String addAuthor(@RequestParam("name") String name) {
//        authorService.addAuthor(name);
//        return "redirect:/authors";
//    }
//
//    @GetMapping("/authors/edit")
//    public String editPage(
//            @RequestParam("id") String id,
//            Model model) throws LibraryServiceException {
//        Author author = authorService.getAuthorById(id);
//        model.addAttribute("author", author);
//        return "authorEdit";
//    }
//
//    @PostMapping("/authors/edit")
//    public String editAuthors(AuthorRequest request) throws LibraryServiceException {
//        authorService.updateAuthor(request.getId(), request.getName());
//        return "redirect:/authors";
//    }
//
//    @DeleteMapping("/authors/delete")
//    public String deleteAuthor(@RequestParam("id") String id) {
//        try {
//            authorService.deleteAuthorById(id);
//        } catch (LibraryServiceException e) {
//            return "redirect:/authors";
//        }
//        return "redirect:/authors";
//    }
//
//    @ExceptionHandler(LibraryServiceException.class)
//    public ResponseEntity<String> handleNotEnoughFunds(LibraryServiceException ex) {
//        return ResponseEntity.badRequest().body("Not found author");
//    }
//
//    @Data
//    class AuthorRequest {
//        private String id;
//        private String name;
//    }
}
