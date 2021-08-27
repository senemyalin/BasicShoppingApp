package com.example.basicshoppingapp.Class;

public class ProductCount {
    Product product;
    int count;

    public ProductCount(Product product, int count) {
        this.product = product;
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void addCount(int c){
        this.count = count+c;
    }

    public void removeCount(int c){
        this.count = count - c;
    }

}
