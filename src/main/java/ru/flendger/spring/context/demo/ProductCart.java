package ru.flendger.spring.context.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component("cart")
@Scope("prototype")
public class ProductCart implements Cart{

    private final HashMap<Integer, CartItem> products;

    @Autowired
    private ProductRepository rep;

    public ProductCart() {
        products = new HashMap<>();
    }

    @Override
    public void add(int id, int count) {
        Product product = rep.getProduct(id);
        if (product == null) return;

        if (count < 0) {
            throw new IllegalArgumentException();
        }

        CartItem item;
        if (products.containsKey(product.getId())) {
            item = products.get(product.getId());
        } else {
            item = new CartItem(product);
            products.put(product.getId(), item);
        }
        item.add(count);
    }

    @Override
    public void remove(int id, int count) {
        Product product = rep.getProduct(id);
        if (product == null) return;

        if (!products.containsKey(product.getId())) return;

        if (count < 0) {
            throw new IllegalArgumentException();
        }

        CartItem item = products.get(product.getId());
        item.reduce(count);
        if (item.quantity == 0) {
            products.remove(product.getId());
        }
    }

    @Override
    public int getQuantity() {
        return products.values().stream()
                .mapToInt(agr -> agr.quantity)
                .sum();
    }

    @Override
    public float getSum() {
        return (float) products.values().stream()
                .mapToDouble(arg -> arg.sum)
                .sum();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Cart{\n");
        products.values()
                .forEach(cartItem -> sb.append(cartItem.toString()).append("\n"));
        sb.setLength(sb.length()-1);
        sb.append("}");
        return sb.toString();
    }

    private static class CartItem {

        private final Product product;
        private int quantity;
        private float sum;

        private CartItem(Product product) {
            this.product = product;
        }

        private void add(int count) {
            if (count < 0) {
                throw new IllegalArgumentException();
            }
            quantity += count;
            evaluateSum();
        }

        private void reduce(int count) {
            if (count < 0) {
                throw new IllegalArgumentException();
            }
            if (count >= quantity) {
                quantity = 0;
            } else {
                quantity -= count;
            }
            evaluateSum();
        }

        private void evaluateSum() {
            sum = product.getPrice() * quantity;
        }

        @Override
        public String toString() {
            return String.format("[id = %d title = %s quantity = %d price = %.2f sum = %.2f]",
                    product.getId(), product.getTitle(), quantity, product.getPrice(), sum);
        }
    }
}
