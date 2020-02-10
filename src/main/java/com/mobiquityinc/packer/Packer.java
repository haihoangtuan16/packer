package com.mobiquityinc.packer;

import com.mobiquityinc.domain.Inventory;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.service.PackageService;
import com.mobiquityinc.service.impl.PackageServiceImpl;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.EMPTY;


public class Packer {

    private Packer() {
    }

    /**
     * Static method to load Package and Items from an input file
     * then find the solution for each package
     * @param filePath file path to the input file
     * @return String of solution for each package. each solution is list of items separate by comma. each solution is separate by new line
     * @throws APIException if input file is invalid
     */
    public static String pack(String filePath) throws APIException {

        PackageService packageService = new PackageServiceImpl();
        Inventory inventory = packageService.loadItemFromFile(filePath);

        if (Objects.isNull(inventory) || inventory.getPackages().isEmpty()) {
            return EMPTY;
        }

        packageService.findSolution(inventory);

        return inventory.getSolutionAsString();
    }
}
