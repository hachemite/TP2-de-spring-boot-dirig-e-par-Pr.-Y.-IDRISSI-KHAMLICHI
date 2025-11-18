package com.example.demo.service;

import com.example.demo.dto.ProduitDTO;
import com.example.demo.repository.ProduitRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ProduitMapper;
import com.example.demo.model.Produit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Professional Test: Unit Test for the Service Layer.
 * We use @ExtendWith(MockitoExtension.class) to enable Mockito.
 * We @Mock the dependencies (Repository, Mapper) to test the service
 * logic in complete isolation.
 */
@ExtendWith(MockitoExtension.class)
public class ProduitServiceTest {

    @Mock // Creates a mock implementation for this dependency
    private ProduitRepository produitRepository;

    @Mock // Creates a mock implementation for this dependency
    private ProduitMapper produitMapper;

    @InjectMocks // Creates an instance of ProduitService and injects the mocks into it
    private ProduitService produitService;

    @Test
    public void testFindProduitById_WhenProduitExists() {
        // 1. Arrange (Setup)
        Produit produit = new Produit(); // A mock entity
        produit.setId(1L);
        produit.setNom("Doliprane");

        ProduitDTO produitDTO = new ProduitDTO(); // A mock DTO
        produitDTO.setId(1L);
        produitDTO.setNom("Doliprane");

        // When the repository's findById is called with 1L, return our mock entity
        when(produitRepository.findById(1L)).thenReturn(Optional.of(produit));

        // When the mapper is called with our entity, return our DTO
        when(produitMapper.produitToProduitDTO(produit)).thenReturn(produitDTO);

        // 2. Act (Execute)
        ProduitDTO result = produitService.findProduitById(1L);

        // 3. Assert (Verify)
        assertNotNull(result);
        assertEquals("Doliprane", result.getNom());
        verify(produitRepository).findById(1L); // Verify findById was called once
        verify(produitMapper).produitToProduitDTO(produit); // Verify mapper was called
    }

    @Test
    public void testFindProduitById_WhenProduitNotFound() {
        // 1. Arrange (Setup)
        // When findById is called, return an empty Optional
        when(produitRepository.findById(1L)).thenReturn(Optional.empty());

        // 2. Act & 3. Assert (Execute & Verify)
        // Assert that the correct enterprise exception is thrown
        assertThrows(ResourceNotFoundException.class, () -> {
            produitService.findProduitById(1L);
        });

        // Verify the mapper was *never* called
        verify(produitMapper, never()).produitToProduitDTO(any());
    }
}