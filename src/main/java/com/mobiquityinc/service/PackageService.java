package com.mobiquityinc.service;

import com.mobiquityinc.domain.Inventory;
import com.mobiquityinc.exception.APIException;

public interface PackageService {
    public Inventory loadItemFromFile(String filePath) throws APIException;
    public void findSolution(Inventory inventory);
}
