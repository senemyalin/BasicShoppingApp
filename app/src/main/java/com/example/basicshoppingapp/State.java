package com.example.basicshoppingapp;

import java.util.ArrayList;
import java.util.List;

public class State<T> {
    public State(T item) {
        this.item = item;
    }

    private T item;
    private List<Runnable> runnables = new ArrayList<>();

    public void addListener(Runnable runnable) {
        runnables.add(runnable);
    }

    public void setItem(T username) {
        this.item = username;
        for (Runnable runnable : runnables)
            runnable.run();
    }

    public T getItem() {
        return this.item;
    }
}