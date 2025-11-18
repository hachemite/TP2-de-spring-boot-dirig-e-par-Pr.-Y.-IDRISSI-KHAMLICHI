package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

/**
 * NOUVELLE ENTITÉ: DetailProduit
 * Basée sur votre TP1.
 * C'est une relation OneToOne avec Produit.
 */
@Entity
@Data
@NoArgsConstructor
public class DetailProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fabricant;
    private String paysOrigine;
    private String composition;
    private String dosage;
    private String indications;

    // Note: Pour une relation OneToOne unidirectionnelle, nous n'avons
    // pas besoin de référencer le 'Produit' ici. L'entité 'Produit'
    // sera propriétaire de la relation avec @JoinColumn("detail_id").
}