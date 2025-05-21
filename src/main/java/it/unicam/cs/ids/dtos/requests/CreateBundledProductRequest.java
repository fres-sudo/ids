package it.unicam.cs.ids.dtos.requests;

import it.unicam.cs.ids.dtos.DTO;

public class CreateBundledProductRequest extends DTO {
    private long productId;
    private int quantityInBundle;
    private double unitPriceInBundle;

    public CreateBundledProductRequest(long productId, long bundleId, int quantityInBundle, double unitPriceInBundle) {
        this.productId = productId;
        this.quantityInBundle = quantityInBundle;
        this.unitPriceInBundle = unitPriceInBundle;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantityInBundle() {
        return quantityInBundle;
    }

    public void setQuantityInBundle(int quantityInBundle) {
        this.quantityInBundle = quantityInBundle;
    }

    public double getUnitPriceInBundle() {
        return unitPriceInBundle;
    }

    public void setUnitPriceInBundle(double unitPriceInBundle) {
        this.unitPriceInBundle = unitPriceInBundle;
    }
}
