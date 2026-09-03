package com.library.service;

import java.util.List;

import com.library.entity.Fine;

public interface FineService {

    Fine saveFine(Fine fine);

    Fine getFineById(Integer fineId);

    List<Fine> getAllFines();
    
    List<Fine> getFinesByUserId(Integer userId);

    Fine getFineByIssueId(Integer issueId);

    void deleteFine(Integer fineId);
}