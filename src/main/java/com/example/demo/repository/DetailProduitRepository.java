package com.example.demo.repository;

import com.example.demo.model.DetailProduit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailProduitRepository extends JpaRepository<DetailProduit, Long> {
}
