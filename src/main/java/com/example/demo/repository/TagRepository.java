package com.example.demo.repository;

import com.example.demo.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    // Logique métier : trouver un tag par son nom
    Optional<Tag> findByLibelle(String libelle);

    // trouver tous les tags dont le libellé
    // est dans un set de strings.
    Set<Tag> findByLibelleIn(Set<String> libelles);
}