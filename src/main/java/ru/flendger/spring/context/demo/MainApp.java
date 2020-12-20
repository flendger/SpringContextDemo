package ru.flendger.spring.context.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

/**
 *         cart - new cart
 *         add id - add new product + print cart
 *         remove id - remove product + print cart
 *         exit - close app
 */
public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ProductRepository rep = context.getBean("rep", ProductRepository.class);
        System.out.println(rep.getProducts());

        Scanner sc = new Scanner(System.in);
        String[] commands;
        Cart cart = null;
        label:
        while (true){
            System.out.print(">>> ");
            String cm = sc.nextLine();
            commands = cm.split(" ");
            if (commands.length == 0) continue;

            switch (commands[0]) {
                case "exit":
                    break label;
                case "cart":
                    cart = context.getBean("cart", Cart.class);
                    break;
                case "add":
                    if (commands.length < 2 || cart == null) continue;
                    try {
                        int id = Integer.parseInt(commands[1]);
                        cart.add(id, 1);
                        System.out.println(cart);
                        System.out.printf("quantity: %d sum: %.2f\n", cart.getQuantity(), cart.getSum());
                    } catch (NumberFormatException e) {
                        System.out.println("Illegal Id");
                    }
                    break;
                case "remove":
                    if (commands.length < 2 || cart == null) continue;
                    try {
                        int id = Integer.parseInt(commands[1]);
                        cart.remove(id, 1);
                        System.out.println(cart);
                        System.out.printf("quantity: %d sum: %.2f\n", cart.getQuantity(), cart.getSum());
                    } catch (NumberFormatException e) {
                        System.out.println("Illegal Id");
                    }
                    break;
            }
        }
    }
}
