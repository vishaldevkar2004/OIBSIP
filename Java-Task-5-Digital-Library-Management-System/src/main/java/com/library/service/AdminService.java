package com.library.service;

import java.util.List;

import com.library.entity.Admin;

public interface AdminService {

	Admin saveAdmin(Admin admin);
	
	Admin getAdminById(Integer adminId);
	
	List<Admin> getAllAdmins();
	
	void deleteAdmin(Integer adminId);
}
