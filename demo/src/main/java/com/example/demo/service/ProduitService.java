package com.example.demo.service;

import com.example.demo.dto.ProduitDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ProduitMapper;
import com.example.demo.model.Categorie;
import com.example.demo.model.Fournisseur;
import com.example.demo.model.Produit;
import com.example.demo.model.Tag;
import com.example.demo.repository.CategorieRepository;
import com.example.demo.repository.FournisseurRepository;
import com.example.demo.repository.ProduitRepository;
import com.example.demo.repository.TagRepository;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProduitService {

    private final ProduitRepository produitRepository;
    private final TagRepository tagRepository;
    private final FournisseurRepository fournisseurRepository;
    private final CategorieRepository categorieRepository;
    // Pas besoin de DetailProduitRepository ici, CascadeType.ALL s'en occupe.
    private final ProduitMapper produitMapper;

    @Autowired
    public ProduitService(ProduitRepository produitRepository,
                          TagRepository tagRepository,
                          FournisseurRepository fournisseurRepository,
                          CategorieRepository categorieRepository,
                          ProduitMapper produitMapper) {
        this.produitRepository = produitRepository;
        this.tagRepository = tagRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.categorieRepository = categorieRepository;
        this.produitMapper = produitMapper;
    }

    // ... (findAllProduits et findProduitById ne changent pas)
    @Transactional(readOnly = true)
    public List<ProduitDTO> findAllProduits() {
        return produitMapper.produitsToProduitDTOs(produitRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ProduitDTO findProduitById(Long id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec id: " + id));
        return produitMapper.produitToProduitDTO(produit);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PHARMACIEN')")
    public ProduitDTO saveProduit(ProduitDTO produitDTO) {

        // 1. Convertir le DTO en Entité.
        // MapStruct va aussi mapper le 'detailProduitDTO' imbriqué.
        Produit produit = produitMapper.produitDTOToProduit(produitDTO);

        // 2. Gérer la logique métier pour les relations
        // (Fournisseur, Categorie, Tags... comme avant)

        if (produitDTO.getFournisseurNom() != null && !produitDTO.getFournisseurNom().isBlank()) {
            Fournisseur f = fournisseurRepository.findByNom(produitDTO.getFournisseurNom())
                    .orElseGet(() -> fournisseurRepository.save(new Fournisseur(produitDTO.getFournisseurNom())));
            produit.setFournisseur(f);
        }

        if (produitDTO.getCategorieNom() != null && !produitDTO.getCategorieNom().isBlank()) {
            Categorie c = categorieRepository.findByNom(produitDTO.getCategorieNom())
                    .orElseGet(() -> categorieRepository.save(new Categorie(produitDTO.getCategorieNom())));
            produit.setCategorie(c);
        }

        if (produitDTO.getTagLibelles() != null && !produitDTO.getTagLibelles().isEmpty()) {
            Set<Tag> tagsExistants = tagRepository.findByLibelleIn(produitDTO.getTagLibelles());
            Set<String> libellesExistants = tagsExistants.stream()
                    .map(Tag::getLibelle)
                    .collect(Collectors.toSet());
            Set<Tag> nouveauxTags = produitDTO.getTagLibelles().stream()
                    .filter(libelle -> !libellesExistants.contains(libelle))
                    .map(Tag::new)
                    .collect(Collectors.toSet());
            tagsExistants.addAll(nouveauxTags);
            produit.setTags(tagsExistants);
        }

        // 3. Sauvegarder le produit.
        // GRÂCE À CascadeType.ALL, JPA va :
        //    a. Sauvegarder l'objet 'DetailProduit'
        //    b. Récupérer son nouvel ID
        //    c. Mettre cet ID dans la colonne 'detail_id' de Produit
        //    d. Sauvegarder l'objet 'Produit'
        // Tout cela en une seule transaction !
        Produit savedProduit = produitRepository.save(produit);

        return produitMapper.produitToProduitDTO(savedProduit);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PHARMACIEN')")
    public void deleteProduit(Long id) {
        if (!produitRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produit non trouvé avec id: " + id);
        }
        produitRepository.deleteById(id);
    }
}
