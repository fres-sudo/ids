package it.unicam.cs.ids.entities;

import lombok.Data;


@Data
public class BundledProduct {
    private long productId;
    private long bundleId;
    private int quantityInBundle;
    private double unitPriceInBundle;
}
