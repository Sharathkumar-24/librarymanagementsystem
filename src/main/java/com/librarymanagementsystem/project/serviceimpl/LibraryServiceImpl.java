package com.librarymanagementsystem.project.serviceimpl;

import com.librarymanagementsystem.project.Dtos.BookDto;
import com.librarymanagementsystem.project.entity.BookEntity;
import com.librarymanagementsystem.project.repository.BookRepository;
import com.librarymanagementsystem.project.service.LibraryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LibraryServiceImpl implements LibraryService {



    @Autowired
    private ModelMapper modelMapper;
    private final BookRepository bookRepository;
    @Override
    public String addBook(BookDto bookDto) {
        if ( bookDto.getAuthor()!=null && bookDto.getAuthor().trim().isEmpty() &&
                bookDto.getCategory()!=null && bookDto.getCategory().trim().isEmpty() &&
                bookDto.getCreatedAt()!=null &&
                bookDto.getTotalCopies()!=null){
            return "Sorry Something went Wrong (or) may be this Book already exists!! " ;
        }
        BookEntity book = modelMapper.map(bookDto, BookEntity.class);
        book.setAvailableCopies(book.getTotalCopies());
        book.setIsActive(true);
        bookRepository.save(book);
        return "Added Successfully";
    }

    @Override
    public List<BookEntity> getAllBooks() {
        List<BookEntity> bookDetails =bookRepository.findAll();
        return bookDetails;
    }


    @Override
    public BookDto getById(Long id) {
        Optional<BookEntity> idDetails = bookRepository.findById(id);
        BookDto bookDto = modelMapper.map(idDetails.get(), BookDto.class);
        return bookDto;

    }

    @Override
    public String deleteBookDetails(Long id) {
        Optional<BookEntity> idDetails = bookRepository.findById(id);
        if (!idDetails.isEmpty() && idDetails.get().getIsActive()!=false){
            idDetails.get().setIsActive(false);
            bookRepository.save(idDetails.get());
            return "Deleted Successfully";
        }
        return "Error!!";
    }

    @Override
    public BookDto edit(BookDto bookDto ,Long id) {
        Optional<BookEntity> Details = bookRepository.findById(id);
        BookDto modifiedBookDetails = new BookDto();
        if (!Details.isEmpty()){
            modelMapper.map(bookDto, Details.get());
            bookRepository.save(Details.get());
            modelMapper.map(Details.get(), modifiedBookDetails);
        }
        return modifiedBookDetails;
    }

    @Override
    public List<BookEntity> searchDetails(String title, String author,
                                          String category, Long availableCopies) {

        if (title!=null && !title.isBlank()){
            return bookRepository.findByTitleContainingIgnoreCase(title);
        }

        if (author!=null && !author.isBlank()){
           return bookRepository.findByAuthorContainingIgnoreCase(author);
        }

        if (category!=null && !category.isBlank()){
            return bookRepository.findByCategoryContainingIgnoreCase(category);
        }

        if (availableCopies!=null){
            return bookRepository.findByAvailableCopies(availableCopies);
        }

        return bookRepository.findAll();
    }


}
