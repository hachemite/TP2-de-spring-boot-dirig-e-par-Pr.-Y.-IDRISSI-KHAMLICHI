package com.example.demo.repository;

import com.example.demo.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * NOUVEAU REPOSITORY ("DAO") pour les Categories.
 */
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    // Logique métier : trouver une catégorie par son nom
    Optional<Categorie> findByNom(String nom);
}
