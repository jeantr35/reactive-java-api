package com.sofka.library.repositories;

import com.sofka.library.collections.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book, String> {
    Flux<Book> findByCategory(String category);
}
