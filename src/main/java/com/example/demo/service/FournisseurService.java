package com.example.demo.service;

import com.example.demo.model.Fournisseur;
import com.example.demo.repository.FournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * NOUVEAU SERVICE pour la logique métier des Fournisseurs.
 */
@Service
@Transactional
public class FournisseurService {

    private final FournisseurRepository fournisseurRepository;

    @Autowired
    public FournisseurService(FournisseurRepository fournisseurRepository) {
        this.fournisseurRepository = fournisseurRepository;
    }

    /**
     * Retourne la liste complète pour les dropdowns du formulaire.
     */
    @Transactional(readOnly = true)
    public List<Fournisseur> findAll() {
        return fournisseurRepository.findAll();
    }

    /**
     * Logique métier "Find-or-Create" (Trouver ou Créer).
     * C'est une "best practice" pour gérer les relations à partir des DTOs.
     * Si le fournisseur existe, on le retourne. Sinon, on le crée et le sauvegarde.
     */
    public Fournisseur findOrCreateByNom(String nom) {
        return fournisseurRepository.findByNom(nom)
                .orElseGet(() -> {
                    Fournisseur nouveauFournisseur = new Fournisseur(nom);
                    // Dans une vraie app, on pourrait laisser les autres champs (contact, adresse)
                    // à null pour que l'admin les complète plus tard.
                    return fournisseurRepository.save(nouveauFournisseur);
                });
    }
}