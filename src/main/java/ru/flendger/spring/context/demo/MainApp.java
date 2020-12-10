package ru.flendger.spring.context.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ProductRepository rep = context.getBean("rep", ProductRepository.class);
        System.out.println(rep.getProducts());

        Cart cart = context.getBean("cart", Cart.class);
        cart.add(0, 1);
        cart.add(0, 1);
        cart.add(1, 1);
        cart.add(2, 1);
        cart.add(2, 1);
        cart.add(2, 1);
        System.out.println(cart);

        cart.remove(1, 1);
        System.out.println(cart);
        System.out.println(cart.getQuantity());
        System.out.println(cart.getSum());

        //TODO: add cart management via console
    }
}
