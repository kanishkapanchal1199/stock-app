package com.hai.stockapp.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class NseStock {

    @Id
    private String nseSymbol;
    private String companyName;


}


