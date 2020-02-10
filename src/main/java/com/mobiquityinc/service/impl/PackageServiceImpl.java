package com.mobiquityinc.service.impl;

import com.mobiquityinc.Constants;
import com.mobiquityinc.domain.Inventory;
import com.mobiquityinc.domain.Item;
import com.mobiquityinc.domain.Package;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.service.PackageService;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mobiquityinc.util.PackageUtils.convertLineToPackage;
import static java.util.Objects.isNull;

public class PackageServiceImpl implements PackageService {

    /**
     * Load data from input file and convert  it to an Inventory which contain all the package
     * @param filePath : path to the input file
     * @return an Inventory
     * @throws APIException
     */
    @Override
    public Inventory loadItemFromFile(String filePath) throws APIException {

        Inventory inventory = new Inventory();
        if (StringUtils.isEmpty(filePath)) {
            throw new APIException("Input file path is empty!");
        }
        Path path = Paths.get(filePath);

        List<String> errorMessage = new ArrayList<>();
        try (Stream<String> lines = Files.lines(path)) {
            List<Package> packages = lines.map(line -> {
                try {
                    return convertLineToPackage(line, Constants.PACKAGE_MAX_WEIGHT, Constants.ITEM_MAX_WEIGHT, Constants.ITEM_MAX_COST);
                } catch (APIException e) {
                    errorMessage.add(e.getMessage());
                    return null;
                }
            }).collect(Collectors.toList());

            if(!errorMessage.isEmpty()){
                throw new APIException(String.join(",", errorMessage));
            }

            if (isNull(packages) || packages.isEmpty()) {
                throw new APIException("Invalid Input File: " + filePath);
            }

            inventory.setPackages(packages);
        } catch (Exception e) {
            throw new APIException("Unable to load input file: " + filePath);
        }
        return inventory;
    }

    /**
     * Finding solution for each package in Inventory
     * @param inventory
     */
    @Override
    public void findSolution(Inventory inventory) {

        for (Package pack : inventory.getPackages()) {
            List<Package> possibleCombinations = populateAllPossibleCombination(pack);
            Package solution = getPackageWithHighestCost(possibleCombinations);
            inventory.getSolutions().add(solution);
        }
    }

    /**
     * Populate all the possible combination of item in given package
     * possible combination must have total weight < weight limit
     * total items must be less then max_allow_items
     * @param pack
     * @return list of possible combination
     */
    private List<Package> populateAllPossibleCombination(Package pack) {
        final double weightLimit = pack.getWeightLimit();
        List<Package> possibleCombinations = new ArrayList<>();
        for (Item item : pack.getItems()) {
            int existingCombination = possibleCombinations.size();
            for (int i = 0; i < existingCombination; i++) {
                //if check if current combine is able to add more item. (new total weight < weight limit and number of items < 15)
                if(possibleCombinations.get(i).isAddble(item) && possibleCombinations.get(i).getItems().size() < Constants.MAX_ITEM_ALLOW) {
                    List<Item> combineItems = new ArrayList<Item>(possibleCombinations.get(i).getItems());
                    combineItems.add(item);
                    possibleCombinations.add(new Package(weightLimit, combineItems, true));
                }
            }

            if(item.getWeight() <= weightLimit) {
                List<Item> newCombine = new ArrayList<>();
                newCombine.add(item);
                possibleCombinations.add(new Package(weightLimit, newCombine, true));
            }
        }

        return possibleCombinations;
    }

    /**
     * Find the bes solution form list of all possible combination
     * if empty then return a empty solution
     * best solution is must have highest cost if multiple solution have same cost then choose the least weight
     * @param packages all possible combination
     * @return best solution
     */
    private Package getPackageWithHighestCost(List<Package> packages) {
        if (Objects.isNull(packages) || packages.isEmpty()) {
            return new Package(0, new ArrayList<>());
        }
        Package bestPackage = packages.get(0);
        for (int i = 1; i < packages.size(); i++) {
            if (bestPackage.getCost() < packages.get(i).getCost()) {
                bestPackage = packages.get(i);
            } else if (bestPackage.getCost() == packages.get(i).getCost()) {
                //if same cost need to chose the one with less weight;
                if (bestPackage.getWeight() > packages.get(i).getWeight()) {
                    bestPackage = packages.get(i);
                }
            }
        }
        return bestPackage;
    }
}
