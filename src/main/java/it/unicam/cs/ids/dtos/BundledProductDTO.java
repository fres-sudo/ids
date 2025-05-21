package it.unicam.cs.ids.dtos;

public class BundledProductDTO extends DTO {
    private int quantityInBundle;
    private ProductDTO product;
    private double unitPriceInBundle;

    public BundledProductDTO(int quantityInBundle, ProductDTO product, double unitPriceInBundle) {
        this.quantityInBundle = quantityInBundle;
        this.product = product;
        this.unitPriceInBundle = unitPriceInBundle;
    }

    public int getQuantityInBundle() {
        return quantityInBundle;
    }

    public void setQuantityInBundle(int quantityInBundle) {
        this.quantityInBundle = quantityInBundle;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public double getUnitPriceInBundle() {
        return unitPriceInBundle;
    }

    public void setUnitPriceInBundle(double unitPriceInBundle) {
        this.unitPriceInBundle = unitPriceInBundle;
    }
}
