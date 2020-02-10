package com.mobiquityinc.domain;


import java.util.List;

public class Package {
    private double weight;
    private double cost;
    private double weightLimit;
    private List<Item> items;

    public Package(double weightLimit, List<Item> items) {
        this.weightLimit = weightLimit;
        this.items = items;
    }

    public Package(double weightLimit, List<Item> items, boolean isCalculate) {
        this.weightLimit = weightLimit;
        this.items = items;

        if (isCalculate) {
            for (Item item : this.items) {
                weight += item.getWeight();
                cost += item.getCost();
            }
        }
    }

    public boolean isAddble(Item item) {
        if (this.weight + item.getWeight() <= weightLimit) {
            return true;
        }
        return false;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
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

    public double getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(double weightLimit) {
        this.weightLimit = weightLimit;
    }

}
