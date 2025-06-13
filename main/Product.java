package main;

import java.util.Comparator;

public class Product implements Comparable<Product> {
    private String code;
    private String name;
    private double price;
    private double discountPrice;

    public Product(String code, String name, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.discountPrice = price;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void applyDiscount(double discountPercent) {
        this.discountPrice = price * (1 - discountPercent / 100.0);
    }

    public void resetDiscount() {
        this.discountPrice = price;
    }

    @Override
    public int compareTo(Product other) {
        // Default sorting: descending by price, then alphabetically by name
        int priceComparison = Double.compare(other.price, this.price); // Descending
        if (priceComparison != 0) {
            return priceComparison;
        }
        return this.name.compareTo(other.name); // Alphabetically
    }

    // Comparators for different sorting strategies
    public static class PriceComparator implements Comparator<Product> {
        @Override
        public int compare(Product p1, Product p2) {
            return Double.compare(p2.getPrice(), p1.getPrice()); // Descending
        }
    }

    public static class NameComparator implements Comparator<Product> {
        @Override
        public int compare(Product p1, Product p2) {
            return p1.getName().compareTo(p2.getName());
        }
    }

    public static class PriceThenNameComparator implements Comparator<Product> {
        @Override
        public int compare(Product p1, Product p2) {
            int priceComparison = Double.compare(p2.getPrice(), p1.getPrice()); // Descending
            if (priceComparison != 0) {
                return priceComparison;
            }
            return p1.getName().compareTo(p2.getName()); // Alphabetically
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", discountPrice=" + discountPrice +
                '}';
    }
}
