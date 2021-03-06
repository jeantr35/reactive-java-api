package com.sofka.library.mappers;

import com.sofka.library.collections.Book;
import com.sofka.library.models.BookDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@Component
public class BookMapper {

    public Function<BookDTO, Book> mapperToBook(String id) {
        return updateBook -> {
            var book = new Book();
            book.setId(id);
            book.setName(updateBook.getName());
            book.setBorrowed(updateBook.getBorrowed());
            book.setCategory(updateBook.getCategory());
            book.setDate(updateBook.getDate());
            return book;
        };
    }

    public Function<Book, BookDTO> mapEntityToBook(){
        return entity ->  new BookDTO(entity.getId(), entity.getName(), entity.getCategory(), entity.getBorrowed(), entity.getDate());
    }

}
