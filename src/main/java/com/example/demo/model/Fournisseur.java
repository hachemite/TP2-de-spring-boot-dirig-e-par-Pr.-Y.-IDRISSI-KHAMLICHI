package com.example.demo.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * NOUVELLE ENTITÉ: Fournisseur (Supplier)
 * Basée sur votre TP1.
 */
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "produits") // Évite les boucles
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;
    private String contact;
    private String adresse;

    @OneToMany(
            mappedBy = "fournisseur",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Produit> produits = new ArrayList<>();

    // Constructeur pratique pour notre service
    public Fournisseur(String nom) {
        this.nom = nom;
    }
}