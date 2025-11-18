package com.example.demo.dto;

import lombok.Data;


@Data
public class DetailProduitDTO {
    private String fabricant;
    private String paysOrigine;
    private String composition;
    private String dosage;
    private String indications;
}