package com.library.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.library.entity.User;
import com.library.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/members")
    public String adminMembers(Model model) {

        List<User> users = userService.getAllUsers();

        model.addAttribute("users", users);

        return "admin-members";
    }
}