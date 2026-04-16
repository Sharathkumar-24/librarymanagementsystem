package com.librarymanagementsystem.project.repository;

import com.librarymanagementsystem.project.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    @Query(value = "select Distinct type from userEntity",nativeQuery = true)
    List<String> getProductTypes();
}
