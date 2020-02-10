package com.mobiquityinc.domain;


public class Item {
    private double weight;
    private double cost;
    private int index;

    public Item(int index, double weight, double cost) {
        this.index = index;
        this.weight = weight;
        this.cost = cost;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
