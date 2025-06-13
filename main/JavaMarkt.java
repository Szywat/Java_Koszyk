package main;

import main.promotion.Promotion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Main class for the JavaMarkt online store.
 * Handles the shopping cart and promotions.
 */
public class JavaMarkt {
    private Koszyk cart;
    private List<Promotion> availablePromotions;

    /**
     * Creates a new JavaMarkt instance with an empty cart and no promotions.
     * @param initialCartCapacity The initial capacity of the shopping cart.
     */
    public JavaMarkt(int initialCartCapacity) {
        cart = new Koszyk(initialCartCapacity);
        availablePromotions = new ArrayList<>();
    }

    /**
     * Gets the shopping cart.
     * @return The shopping cart.
     */
    public Koszyk getCart() {
        return cart;
    }

    /**
     * Adds a product to the cart.
     * @param product The product to add.
     */
    public void addToCart(Product product) {
        cart.addToCart(product);
    }

    /**
     * Removes a product from the cart.
     * @param product The product to remove.
     */
    public void removeFromCart(Product product) {
        cart.removeFromCart(product);
    }

    /**
     * Adds a promotion to the list of available promotions.
     * @param promotion The promotion to add.
     */
    public void addPromotion(Promotion promotion) {
        availablePromotions.add(promotion);
    }

    /**
     * Removes a promotion from the list of available promotions.
     * @param promotion The promotion to remove.
     */
    public void removePromotion(Promotion promotion) {
        availablePromotions.remove(promotion);
    }

    /**
     * Gets the list of available promotions.
     * @return The list of available promotions.
     */
    public List<Promotion> getAvailablePromotions() {
        return new ArrayList<>(availablePromotions);
    }

    /**
     * Applies all applicable promotions to the cart.
     * @return The total amount saved by applying promotions.
     */
    public double applyPromotions() {
        double totalSaved = 0;

        // Reset all discounts before applying promotions
        cart.resetAllDiscounts();

        // Apply each applicable promotion
        for (Promotion promotion : availablePromotions) {
            if (promotion.isApplicable(cart)) {
                totalSaved += promotion.apply(cart);
            }
        }

        return totalSaved;
    }

    /**
     * Finds the optimal way to apply promotions to maximize savings.
     * @return The total amount saved by applying promotions in the optimal order.
     */
    public double applyOptimalPromotions() {
        // Reset all discounts before applying promotions
        cart.resetAllDiscounts();

        // Get all applicable promotions
        List<Promotion> applicablePromotions = new ArrayList<>();
        for (Promotion promotion : availablePromotions) {
            if (promotion.isApplicable(cart)) {
                applicablePromotions.add(promotion);
            }
        }

        // If there are no applicable promotions, return 0
        if (applicablePromotions.isEmpty()) {
            return 0;
        }

        // Try all possible permutations of promotions to find the optimal order
        List<List<Promotion>> allPermutations = generatePermutations(applicablePromotions);

        double maxSavings = 0;
        List<Promotion> bestOrder = null;

        for (List<Promotion> permutation : allPermutations) {
            // Reset all discounts
            cart.resetAllDiscounts();

            // Apply promotions in this order
            double totalSaved = 0;
            for (Promotion promotion : permutation) {
                if (promotion.isApplicable(cart)) {
                    totalSaved += promotion.apply(cart);
                }
            }

            // Update the maximum savings if this order is better
            if (totalSaved > maxSavings) {
                maxSavings = totalSaved;
                bestOrder = permutation;
            }
        }

        // Apply the best order of promotions
        cart.resetAllDiscounts();
        double totalSaved = 0;
        for (Promotion promotion : bestOrder) {
            if (promotion.isApplicable(cart)) {
                totalSaved += promotion.apply(cart);
            }
        }

        return totalSaved;
    }

    private List<List<Promotion>> generatePermutations(List<Promotion> promotions) {
        List<List<Promotion>> result = new ArrayList<>();
        generatePermutationsHelper(promotions, new ArrayList<>(), result, new boolean[promotions.size()]);
        return result;
    }

    private void generatePermutationsHelper(List<Promotion> promotions, List<Promotion> current, List<List<Promotion>> result, boolean[] used) {
        if (current.size() == promotions.size()) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = 0; i < promotions.size(); i++) {
            if (used[i]) {
                continue;
            }

            used[i] = true;
            current.add(promotions.get(i));

            generatePermutationsHelper(promotions, current, result, used);

            used[i] = false;
            current.remove(current.size() - 1);
        }
    }

    /**
     * Sorts the products in the cart by price (descending) and then by name.
     */
    public void sortCart() {
        cart.sortProducts();
    }

    /**
     * Sorts the products in the cart using the provided comparator.
     * @param comparator The comparator to use for sorting.
     */
    public void sortCart(Comparator<Product> comparator) {
        cart.sortProducts(comparator);
    }

    /**
     * Calculates the total price of the cart after applying promotions.
     * @return The total price of the cart.
     */
    public double calculateTotalPrice() {
        return cart.calculateTotalPrice();
    }

    /**
     * Finds the cheapest product in the cart.
     * @return The cheapest product, or null if the cart is empty.
     */
    public Product findCheapestProduct() {
        return cart.findCheapestProduct();
    }

    /**
     * Finds the most expensive product in the cart.
     * @return The most expensive product, or null if the cart is empty.
     */
    public Product findMostExpensiveProduct() {
        return cart.findMostExpensiveProduct();
    }

    /**
     * Finds the n cheapest products in the cart.
     * @param n The number of products to find.
     * @return An array of the n cheapest products.
     */
    public Product[] findNCheapestProducts(int n) {
        return cart.findNCheapestProducts(n);
    }

    /**
     * Finds the n most expensive products in the cart.
     * @param n The number of products to find.
     * @return An array of the n most expensive products.
     */
    public Product[] findNMostExpensiveProducts(int n) {
        return cart.findNMostExpensiveProducts(n);
    }
}
