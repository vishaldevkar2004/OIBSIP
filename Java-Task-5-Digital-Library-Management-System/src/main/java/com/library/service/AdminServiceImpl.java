package com.library.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.library.entity.Admin;
import com.library.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository,
                            PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Admin saveAdmin(Admin admin) {

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        return adminRepository.save(admin);
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        return adminRepository.findById(adminId).orElse(null);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public void deleteAdmin(Integer adminId) {
        adminRepository.deleteById(adminId);
    }
}