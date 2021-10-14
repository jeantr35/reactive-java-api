package com.sofka.library.usecases;

import com.sofka.library.mappers.BookMapper;
import com.sofka.library.models.BookDTO;
import com.sofka.library.repositories.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@Validated
public class ListUseCase implements Supplier<Flux<BookDTO>> {

    private final BookRepository bookRepository;
    private final BookMapper mapper;

    public ListUseCase(BookRepository bookRepository, BookMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    @Override
    public Flux<BookDTO> get() {
        return bookRepository.findAll().
                map(mapper.mapEntityToBook());
    }
}
