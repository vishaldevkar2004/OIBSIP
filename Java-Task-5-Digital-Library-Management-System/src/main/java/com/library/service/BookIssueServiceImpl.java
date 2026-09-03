package com.library.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.library.entity.BookIssue;
import com.library.repository.BookIssueRepository;

@Service
public class BookIssueServiceImpl implements BookIssueService {

	private final BookIssueRepository bookIssueRepository;
	
	public BookIssueServiceImpl(BookIssueRepository bookIssueRepository)
	{
		this.bookIssueRepository = bookIssueRepository;
	}
	
	@Override
	public BookIssue saveBookIssue(BookIssue bookIssue) {
		return bookIssueRepository.save(bookIssue);
	}

	@Override
	public BookIssue getBookIssueById(Integer issueId) {
		return bookIssueRepository.findById(issueId).orElse(null);
	}

	@Override
	public List<BookIssue> getAllBookIssue() {
		return bookIssueRepository.findAll();
	}

	@Override
	public void deleteBookIssue(Integer issueId) {
		bookIssueRepository.deleteById(issueId);
	}
	
	@Override
	public List<BookIssue> getBookIssuesByUser(Integer userId) {
	    return bookIssueRepository.findByUserId(userId);
	}

}
