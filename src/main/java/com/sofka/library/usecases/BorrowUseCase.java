package com.sofka.library.usecases;

import com.sofka.library.collections.Book;
import com.sofka.library.mappers.BookMapper;
import com.sofka.library.repositories.BookRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.function.Function;

@Service
public class BorrowUseCase implements Function<String, Mono<String>> {

    private final BookRepository bookRepository;
    private final BookMapper mapper;

    public BorrowUseCase(BookRepository bookRepository, BookMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    private Mono<String> setBorrowed(String id){
        return bookRepository.findById(id).flatMap(
                book -> {
                    if(book.getBorrowed()) {
                        return Mono.just("El libro con ID: " + book.getId() + " no se encuentra disponible," +
                                " fue prestado en la fecha " + book.getDate());
                    }
                    book.setDate(LocalDateTime.now().toString());
                    book.setBorrowed(true);
                    return bookRepository.save(book).thenReturn("Libro prestado en la fecha: " + book.getDate());
                }
        );
    }

    @Override
    public Mono<String> apply(String id) {
        return setBorrowed(id);
    }
}
