package com.librarymanagementsystem.project.service;

import com.librarymanagementsystem.project.Dtos.BookDto;
import com.librarymanagementsystem.project.entity.BookEntity;

import java.util.List;

public interface LibraryService {
    String addBook(BookDto bookDto);



    List<BookEntity> getAllBooks();

    BookDto getById(Long id);

    String deleteBookDetails(Long id);

    BookDto edit(BookDto bookDto , Long id);

    List<BookEntity> searchDetails(String tittle, String author, String category, Long availableCopies);
}
