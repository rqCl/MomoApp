package com.example.navigationsandtabs;

public class AddReward {
    private String reward;
    private int cost;
    private int img;

    public AddReward (String reward, int cost, int img) {
        this.reward = reward;
        this.cost = cost;
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public int getCost() {
        return cost;
    }

    public String getReward() {
        return reward;
    }
}