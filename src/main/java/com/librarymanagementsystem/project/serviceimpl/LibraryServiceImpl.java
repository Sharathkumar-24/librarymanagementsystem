package com.librarymanagementsystem.project.serviceimpl;

import com.librarymanagementsystem.project.Dtos.BookDto;
import com.librarymanagementsystem.project.entity.BookEntity;
import com.librarymanagementsystem.project.repository.BookRepository;
import com.librarymanagementsystem.project.service.LibraryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LibraryServiceImpl implements LibraryService {



    @Autowired
    private ModelMapper modelMapper;
    private final BookRepository bookRepository;
    @Override
    public void addBook(BookDto bookDto) {

        BookEntity book = modelMapper.map(bookDto, BookEntity.class);
        book.setAvailableCopies(book.getTotalCopies());
        book.setIsActive(true);
        bookRepository.save(book);

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
    public Boolean deleteBookDetails(Long id) {
        Optional<BookEntity> idDetails = bookRepository.findById(id);
        if (!idDetails.isEmpty()){
            idDetails.get().setIsActive(false);
            bookRepository.save(idDetails.get());
            return true;
        }
        return false;
    }

    @Override
    public BookDto edit(BookDto bookDto ,Long id) {
        Optional<BookEntity> Details = bookRepository.findById(id);
        BookDto modifiedBookDetails = new BookDto();
        if (!Details.isEmpty()){
            modelMapper.map(bookDto,Details.get());
            bookRepository.save(Details.get());
            modelMapper.map(Details.get(),modifiedBookDetails);
        }
        return modifiedBookDetails;
    }


}
