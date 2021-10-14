package com.sofka.library.usecases;

import com.sofka.library.mappers.BookMapper;
import com.sofka.library.repositories.BookRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
public class DeleteUseCase implements Function<String, Mono<Void>> {

    private final BookRepository bookRepository;
    private final BookMapper mapper;

    public DeleteUseCase(BookRepository bookRepository, BookMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Void> apply(String id) {
        Objects.requireNonNull(id, "Id required");
        return bookRepository.deleteById(id);
    }
}
