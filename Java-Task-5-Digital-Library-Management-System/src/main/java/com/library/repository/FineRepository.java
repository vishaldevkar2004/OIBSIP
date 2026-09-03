package com.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.entity.Fine;

public interface FineRepository extends JpaRepository<Fine, Integer> {

    List<Fine> findByUserId(Integer userId);

    Fine findByIssueId(Integer issueId);
}