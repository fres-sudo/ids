package it.unicam.cs.ids.dtos.filters;

import it.unicam.cs.ids.enums.ProductCategory;
import it.unicam.cs.ids.enums.SortDirection;

import java.util.List;

public class BundleFilter extends FilterParam {

    private List<ProductCategory> categories;
    private double minPrice;
    private double maxPrice;
    private boolean available;
    private boolean availableForShipping;
    private double minDiscountPercentage;

    public BundleFilter(String page, String pageSize, SortDirection sortDirection, String sortBy, String searchBy, List<String> tags, List<ProductCategory> categories, double minPrice, double maxPrice, boolean available, boolean availableForShipping, double minDiscountPercentage) {
        super(page, pageSize, sortDirection, sortBy, searchBy, tags);
        this.categories = categories;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.available = available;
        this.availableForShipping = availableForShipping;
        this.minDiscountPercentage = minDiscountPercentage;
    }

    public List<ProductCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ProductCategory> categories) {
        this.categories = categories;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isAvailableForShipping() {
        return availableForShipping;
    }

    public void setAvailableForShipping(boolean availableForShipping) {
        this.availableForShipping = availableForShipping;
    }

    public double getMinDiscountPercentage() {
        return minDiscountPercentage;
    }

    public void setMinDiscountPercentage(double minDiscountPercentage) {
        this.minDiscountPercentage = minDiscountPercentage;
    }
}
