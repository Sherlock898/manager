package com.goldenelectric.manager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.goldenelectric.manager.models.User;
import com.goldenelectric.manager.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/") public String login(@ModelAttribute("user") User user){return "login.jsp";}

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, @RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session){
        boolean isAuthenticated = userService.authenticateUser(email, password);
        if (isAuthenticated) {
            User u = userService.findByEmail(email);
            session.setAttribute("userId", u.getId());
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Invalid Credentials. Please try again.");
            return "login.jsp";
        }
    }
    
    @GetMapping("/add-user")
    public String addUser(@ModelAttribute("user") User user, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/";

        User sessionUser = userService.findById(userId);
        if (sessionUser == null || sessionUser.getRole() != User.Role.ADMIN) return "redirect:/";
        return "addUser.jsp";
    }

    @PostMapping("/add-user")
    public String addUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/";

        User sessionUser = userService.findById(userId);
        if (sessionUser == null || sessionUser.getRole() != User.Role.ADMIN) return "redirect:/";

        User isUser = userService.findByEmail(user.getEmail());
        if (isUser != null) {
            result.rejectValue("email", "Unique", "Email already on use");
        }
        if(!user.getPassword().equals(user.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "Matches", "Passwords do not match");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("error", result.getAllErrors() + "\n" + user.getPassword());
            return "addUser.jsp";
        }
        User u = userService.registerUser(user);
        session.setAttribute("userId", u.getId());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
