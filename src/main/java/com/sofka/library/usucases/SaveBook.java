package com.sofka.library.usucases;

import com.sofka.library.models.BookDTO;
import reactor.core.publisher.Mono;

public interface SaveBook {
    Mono<String> apply(BookDTO bookDTO);
}
