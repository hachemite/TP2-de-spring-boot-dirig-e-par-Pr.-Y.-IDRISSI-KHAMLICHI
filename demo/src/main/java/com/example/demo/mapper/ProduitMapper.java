package com.example.demo.mapper;


import com.example.demo.dto.ProduitDTO;
import com.example.demo.model.Produit;
import com.example.demo.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", uses = {DetailProduitMapper.class})
public interface ProduitMapper {

    @Mappings({
            @Mapping(source = "categorie.nom", target = "categorieNom"),
            @Mapping(source = "fournisseur.nom", target = "fournisseurNom"),

            // MapStruct est intelligent. Il voit "detailProduit" des deux côtés
            // et utilisera DetailProduitMapper automatiquement.
            @Mapping(source = "detailProduit", target = "detailProduit"),

            @Mapping(source = "tags", target = "tagLibelles")
    })
    ProduitDTO produitToProduitDTO(Produit produit);

    List<ProduitDTO> produitsToProduitDTOs(List<Produit> produits);

    @Mappings({
            @Mapping(source = "categorieNom", target = "categorie.nom"),
            @Mapping(source = "fournisseurNom", target = "fournisseur.nom"),

            // Mapping inverse:
            @Mapping(source = "detailProduit", target = "detailProduit"),

            @Mapping(target = "tags", ignore = true) // Toujours géré par le service
    })
    Produit produitDTOToProduit(ProduitDTO produitDTO);

    default Set<String> mapTagsToLibelles(Set<Tag> tags) {
        if (tags == null) {
            return java.util.Collections.emptySet();
        }
        return tags.stream()
                .map(Tag::getLibelle)
                .collect(Collectors.toSet());
    }
}