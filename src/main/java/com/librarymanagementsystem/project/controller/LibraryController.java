package com.librarymanagementsystem.project.controller;

import com.librarymanagementsystem.project.Dtos.BookDto;
import com.librarymanagementsystem.project.entity.BookEntity;
import com.librarymanagementsystem.project.service.LibraryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Library")
public class LibraryController {

    private LibraryService libraryService;

    @PostMapping("/addbook")
    public String addbook(@RequestBody BookDto bookDto){
        return libraryService.addBook(bookDto);
    }


    @GetMapping("/getAllBooks")
    public List<BookEntity> getAllBooks(){
        return libraryService.getAllBooks();
    }


    @GetMapping("/getbook/{id}")
    public BookDto getById(@PathVariable Long id){
        return libraryService.getById(id);
    }


    @DeleteMapping("/deletebook/{id}")
    public String  deleteBookDetails (@PathVariable Long id){
        return libraryService.deleteBookDetails(id);
    }

    @PutMapping("/edit/{id}")
    public BookDto edit(@RequestBody BookDto bookDto ,@PathVariable Long id){
        return libraryService.edit(bookDto,id);
    }

    @GetMapping("/search")
    public List<BookEntity> searchDetails (@RequestParam (required = false) String title,
                                        @RequestParam (required = false) String author,
                                        @RequestParam (required = false) String category ,
                                        @RequestParam (required = false) Long availableCopies){
        return libraryService.searchDetails(title,author,category, availableCopies);
    }
}
