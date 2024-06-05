package com.hai.stockapp.controller;


import com.hai.stockapp.mining.StockMarket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockMarket stockMarket;


    @GetMapping
    public String getAllStockHistory()
    {
        return stockMarket.getAllHistory();
    }

}
