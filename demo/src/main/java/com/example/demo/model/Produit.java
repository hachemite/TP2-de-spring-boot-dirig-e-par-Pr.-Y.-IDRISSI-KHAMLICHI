package com.example.demo.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "tags") // Ajout pour ManyToMany
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;
    private String description;
    private BigDecimal prix;
    private String codeBarre;
    private LocalDate dateExpiration;

    // --- Relations ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseur;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "detail_id")
    private DetailProduit detailProduit;

    // --- NOUVELLE RELATION: ManyToMany avec Tag ---
    // C'est le côté "propriétaire" (owning side) de la relation.
    // Cascade PERSIST et MERGE: si nous sauvegardons un produit avec un NOUVEAU tag,
    // le tag sera aussi sauvegardé.
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "produit_tag", // Nom de la table de jointure (comme dans votre TP)
            joinColumns = @JoinColumn(name = "produit_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
}