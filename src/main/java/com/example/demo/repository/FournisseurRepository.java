package com.example.demo.repository;

import com.example.demo.model.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
    // Logique métier : trouver un fournisseur par son nom
    Optional<Fournisseur> findByNom(String nom);
}
