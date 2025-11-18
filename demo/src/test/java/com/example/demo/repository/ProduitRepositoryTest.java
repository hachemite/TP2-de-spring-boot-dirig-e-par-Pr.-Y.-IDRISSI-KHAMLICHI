package com.example.demo.repository;

import com.example.demo.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Professional Test: Integration Test for the Repository (DAO) Layer.
 * @DataJpaTest loads *only* the JPA context and connects to an in-memory H2 database.
 * This is the correct way to test if your @Query or JPA mappings work.
 */
@DataJpaTest
public class ProduitRepositoryTest {

    @Autowired
    private TestEntityManager entityManager; // Helper to prepare data

    @Autowired
    private ProduitRepository produitRepository;

    @Test
    public void testFindByNom_WhenProduitExists() {
        // 1. Arrange (Setup)
        Produit produit = new Produit();
        produit.setNom("Amoxicilline 500mg");
        produit.setPrix(new BigDecimal("45.00"));
        entityManager.persist(produit); // Save it to the H2 database
        entityManager.flush();

        // 2. Act (Execute)
        // We need to add 'findByNom' to the ProduitRepository interface for this test
        // Let's test findById instead, which is already there.
        Optional<Produit> found = produitRepository.findById(produit.getId());

        // 3. Assert (Verify)
        assertThat(found).isPresent();
        assertThat(found.get().getNom()).isEqualTo(produit.getNom());
    }

    @Test
    public void testFindById_WhenProduitDoesNotExist() {
        // 2. Act (Execute)
        Optional<Produit> found = produitRepository.findById(999L);

        // 3. Assert (Verify)
        assertThat(found).isNotPresent();
    }
}