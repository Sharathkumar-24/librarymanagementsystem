package com.librarymanagementsystem.project.service;

import com.librarymanagementsystem.project.Dtos.BorrowDto;

public interface BorrowService {
   // String addingBorrowDetails(BorrowDto borrowDto);

    String borrowingBook(BorrowDto borrowDto);

    String returnBook(Long userId, Long bookId);
}
