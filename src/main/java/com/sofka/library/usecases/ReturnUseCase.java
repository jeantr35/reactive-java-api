package com.sofka.library.usecases;

import com.sofka.library.collections.Book;
import com.sofka.library.mappers.BookMapper;
import com.sofka.library.repositories.BookRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.function.Function;

@Service
public class ReturnUseCase implements Function<String, Mono<String>> {

    private final BookRepository bookRepository;
    private final BookMapper mapper;

    public ReturnUseCase(BookRepository bookRepository, BookMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    private Mono<Boolean> isBorrowed(String id){
        return bookRepository.findById(id).map(
                Book::getBorrowed
        );
    }

    private Mono<String> setBorrowed(String id){
        return bookRepository.findById(id).flatMap(
                book -> {
                    if(book.getBorrowed()) {
                        book.setDate(LocalDateTime.now().toString());
                        book.setBorrowed(false);
                        return bookRepository.save(book).thenReturn("Libro devuelto en la fecha: " + book.getDate());
                    }
                    return Mono.just("El libro con ID: " + book.getId() + " no se puede devolver ya que no ha sido prestado");
                }
        );
    }

    @Override
    public Mono<String> apply(String id) {
        return setBorrowed(id);
    }
}
