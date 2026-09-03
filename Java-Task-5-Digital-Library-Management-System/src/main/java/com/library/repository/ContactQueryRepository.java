package com.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.entity.ContactQuery;

public interface ContactQueryRepository extends JpaRepository<ContactQuery, Integer>{

}
