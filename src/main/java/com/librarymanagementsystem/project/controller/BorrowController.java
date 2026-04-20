package com.librarymanagementsystem.project.controller;

import com.librarymanagementsystem.project.Dtos.BorrowDto;
import com.librarymanagementsystem.project.service.BorrowService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/apiBorrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

//    @PostMapping("/addBorrowDetails")
//    public String addingBorrowDetails (@RequestBody BorrowDto borrowDto){
//        return borrowService.addingBorrowDetails(borrowDto);
//    }

    @PostMapping("/borrow")
    public String borrowingBook (@RequestBody  BorrowDto borrowDto){
        return borrowService.borrowingBook(borrowDto);
    }

    @PutMapping("/returnbook/{userId}/{bookId}")
    public String returnBook (@PathVariable Long userId, @PathVariable Long bookId){
        return borrowService.returnBook(userId,bookId);
    }
}
