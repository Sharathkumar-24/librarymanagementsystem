package com.librarymanagementsystem.project.serviceimpl;

import com.librarymanagementsystem.project.Dtos.BookDto;
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
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static com.librarymanagementsystem.project.enums.BorrowStatus.BORROWED;

@Service
@AllArgsConstructor
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    private BorrowRepository borrowRepository;
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private BookRepository bookRepository;

//    @Override
//    public String addingBorrowDetails(BorrowDto borrowDto) {
//        if (borrowDto.getUserId()!=0 && borrowDto.getBookId()!=0 ){
//
//            BorrowEntity Details = new BorrowEntity();
//            UserEntity userDetails =userRepository.findById(borrowDto.getUserId()).orElseThrow();
//            BookEntity bookEntity = bookRepository.findById(borrowDto.getBookId()).orElseThrow();
//            Details.setUserName(userDetails.getName());
//            Details.setBook(bookEntity.getTitle());
//            Details.setIssueDate(LocalDate.now());
//            Details.setDueDate(LocalDate.now().plusDays(7));
//            Details.setBorrowStatus(BORROWED);
//            Details.setFineAmount(0.0);
//            borrowRepository.save(Details);
//            return "Added Borrowed Details";
//
//        }
//        return "invalid input";
//
//    }

    @Override
    public String borrowingBook(BorrowDto borrowDto) {
        BorrowEntity Details = new BorrowEntity();
        Optional<BookEntity> bookEntity = bookRepository.findById(borrowDto.getBookId());
        Optional<UserEntity> userEntity = userRepository.findById(borrowDto.getUserId());

        if (bookEntity.isEmpty()) {
            return "Book not found";
        }

        if (userEntity.isEmpty()) {
            return "User not found";
        }

        boolean exists = borrowRepository.existsByUserIdAndBookIdAndBorrowStatus(borrowDto.getUserId(),
                            borrowDto.getBookId() , BorrowStatus.BORROWED);

        if(exists){
            return "You already borrowed that book";
        }

        BookEntity book = bookEntity.get();
        UserEntity user = userEntity.get();

        Long noOfBooksAvailable =book.getAvailableCopies();
        int noOfBorrowedBooks = user.getBorrowedBooks();


        if (noOfBooksAvailable>=1 && noOfBorrowedBooks < user.getMaxBooksAllowed()){
            Details.setUserId(user.getUserid());
            Details.setUserName(user.getName());
            Details.setBook(book.getTitle());
            Details.setBookId(book.getBookId());
            Details.setIssueDate(LocalDate.now());
            Details.setDueDate(LocalDate.now().plusDays(14));
            Details.setBorrowStatus(BorrowStatus.BORROWED);
            Details.setFineAmount(0.0);
            borrowRepository.save(Details);

            book.setAvailableCopies(book.getAvailableCopies()-1);
            bookRepository.save(book);

            user.setBorrowedBooks(user.getBorrowedBooks()+1);
            userRepository.save(user);

            return "Added Borrowed Details";

        }
        return "no books available or max books taken ";
    }

    @Override
    public String returnBook(Long userId, Long bookId) {

        Optional<BorrowEntity> borrowOpt = borrowRepository.findByUserIdAndBookId(userId, bookId);

        if (borrowOpt.isPresent() ){
            BorrowEntity borrowDetails = borrowOpt.get();

            if(borrowDetails.getBorrowStatus().equals(BORROWED)){

                borrowDetails.setReturnDate(LocalDate.now());
                if (LocalDate.now().isAfter(borrowDetails.getDueDate())) {
                    long days = ChronoUnit.DAYS.between(borrowDetails.getDueDate(), LocalDate.now());
                    double amount = days * 10;
                    borrowDetails.setFineAmount(amount);
                } else borrowDetails.setFineAmount(0.0);
                //pending update every tabel like available copies
                borrowDetails.setBorrowStatus(BorrowStatus.RETURNED);
                borrowRepository.save(borrowDetails);

                Optional<UserEntity> byId = userRepository.findById(userId);
                UserEntity userData = byId.get();
                userData.setBorrowedBooks(userData.getBorrowedBooks() - 1);
                userRepository.save(userData);

                Optional<BookEntity> bkId = bookRepository.findById(bookId);
                BookEntity bookData = bkId.get();
                bookData.setAvailableCopies(bookData.getAvailableCopies() + 1);
                bookRepository.save(bookData);

                return "Thank you returning the book \n" + "your due fee is: " + borrowDetails.getFineAmount();
            }
            return "this book is already returned";
        }

        return " incorrrect data!!";

    }
}
