package com.librarymanagementsystem.project.repository;

import com.librarymanagementsystem.project.entity.BorrowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<BorrowEntity,Long> {
   // @Query ("select * from borrow_entity where user_name=")
    List<BorrowEntity> findByUserName(String userName);
}
