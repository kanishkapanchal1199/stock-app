package com.hai.stockapp.repository;

import com.hai.stockapp.entity.NseStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NseStockRepository extends JpaRepository<NseStock, String> {
}