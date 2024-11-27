package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {

    private final UserService userService;



    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    private User getCurrentUser(Authentication authentication) {
        String email = authentication.getName(); // Assuming email is the username
        return userService.findByEmail(email);
    }

    @GetMapping("/student/profile")
    @PreAuthorize("hasRole('STUDENT')")
    public String viewProfileStudent(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); 

        User user = userService.findByEmail(email);

        // Get currently logged in user id()
        System.out.println(user.getId());

        model.addAttribute("user", user);

        return "studentProfile";
    }

    @GetMapping("/student/profile/edit")
    @PreAuthorize("hasRole('STUDENT')")
    public String showEditStudentProfileForm(Model model, Authentication authentication) {
        User currentUser = getCurrentUser(authentication);

        if (currentUser != null) {
            model.addAttribute("user", currentUser);
        } else {
            model.addAttribute("error", "User not found!");
        }

        return "editStudentProfile"; // A Thymeleaf template for the edit profile page
    }

    @PutMapping("/student/profile/edit")
    public ResponseEntity<User> updateStudentUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/student/profile/edit")
    @PreAuthorize("hasRole('STUDENT')")
    public String updateStudentProfile(@ModelAttribute("user") User updatedUser, Authentication authentication, Model model) {
        getCurrentUser(updatedUser, authentication, model);

        return "studentProfile"; // Redirect to the profile page
    }

    @GetMapping("/teacher/profile")
    @PreAuthorize("hasRole('TEACHER')")
    public String viewProfileTeacher(Model model) {

        getAuthentication(model);

        return "teacherProfile";
    }

    private void getAuthentication(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userService.findByEmail(email);

        if (user != null) {

            model.addAttribute("user", user);
        } else {
            model.addAttribute("error", "User not found!");
        }
    }

    @GetMapping("/teacher/profile/edit")
    @PreAuthorize("hasRole('TEACHER')")
    public String showEditTeacherProfileForm(Model model, Authentication authentication) {
        User currentUser = getCurrentUser(authentication);

        if (currentUser != null) {
            model.addAttribute("user", currentUser);
        } else {
            model.addAttribute("error", "User not found!");
        }

        return "editTeacherProfile"; // A Thymeleaf template for the edit profile page
    }

    @PutMapping("/teacher/profile/edit")
    public ResponseEntity<User> updateTeacherUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/teacher/profile/edit")
    @PreAuthorize("hasRole('TEACHER')")
    public String updateTeacherProfile(@ModelAttribute("user") User updatedUser, Authentication authentication, Model model) {
        getCurrentUser(updatedUser, authentication, model);

        return "teacherProfile"; // Redirect to the same page or a success page
    }

    @GetMapping("/admin/profile")
    @PreAuthorize("hasRole('ADMIN')")
    public String viewProfileAdmin(Model model) {

        getAuthentication(model);

        return "adminProfile";
    }

    @GetMapping("/admin/profile/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String showEditAdminProfileForm(Model model, Authentication authentication) {
        User currentUser = getCurrentUser(authentication);

        if (currentUser != null) {
            model.addAttribute("user", currentUser);
        } else {
            model.addAttribute("error", "User not found!");
        }

        return "editAdminProfile"; // A Thymeleaf template for the edit profile page
    }

    @PutMapping("/admin/profile/edit")
    public ResponseEntity<User> updateAdminUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/admin/profile/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateAdminProfile(@ModelAttribute("user") User updatedUser, Authentication authentication, Model model) {
        getCurrentUser(updatedUser, authentication, model);

        return "adminProfile"; // Redirect to the profile page
    }

    @GetMapping("/finance/profile")
    @PreAuthorize("hasRole('FINANCE')")
    public String viewProfileFinance(Model model) {

        getAuthentication(model);

        return "financeProfile";
    }

    @GetMapping("/finance/profile/edit")
    @PreAuthorize("hasRole('FINANCE')")
    public String showEditFinanceProfileForm(Model model, Authentication authentication) {
        User currentUser = getCurrentUser(authentication);

        if (currentUser != null) {
            model.addAttribute("user", currentUser);
        } else {
            model.addAttribute("error", "User not found!");
        }

        return "editFinanceProfile"; // A Thymeleaf template for the edit profile page
    }

    @PutMapping("/finance/profile/edit")
    public ResponseEntity<User> updateFinanceUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/finance/profile/edit")
    @PreAuthorize("hasRole('FINANCE')")
    public String updateFinanceProfile(@ModelAttribute("user") User updatedUser, Authentication authentication, Model model) {
        getCurrentUser(updatedUser, authentication, model);

        return "financeProfile"; // Redirect to the profile page
    }

    private void getCurrentUser(@ModelAttribute("user") User updatedUser, Authentication authentication, Model model) {
        User currentUser = getCurrentUser(authentication);

        if (currentUser != null) {
            // Update editable fields only
            currentUser.setName(updatedUser.getName());
            currentUser.setPhone(updatedUser.getPhone());
            currentUser.setBio(updatedUser.getBio());
            // Add any additional fields you want to allow editing

            userService.updateUser(currentUser.getId(), currentUser);

            model.addAttribute("user", currentUser);
            model.addAttribute("success", "Profile updated successfully!");
        } else {
            model.addAttribute("error", "User not found!");
        }
    }
}
