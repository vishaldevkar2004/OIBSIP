package com.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.entity.BookIssue;

public interface BookIssueRepository extends JpaRepository<BookIssue, Integer> {

    List<BookIssue> findByUserId(Integer userId);
}