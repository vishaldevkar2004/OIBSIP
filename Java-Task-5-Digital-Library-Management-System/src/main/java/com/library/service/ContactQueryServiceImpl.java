package com.library.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.library.entity.ContactQuery;
import com.library.repository.ContactQueryRepository;

@Service
public class ContactQueryServiceImpl implements ContactQueryService {

    private final ContactQueryRepository contactQueryRepository;

    public ContactQueryServiceImpl(ContactQueryRepository contactQueryRepository) {
        this.contactQueryRepository = contactQueryRepository;
    }

    @Override
    public ContactQuery saveQuery(ContactQuery query) {
        return contactQueryRepository.save(query);
    }

    @Override
    public ContactQuery getQueryById(Integer queryId) {
        return contactQueryRepository.findById(queryId).orElse(null);
    }

    @Override
    public List<ContactQuery> getAllQueries() {
        return contactQueryRepository.findAll();
    }

    @Override
    public void deleteQuery(Integer queryId) {
        contactQueryRepository.deleteById(queryId);
    }
}