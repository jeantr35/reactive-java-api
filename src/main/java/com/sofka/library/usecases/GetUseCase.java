package com.sofka.library.usecases;

import com.sofka.library.mappers.BookMapper;
import com.sofka.library.models.BookDTO;
import com.sofka.library.repositories.BookRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@Service
public class GetUseCase implements Function<String, Mono<BookDTO>> {
    private final BookRepository bookRepository;
    private final BookMapper mapper;

    public GetUseCase(BookRepository bookRepository, BookMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<BookDTO> apply(String id) {
        Objects.requireNonNull(id, "Id is required");
        return bookRepository.findById(id)
                .map(mapper.mapEntityToBook());
    }
}
