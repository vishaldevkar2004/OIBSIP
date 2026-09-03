package com.library.service;

import java.util.List;

import com.library.entity.BookIssue;

public interface BookIssueService 
{
	BookIssue saveBookIssue(BookIssue bookIssue);
	
	BookIssue getBookIssueById(Integer issueId);
	
	List<BookIssue> getAllBookIssue();
	
	void deleteBookIssue(Integer issueId);
	
	List<BookIssue> getBookIssuesByUser(Integer userId);
}
