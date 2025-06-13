package main;

import java.util.Arrays;
import java.util.Comparator;

public class Koszyk {
    private Product[] products;
    private int size;

    public Koszyk(int initialCapacity) {
        products = new Product[initialCapacity];
        size = 0;
    }

    public void addToCart(Product product) {
        if (size >= products.length) {
            Product[] newProducts = new Product[products.length + 1];
            System.arraycopy(products, 0, newProducts, 0, products.length);
            products = newProducts;
        }
        products[size++] = product;
    }

    public void removeFromCart(Product product) {
        for (int i = 0; i < size; i++) {
            if (products[i] == product) {
                System.arraycopy(products, i+1, products, i, size - i - 1);
                products[size-1] = null;
                size--;
                break;
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Product[] getProducts() {
        // Create a new array with the same products (not copies)
        Product[] result = new Product[size];
        for (int i = 0; i < size; i++) {
            result[i] = products[i];
        }
        return result;
    }

    public void sortProducts() {
        // Sort using the natural ordering (Comparable)
        Product[] sortedProducts = getProducts();
        Arrays.sort(sortedProducts);
        System.arraycopy(sortedProducts, 0, products, 0, size);
    }

    public void sortProducts(Comparator<Product> comparator) {
        // Sort using the provided comparator
        Product[] sortedProducts = getProducts();
        Arrays.sort(sortedProducts, comparator);
        System.arraycopy(sortedProducts, 0, products, 0, size);
    }

    public Product findCheapestProduct() {
        if (size == 0) {
            return null;
        }

        Product cheapest = products[0];
        for (int i = 1; i < size; i++) {
            if (products[i].getPrice() < cheapest.getPrice()) {
                cheapest = products[i];
            }
        }
        return cheapest;
    }

    public Product findMostExpensiveProduct() {
        if (size == 0) {
            return null;
        }

        Product mostExpensive = products[0];
        for (int i = 1; i < size; i++) {
            if (products[i].getPrice() > mostExpensive.getPrice()) {
                mostExpensive = products[i];
            }
        }
        return mostExpensive;
    }

    public Product[] findNCheapestProducts(int n) {
        if (size == 0 || n <= 0) {
            return new Product[0];
        }

        // Create a copy of products array
        Product[] sortedProducts = new Product[size];
        for (int i = 0; i < size; i++) {
            sortedProducts[i] = products[i];
        }

        // Sort by price (ascending)
        Arrays.sort(sortedProducts, Comparator.comparingDouble(Product::getPrice));

        // Return n cheapest products (or all if n > size)
        int resultSize = Math.min(n, size);
        Product[] result = new Product[resultSize];
        System.arraycopy(sortedProducts, 0, result, 0, resultSize);

        return result;
    }

    public Product[] findNMostExpensiveProducts(int n) {
        if (size == 0 || n <= 0) {
            return new Product[0];
        }

        // Create a copy of products array
        Product[] sortedProducts = new Product[size];
        for (int i = 0; i < size; i++) {
            sortedProducts[i] = products[i];
        }

        // Sort by price (descending)
        Arrays.sort(sortedProducts, Comparator.comparingDouble(Product::getPrice).reversed());

        // Return n most expensive products (or all if n > size)
        int resultSize = Math.min(n, size);
        Product[] result = new Product[resultSize];
        System.arraycopy(sortedProducts, 0, result, 0, resultSize);

        return result;
    }

    public double calculateTotalPrice() {
        double total = 0;
        for (int i = 0; i < size; i++) {
            total += products[i].getDiscountPrice();
        }
        return total;
    }

    public void resetAllDiscounts() {
        for (int i = 0; i < size; i++) {
            products[i].resetDiscount();
        }
    }
}
