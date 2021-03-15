package com.example.demo.required;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MissingServletRequestParameterException;

import static org.assertj.core.api.Assertions.*;
import static com.example.demo.required.BookConverter.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Burkhard Graves
 */
@WebMvcTest(BookController.class)
public class BookRequestParamTests {

    @Autowired
    private MockMvc mvc;
        
    @Test
    void theNormalCase() throws Exception {
        this.mvc.perform(get("/rp/book").param("id", ID_OF_EXISTANT_BOOK))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID_OF_EXISTANT_BOOK));
    }

    @Test
    void noRequestParamThrowsMissingServletRequestParameterException() throws Exception {
        this.mvc.perform(get("/rp/book"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(MissingServletRequestParameterException.class));
    }
    
    // since 5.3 - I don't like it, but ok ;-)
    @Test
    void emptyRequestParamThrowsMissingServletRequestParameterException() throws Exception {
        this.mvc.perform(get("/rp/book").param("id", ""))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(MissingServletRequestParameterException.class));
    }

    // since 5.3 - the misleading exception
    @Test
    void idOfDeletedBookThrowsMissingServletRequestParameterException() throws Exception {
        this.mvc.perform(get("/rp/book").param("id", ID_OF_DELETED_BOOK))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(MissingServletRequestParameterException.class));
    }

    // I don't like it, but ok ;-)
    @Test
    void noRequestParamForNullableBook() throws Exception {
        this.mvc.perform(get("/rp/nullable_book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
    
    // I don't like it, but ok ;-)
    @Test
    void noRequestParamForOptionalBook() throws Exception {
        this.mvc.perform(get("/rp/optional_book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
