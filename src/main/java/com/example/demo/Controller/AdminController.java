package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.Classes;
import com.example.demo.service.ClassService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ClassService classService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/adminDashboard")
    public String adminDashboard() {
        return "adminDashboard";
    }

    @GetMapping("/userList")
    public String listAllUsers(Model model) {
        // Fetch all users from the service layer
        List<UserDto> users = userService.findAllUsers();

        // Add the users to the model for the view
        model.addAttribute("users", users);
        
        return "userList"; 
    }

    @GetMapping("/adminclasses")
    public String getClasses(Model model) {
        List<Classes> classes = classService.getAllClasses(); 
        model.addAttribute("classes", classes); 
        return "adminClasses";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("email") String email) {
        try {
            userService.deleteUserByEmail(email); 
        } catch (RuntimeException e) {
           
            return "userList";
        }
        return "deleteSuccess";  
    }

    @GetMapping("/deleteSuccess")
    public String deleteSuccessPage() {
        return "deleteSuccess";
    }
}
