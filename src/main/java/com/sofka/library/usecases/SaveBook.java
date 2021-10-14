package com.sofka.library.usecases;

import com.sofka.library.models.BookDTO;
import reactor.core.publisher.Mono;

public interface SaveBook {
    Mono<String> apply(BookDTO bookDTO);
}
