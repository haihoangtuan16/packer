package com.mobiquityinc.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class Inventory {

    private List<Package> packages;
    private List<Package> solutions;

    public Inventory() {
        packages = new ArrayList<>();
        solutions = new ArrayList<>();
    }

    public String getSolutionAsString() {

        if (solutions.isEmpty()) {
            return EMPTY;
        }

        StringBuilder sb = new StringBuilder("");
        for (Package solution : solutions) {
            if (Objects.isNull(solution.getItems()) || solution.getItems().isEmpty()) {
                sb.append(EMPTY);
            } else {
                for (Item item : solution.getItems()) {
                    sb.append(item.getIndex()).append(",");
                }
                if (sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public List<Package> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<Package> solutions) {
        this.solutions = solutions;
    }
}
