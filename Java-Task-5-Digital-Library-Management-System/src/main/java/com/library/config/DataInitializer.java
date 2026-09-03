package com.library.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.library.entity.Admin;
import com.library.repository.AdminRepository;
import com.library.service.AdminService;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final AdminService adminService;

    public DataInitializer(AdminRepository adminRepository,
                           AdminService adminService) {
        this.adminRepository = adminRepository;
        this.adminService = adminService;
    }

    @Override
    public void run(String... args) {

        if (adminRepository.findByEmail("admin@library.com").isEmpty()) {

            Admin admin = new Admin();

            admin.setAdminName("Library Admin");
            admin.setEmail("admin@library.com");
            admin.setPassword("Admin@123");

            adminService.saveAdmin(admin);

            System.out.println("====================================");
            System.out.println("✅ Default Admin Created Successfully");
            System.out.println("📧 Email: admin@library.com");
            System.out.println("🔑 Password: Admin@123");
            System.out.println("====================================");
        }
    }
}