package ru.flendger.spring.context.demo;

public interface Cart {

    void add(int id, int count);
    void remove(int id, int count);
    int getQuantity();
    float getSum();
}
