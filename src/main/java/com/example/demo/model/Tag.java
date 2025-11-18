package com.example.demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * NOUVELLE ENTITÉ: Tag (étiquette)
 * Basée sur votre TP1.
 */
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "produits") // Important pour éviter les boucles dans ManyToMany
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String libelle; // "libelle" comme dans votre TP

    // 'mappedBy = "tags"' indique que l'entité Produit est propriétaire de la relation
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<Produit> produits = new HashSet<>();

    public Tag(String libelle) {
        this.libelle = libelle;
    }
}