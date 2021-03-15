package com.example.demo.required;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MissingPathVariableException;

import static org.assertj.core.api.Assertions.*;
import static com.example.demo.required.BookConverter.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

/**
 * @author Burkhard Graves
 */
@WebMvcTest(BookController.class)
public class BookPathVariableTests {

    @Autowired
    private MockMvc mvc;
        
    @Test
    void theNormalCase() throws Exception {
        this.mvc.perform(get("/pv/book/{id}", ID_OF_EXISTANT_BOOK))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID_OF_EXISTANT_BOOK));
    }

    @Test
    void missingValueNotHandledByController() throws Exception {
        this.mvc.perform(get("/pv/book/"))
                .andExpect(status().isNotFound())
                .andExpect(handler().handlerType(ResourceHttpRequestHandler.class));
    }
    
    // since 5.3 - the misleading exception, and why InternalServerError?
    @Test
    void idOfDeletedBookThrowsMissingPathVariableException() throws Exception {
        this.mvc.perform(get("/pv/book/{id}", ID_OF_DELETED_BOOK))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(MissingPathVariableException.class));
    }

    @Test
    void idOfDeletedBookWithNullableBook() throws Exception {
        this.mvc.perform(get("/pv/nullable_book/{id}", ID_OF_DELETED_BOOK))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void idOfDeletedBookWithOptionalBook() throws Exception {
        this.mvc.perform(get("/pv/optional_book/{id}", ID_OF_DELETED_BOOK))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
    
    // and two constructed cases
    @Test
    void cc1ThrowsMissingPathVariableException() throws Exception {
        this.mvc.perform(get("/cc/book/{id}", ID_OF_DELETED_BOOK))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(MissingPathVariableException.class));
    }
    
    @Test
    void cc2ThrowsMissingPathVariableException() throws Exception {
        this.mvc.perform(get("/cc/book"))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(MissingPathVariableException.class));
    }
}
