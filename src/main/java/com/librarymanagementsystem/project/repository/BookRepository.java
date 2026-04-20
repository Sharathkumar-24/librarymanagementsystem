package com.librarymanagementsystem.project.repository;


import com.librarymanagementsystem.project.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity,Long> {
    @Query(value = "select Distinct type from bookEntity",nativeQuery = true)
    List<String> getProductTypes();


    List<BookEntity> findByTitleContainingIgnoreCase(String title);

    List<BookEntity> findByAuthorContainingIgnoreCase(String author);

    List<BookEntity> findByCategoryContainingIgnoreCase(String category);

    List<BookEntity> findByAvailableCopies(Long availableCopies);
}
