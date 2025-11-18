package com.example.demo.controller;

import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    /**
     Affiche le tableau de bord principal de l'administration.
     * Mappé à GET /admin/
     */
    @GetMapping
    public String showAdminDashboard(Model model) {
        // Pour une application plus grande, on utiliserait un UserService et des UserDTOs,
        // mais pour un panel admin, accéder au repository est acceptable.
        model.addAttribute("users", userRepository.findAll());
        return "admin/dashboard"; // templates/admin/dashboard.html
    }


}
