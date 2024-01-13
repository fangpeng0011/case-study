package com.project.thread.product;

public class Food {

    public static int foodSum = 10;

    public static final Object lock = new Object();

    public int getFoodSum() {
        return foodSum;
    }

    public void setFoodSum(int foodSum) {
        this.foodSum = foodSum;
    }
}
