import java.util.Comparator;
import java.util.List;

public class JavaMarkt {
    List<Product> koszyk;

    public void addToCart(Product prod) {
        koszyk.add(prod);
    }

    public void removeFromCart(Product prod) {
        koszyk.remove(prod);
    }

    public void sorting(String whatSort, String operator) {
        if (whatSort.equals("price")) {
            switch (operator) {
                case ">" -> koszyk.sort(Comparator.comparing(p -> p.price));
                case "<" -> koszyk.sort(Comparator.comparing((Product p) -> p.price).reversed());
                default -> throw new IllegalArgumentException("Nieznany rodzaj sortowania");

            }
        } else if (whatSort.equals("name")) {
            switch (operator) {
                case ">" -> koszyk.sort(Comparator.comparing(p -> p.name));
                case "<" -> koszyk.sort(Comparator.comparing((Product p) -> p.name).reversed());

                default -> throw new IllegalArgumentException("Nieznany rodzaj sortowania");

            }
        } else {
            throw new IllegalArgumentException("Można sortować tylko po cenia i nazwie");
        }
    }

    public List<Product> searching(String operator, Integer amount ) {
        return switch (operator) {
            case ">" -> {
                sorting("price", operator);
                koszyk.subList(0, Math.min(amount, koszyk.size()));
            }
            case "<" -> {
                sorting("price", operator);
                koszyk.subList(0, Math.min(amount, koszyk.size()));
            }
            default -> throw new IllegalArgumentException("Nieznany argument");

        };
    }
//    public static void main(String args[]) {
//        Product item = new Product("kod", "produkt", 10.99, 0.00);
//        System.out.println(item.price);
//    }
}
