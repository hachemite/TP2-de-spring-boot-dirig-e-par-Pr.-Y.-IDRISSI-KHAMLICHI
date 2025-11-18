package com.example.demo.controller;

import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test "Enterprise" pour un contrôleur sécurisé.
 * Nous testons les règles d'autorisation.
 */
@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Dépendances mockées pour AdminController
    @MockBean
    private UserRepository userRepository;

    // Dépendances requises par SecurityConfig
    @MockBean
    private com.example.demo.security.CustomUserDetailsService customUserDetailsService;

    @Test
    @WithMockUser(roles = "ADMIN") // Test 1: Simuler un ADMIN
    public void testShowDashboardAsAdmin_ShouldSucceed() throws Exception {
        // Arrange
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        // Act & Assert
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk()) // Doit réussir (200)
                .andExpect(view().name("admin/dashboard"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    @WithMockUser(roles = "PHARMACIEN") // Test 2: Simuler un PHARMACIEN
    public void testShowDashboardAsPharmacien_ShouldBeForbidden() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden()); // Doit échouer (403 Forbidden)
    }

    @Test
    @WithMockUser(roles = "CLIENT") // Test 3: Simuler un CLIENT
    public void testShowDashboardAsClient_ShouldBeForbidden() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden()); // Doit échouer (403 Forbidden)
    }

    @Test
    public void testShowDashboardAsAnonymous_ShouldRedirectToLogin() throws Exception {
        // Test 4: Simuler un utilisateur non authentifié
        // Act & Assert
        mockMvc.perform(get("/admin"))
                .andExpect(status().is3xxRedirection()) // Doit être redirigé (302)
                .andExpect(redirectedUrlPattern("**/login")); // Vers la page de connexion
    }
}
