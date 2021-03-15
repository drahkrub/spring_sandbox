package com.example.demo.required;

import org.springframework.core.convert.converter.Converter;

public class BookConverter implements Converter<String, Book> {

    public static final String ID_OF_DELETED_BOOK = "ID_OF_DELETED_BOOK";
    public static final String ID_OF_EXISTANT_BOOK = "42";

    @Override
    public Book convert(String id) {
        id = (id == null) ? "" : id.trim(); // sanitize id
        return id.isEmpty() || id.equals(ID_OF_DELETED_BOOK)
                ? null
                : new Book(id);
    }
}
