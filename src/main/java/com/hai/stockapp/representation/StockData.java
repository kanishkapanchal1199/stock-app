package com.hai.stockapp.representation;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockData {

    private String date; // date
    private String symbol; // stock name

    private double open; // open price
    private double close; // close price
    private double low; // low price
    private double high; // high price
    private double volume; // volume
}
