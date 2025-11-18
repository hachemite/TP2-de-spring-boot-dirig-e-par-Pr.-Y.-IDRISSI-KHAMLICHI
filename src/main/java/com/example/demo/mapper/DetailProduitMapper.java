package com.example.demo.mapper;


import com.example.demo.dto.DetailProduitDTO;
import com.example.demo.model.DetailProduit;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface DetailProduitMapper {

    DetailProduitDTO detailProduitToDetailProduitDTO(DetailProduit detailProduit);

    DetailProduit detailProduitDTOToDetailProduit(DetailProduitDTO detailProduitDTO);
}
