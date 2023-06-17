package com.assignment_2.assignment_2.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.assignment_2.assignment_2.models.User;
import com.assignment_2.assignment_2.models.UserRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/users/hist")
    public String showHistogram(Model model) {
        System.out.println("open histogram page");
        List<User> users = userRepo.findAll();
        model.addAttribute("us", users);
        return "users/sthist";
    }

    @GetMapping("/users/students")
    public String showUsers(Model model) {
        System.out.println("getting users");
        List<User> users = userRepo.findAll();
        model.addAttribute("us", users);
        return "users/home";
    }

    @PostMapping("/add")
    public String addUser(@RequestParam Map<String, String> newuser, HttpServletResponse response) {
        System.out.println("adding user");
        try {
            String newFirst = newuser.get("first");
            String newLast = newuser.get("last");
            double newHeight = Double.parseDouble(newuser.get("height"));
            double newWeight = Double.parseDouble(newuser.get("weight"));
            double newGpa = Double.parseDouble(newuser.get("gpa"));
            userRepo.save(new User(newFirst, newLast, newHeight, newWeight, newGpa));
            response.setStatus(201);
            return "redirect:/users/students";
        } catch (NullPointerException e) {
            return "fill all the fields";
        }
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") int id, HttpServletResponse response) {
        System.out.println("deleting user");
        try {
            userRepo.deleteById(id);
            response.setStatus(202);
            return "redirect:/users/students";
        } catch (IllegalArgumentException e) {
            response.setStatus(404);
            return "user not found";
        }
    }

    @PostMapping("/users/edit")
    public String editUser(@ModelAttribute User user) {
        Optional<User> existingUser = userRepo.findById(user.getUid());
        if (existingUser.isPresent()) {
            User u = existingUser.get();
            u.setFirst(user.getFirst());
            u.setLast(user.getLast());
            u.setHeight(user.getHeight());
            u.setWeight(user.getWeight());
            u.setGpa(user.getGpa());
            userRepo.save(u);
            return "redirect:/users/students";
        } else {
            return "error/userNotFound";
        }
    }
}
