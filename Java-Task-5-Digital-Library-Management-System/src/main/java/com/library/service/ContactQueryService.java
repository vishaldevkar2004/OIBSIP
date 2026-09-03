package com.library.service;

import java.util.List;

import com.library.entity.ContactQuery;

public interface ContactQueryService {

    ContactQuery saveQuery(ContactQuery query);

    ContactQuery getQueryById(Integer queryId);

    List<ContactQuery> getAllQueries();

    void deleteQuery(Integer queryId);
}