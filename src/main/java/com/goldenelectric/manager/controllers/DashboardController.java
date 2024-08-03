package com.goldenelectric.manager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.goldenelectric.manager.models.User;
import com.goldenelectric.manager.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {
    private final UserService userService;

    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/";
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        return "dashboard.jsp";
    }
}
