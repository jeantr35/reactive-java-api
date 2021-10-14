package com.sofka.library.usecases;

import com.sofka.library.collections.Book;
import com.sofka.library.mappers.BookMapper;
import com.sofka.library.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class ListUseCaseTest {

    BookRepository repository;
    ListUseCase listUseCase;


    @BeforeEach
    public void setup(){
        BookMapper mapperUtils = new BookMapper();
        repository = mock(BookRepository.class);
        listUseCase = new ListUseCase(repository, mapperUtils);
    }

    @Test
    void getValidationTest(){
        var book =  new Book();
        book.setId("xxxx-xxxx");
        book.setCategory("tech");
        book.setName("software");
        book.setBorrowed(false);
        when(repository.findAll()).thenReturn(Flux.just(book));

        StepVerifier.create(listUseCase.get())
                .expectNextMatches(bookDTO -> {
                    assert bookDTO.getId().equals("xxxx-xxxx");
                    assert bookDTO.getName().equals("software");
                    assert bookDTO.getBorrowed().equals(false);
                    assert bookDTO.getCategory().equals("tech");
                    return true;
                })
                .verifyComplete();

        verify(repository).findAll();
    }

}