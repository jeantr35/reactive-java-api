package com.sofka.library.usecases;

import com.sofka.library.mappers.BookMapper;
import com.sofka.library.models.BookDTO;
import com.sofka.library.repositories.BookRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class RecommendationUseCase implements Function<String, Flux<BookDTO>> {

    private final BookRepository bookRepository;
    private final BookMapper mapper;

    public RecommendationUseCase(BookRepository bookRepository, BookMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }
    @Override
    public Flux<BookDTO> apply(String category) {
        return bookRepository.findByCategory(category).
                map(mapper.mapEntityToBook());
    }
}
