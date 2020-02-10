package com.mobiquityinc.util;

import com.mobiquityinc.domain.Item;
import com.mobiquityinc.domain.Package;
import com.mobiquityinc.exception.APIException;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class PackageUtils {

    /**
     * convert a string line data from input file to a package
     * @param line             the value of line
     * @param packageMaxWeight the constraint Max Weight of Package
     * @param itemMaxWeight    the constraint Max Weight of Item
     * @param itemMaxCost      the constraint Max Cost of item
     * @return Package if line if valid
     * @throws APIException if line is invalid
     */
    public static Package convertLineToPackage(String line, double packageMaxWeight, double itemMaxWeight, double itemMaxCost) throws APIException {
        String[] tokens = line.split(":");

        if (tokens.length < 2 || isEmpty(tokens[0]) || isEmpty(tokens[1])) {
            throw new APIException("Invalid data in Input File");
        }

        try {
            double weightLimit = Double.parseDouble(tokens[0]);

            if (weightLimit > packageMaxWeight) {
                throw new APIException("Invalid data in Input File");
            }

            String[] itemsInLine = tokens[1].split(" ");

            List<Item> items = new ArrayList<Item>();
            for (String itemStr : itemsInLine) {
                if (isNotEmpty(itemStr)) {

                    items.add(convertTokenToItem(itemStr, itemMaxWeight, itemMaxCost));

                }
            }
            return new Package(weightLimit, items);

        } catch (Exception e) {
            throw new APIException("Invalid data in Input File");
        }
    }

    /**
     * Convert a token value to Item
     *
     * @param token         the token value
     * @param itemMaxWeight the constraint Max Weight of Item
     * @param itemMaxCost   the constraint Max Cost of item
     * @return Item if token is valid
     * @throws APIException if token is invalid
     */
    public static Item convertTokenToItem(String token, double itemMaxWeight, double itemMaxCost) throws APIException {
        String[] datas = token.replace("(", "").replace("(", "").replace(")", "").split(",");

        int index = Integer.parseInt(datas[0]);
        double weight = Double.parseDouble(datas[1]);

        if (weight > itemMaxWeight) {
            throw new APIException("Invalid data in Input File");
        }

        String costStr = null;
        if (isNotEmpty(datas[2]) && datas[2].length() > 1) {
            //remove currency code
            costStr = datas[2].substring(1);
        }
        double cost = Double.parseDouble(costStr);

        if (cost > itemMaxCost) {
            throw new APIException("Invalid data in Input File");
        }
        return new Item(index, weight, cost);
    }
}
