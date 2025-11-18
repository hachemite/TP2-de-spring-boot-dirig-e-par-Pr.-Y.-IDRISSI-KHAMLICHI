package com.example.demo.controller;

import com.example.demo.dto.ProduitDTO;
import com.example.demo.repository.CategorieRepository;
import com.example.demo.repository.FournisseurRepository;
import com.example.demo.repository.TagRepository;
import com.example.demo.service.ProduitService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/produits")
public class ProduitController {

    private final ProduitService produitService;
    private final TagRepository tagRepository;
    private final FournisseurRepository fournisseurRepository; 
    private final CategorieRepository categorieRepository; 

    @Autowired
    public ProduitController(ProduitService produitService,
                             TagRepository tagRepository,
                             FournisseurRepository fournisseurRepository, 
                             CategorieRepository categorieRepository) { 
        this.produitService = produitService;
        this.tagRepository = tagRepository;
        this.fournisseurRepository = fournisseurRepository; 
        this.categorieRepository = categorieRepository; 
    }

    /**
     * Méthode Helper "best practice" pour peupler le modèle avec
     * TOUTES les données nécessaires au formulaire (dropdowns, etc.)
     */
    private void populateFormModel(Model model) {
        model.addAttribute("allTags", tagRepository.findAll());
        model.addAttribute("allFournisseurs", fournisseurRepository.findAll()); 
        model.addAttribute("allCategories", categorieRepository.findAll()); 
    }

    @GetMapping("/new")
    public String showNewProduitForm(Model model) {
        model.addAttribute("produit", new ProduitDTO());
        populateFormModel(model); 
        return "produit-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditProduitForm(@PathVariable Long id, Model model) {
        model.addAttribute("produit", produitService.findProduitById(id));
        populateFormModel(model); 
        return "produit-form";
    }

    @PostMapping
    public String saveProduit(@Valid @ModelAttribute("produit") ProduitDTO produitDTO,
                              BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            populateFormModel(model); 
            return "produit-form";
        }

        produitService.saveProduit(produitDTO);
        return "redirect:/produits";
    }

    @GetMapping
    public String listProduits(Model model) {
        model.addAttribute("produits", produitService.findAllProduits());
        return "produits";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return "redirect:/produits";
    }
}
