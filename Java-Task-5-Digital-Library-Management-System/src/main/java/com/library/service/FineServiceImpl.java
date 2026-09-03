package com.library.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.library.entity.Fine;
import com.library.repository.FineRepository;

@Service
public class FineServiceImpl implements FineService {

    private final FineRepository fineRepository;

    public FineServiceImpl(FineRepository fineRepository) {
        this.fineRepository = fineRepository;
    }

    @Override
    public Fine saveFine(Fine fine) {
        return fineRepository.save(fine);
    }

    @Override
    public Fine getFineById(Integer fineId) {
        return fineRepository.findById(fineId).orElse(null);
    }

    @Override
    public List<Fine> getAllFines() {
        return fineRepository.findAll();
    }
    
    @Override
    public List<Fine> getFinesByUserId(Integer userId) {
        return fineRepository.findByUserId(userId);
    }

    @Override
    public Fine getFineByIssueId(Integer issueId) {
        return fineRepository.findByIssueId(issueId);
    }

    @Override
    public void deleteFine(Integer fineId) {
        fineRepository.deleteById(fineId);
    }
}