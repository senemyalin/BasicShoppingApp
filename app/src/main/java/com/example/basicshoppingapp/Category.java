package com.example.basicshoppingapp;

public enum Category {
    Vegan ("Vegan"),
    Fresh_Bakery ("Fresh Bakery"),
    Snacks ("Snacks") ,
    Beverages ("Beverages"),
    Fruits_Veg ("Fruits & Veg"),
    Fit_Form ("Fit & Form");

    public String category_string;

    Category(String category_string){
        this.category_string = category_string;
    }

    public boolean equalsName(String stringName) {
        return category_string.equals(stringName);
    }

    public String toString() {
        return this.category_string;
    }
}
