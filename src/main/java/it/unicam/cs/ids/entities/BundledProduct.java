package it.unicam.cs.ids.entities;

import lombok.Data;
//////////////////////////////
//TODO: this class deve estendere BaseEntity?
//////////////////////////////
@Data
public class BundledProduct {
    private long productId;
    private long bundleId;
    private int quantityInBundle;
    private double unitPriceInBundle;
}
