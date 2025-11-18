package com.example.demo.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Data
public class ProduitDTO {
    private Long id;
    private String nom;
    private String description;
    private BigDecimal prix;
    private LocalDate dateExpiration;

    // Relations gérées
    private String categorieNom;
    private String fournisseurNom;
    private Set<String> tagLibelles = new HashSet<>();


    private DetailProduitDTO detailProduit = new DetailProduitDTO(); // Initialiser pour éviter les NullPointerException dans la vue
}
