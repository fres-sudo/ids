package it.unicam.cs.ids;


import it.unicam.cs.ids.dtos.filters.ProductFilter;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        ProductFilter productFilter = new ProductFilter(
                "1",
                "10",
                null,
                "name",
                "searchTerm",
                null,
                null,
                0.0,
                100.0,
                true,
                true,
                12345L
        );
    }
}