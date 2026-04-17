package com.librarymanagementsystem.project.serviceimpl;

import com.librarymanagementsystem.project.Dtos.BorrowDto;
import com.librarymanagementsystem.project.Dtos.UserDto;
import com.librarymanagementsystem.project.entity.BookEntity;
import com.librarymanagementsystem.project.entity.BorrowEntity;
import com.librarymanagementsystem.project.entity.UserEntity;
import com.librarymanagementsystem.project.enums.BorrowStatus;
import com.librarymanagementsystem.project.repository.BookRepository;
import com.librarymanagementsystem.project.repository.BorrowRepository;
import com.librarymanagementsystem.project.repository.UserRepository;
import com.librarymanagementsystem.project.service.BorrowService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private BookRepository bookRepository;

    @Override
    public String addingBorrowDetails(BorrowDto borrowDto) {
        if (borrowDto.getUserId()!=0 && borrowDto.getBookId()!=0 ){

            BorrowEntity Details = new BorrowEntity();
            UserEntity userDetails =userRepository.findById(borrowDto.getUserId()).orElseThrow();
            BookEntity bookEntity = bookRepository.findById(borrowDto.getBookId()).orElseThrow();
            Details.setUserName(userDetails.getName());
            Details.setBook(bookEntity.getTitle());
            Details.setIssueDate(LocalDate.now());
            Details.setDueDate(LocalDate.now().plusDays(7));
            Details.setBorrowStatus(BorrowStatus.BORROWED);
            Details.setFineAmount(0.0);
            borrowRepository.save(Details);
            return "Added Borrowed Details";

        }
        return "invalid input";

    }
}
