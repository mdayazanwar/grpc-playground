package com.ayaz.sec03;

import com.ayaz.models.sec03.Book;
import com.ayaz.models.sec03.Library;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Lec05Collection {
    private static final Logger log  = LoggerFactory.getLogger(Lec05Collection.class);

    public static void main(String[] args) {
        var book1 = Book.newBuilder()
                .setTitle("Harry Porter - Part 1")
                .setAuthor("j k rowling")
                .setPublicaitonYear(1997)
                .build();

        var book2 = Book.newBuilder()
                .setTitle("Harry Potter - Part 2")
                .setAuthor("j k rowling")
                .setPublicaitonYear(1998)
                .build();

        var book3 = Book.newBuilder()
                .setTitle("Harry Potter - Part 3")
                .setAuthor("j k rowling")
                .setPublicaitonYear(1999)
                .build();


        var library = Library.newBuilder()
                .setName("Fantasy Library")
//                .addBooks(book1)
//                .addBooks(book2)
//                .addBooks(book3)
                .addAllBooks(List.of(book1, book2, book3))
                .build();

        log.info("{}", library);

    }
}
