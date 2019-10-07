package com.iifym.classes;

public class Macros {

    private int fat;
    private int protein;
    private int carbohydrate;
    private int calories;

    public Macros(int fat, int protein, int carbohydrate, int calories) {
        this.fat = fat;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.calories = calories;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
