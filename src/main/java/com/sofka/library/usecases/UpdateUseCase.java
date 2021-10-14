package com.sofka.library.usecases;

import com.sofka.library.collections.Book;
import com.sofka.library.mappers.BookMapper;
import com.sofka.library.models.BookDTO;
import com.sofka.library.repositories.BookRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateUseCase implements SaveBook{

    private final BookRepository bookRepository;
    private final BookMapper mapper;

    public UpdateUseCase(BookRepository bookRepository, BookMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<String> apply(BookDTO bookDTO) {
        return bookRepository.existsById(bookDTO.getId()).
                then(bookRepository.save(
                        mapper.mapperToBook(bookDTO.getId()).apply(bookDTO))
                        .map(Book::getId)
        );
    }
}
