package com.example.demo.required;

import java.util.Optional;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @GetMapping(path = "/rp/book")
    public Book getBookByRequestParam(@RequestParam("id") Book book) {
        return book;
    }

    @GetMapping("/rp/nullable_book")
    public Book getNullableBookByRequestParam(@RequestParam("id") @Nullable Book book) {
        return book;
    }

    @GetMapping("/rp/optional_book")
    public Book getOptionalBookByRequestParam(@RequestParam("id") Optional<Book> book) {
        return book.orElse(null);
    }

    @GetMapping({"/pv/book/{id}"})
    public Book getBookByPathVariable(@PathVariable("id") Book book) {
        return book;
    }

    @GetMapping({"/cc/book", "/cc/book/{id}"})
    public Book constructedCase(@PathVariable("id") Book book) {
        return book;
    }

    @GetMapping("/pv/nullable_book/{id}")
    public Book getNullableBookByPathVariable(@PathVariable("id") @Nullable Book book) {
        return book;
    }

    @GetMapping("/pv/optional_book/{id}")
    public Book getOptionalBookByPathVariable(@PathVariable("id") Optional<Book> book) {
        return book.orElse(null);
    }
}
