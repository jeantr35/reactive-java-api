package com.sofka.library.routers;

import com.sofka.library.models.BookDTO;
import com.sofka.library.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BookRouter {

    @Bean
    public RouterFunction<ServerResponse> getAll(ListUseCase listUseCase) {
        return route(GET("/getAll"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(listUseCase.get(), BookDTO.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> create(CreateUseCase createUseCase) {
        Function<BookDTO, Mono<ServerResponse>> executor = bookDTO ->  createUseCase.apply(bookDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(result));

        return route(
                POST("/create").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(BookDTO.class).flatMap(executor)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> get(GetUseCase getUseCase) {
        return route(
                GET("/get/{id}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getUseCase.apply(
                                        request.pathVariable("id")),
                                BookDTO.class
                        ))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> update(UpdateUseCase updateUseCase) {
        Function<BookDTO, Mono<ServerResponse>> executor = bookDTO ->  updateUseCase.apply(bookDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(result));

        return route(
                PUT("/update").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(BookDTO.class).flatMap(executor)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> delete(DeleteUseCase deleteUseCase){
        return route(
                DELETE("/delete/{id}"),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(deleteUseCase.apply(request.pathVariable("id")), Void.class))
                        .onErrorResume((error) -> ServerResponse.badRequest().build())
        );
    }

    @Bean
    public RouterFunction<ServerResponse> borrow(BorrowUseCase borrowUseCase){
        return route(
                PUT("/borrow/{id}"),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(borrowUseCase.apply(request.pathVariable("id")), String.class))
                        .onErrorResume((error) -> ServerResponse.badRequest().build())
        );
    }

    @Bean
    public RouterFunction<ServerResponse> returnBook(ReturnUseCase returnUseCase){
        return route(
                PUT("/return/{id}"),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(returnUseCase.apply(request.pathVariable("id")), String.class))
                        .onErrorResume((error) -> ServerResponse.badRequest().build())
        );
    }

    @Bean
    public RouterFunction<ServerResponse> recommendation(RecommendationUseCase recommendationUseCase){
        return route(
                GET("/recommendation/{category}"),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(recommendationUseCase.apply(request.pathVariable("category")), BookDTO.class))
                        .onErrorResume((error) -> ServerResponse.badRequest().build())
        );
    }

}
