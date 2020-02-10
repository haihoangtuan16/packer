package com.mobiqityinc.service.impl;

import com.mobiquityinc.domain.Inventory;
import com.mobiquityinc.domain.Item;
import com.mobiquityinc.domain.Package;
import com.mobiquityinc.service.PackageService;
import com.mobiquityinc.service.impl.PackageServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PackageServiceImplTest {

    private PackageService packageService = new PackageServiceImpl();

    @Test
    public void shouldLoadFromFile(){
        String filePath = "src/test/resources/input3.txt";
        try {
            Inventory inventory = packageService.loadItemFromFile(filePath);
            assertNotNull(inventory);
            assertNotNull(inventory.getPackages());
            assertEquals(1, inventory.getPackages().size());
            assertEquals("81.0", String.valueOf(inventory.getPackages().get(0).getWeightLimit()));
            assertNotNull(inventory.getPackages().get(0).getItems());
            assertEquals(1, inventory.getPackages().get(0).getItems().size());
            assertEquals("53.38", String.valueOf(inventory.getPackages().get(0).getItems().get(0).getWeight()));
            assertEquals("45.0", String.valueOf(inventory.getPackages().get(0).getItems().get(0).getCost()));
            assertEquals("1", String.valueOf(inventory.getPackages().get(0).getItems().get(0).getIndex()));
        }catch (Exception ex){
            Assert.fail();
        }
    }

    @Test
    public void shouldFindSolution(){
        Inventory inventory = new Inventory();
        List<Item> items = new ArrayList<>();
        items.add(new Item(1,53.38,45));
        items.add(new Item(2,88.62,98));
        items.add(new Item(3,78.48,3));
        items.add(new Item(4,72.30,76));
        items.add(new Item(5,30.18,9));
        items.add(new Item(6,46.34,48));
        Package pack = new Package(81.0, items);
        inventory.getPackages().add(pack);

        packageService.findSolution(inventory);

        assertNotNull(inventory.getSolutions());
        assertEquals(1, inventory.getSolutions().size());
        assertEquals(1, inventory.getSolutions().get(0).getItems().size());
        assertEquals("72.3", String.valueOf(inventory.getSolutions().get(0).getItems().get(0).getWeight()));
        assertEquals("76.0", String.valueOf(inventory.getSolutions().get(0).getItems().get(0).getCost()));
        assertEquals("4", String.valueOf(inventory.getSolutions().get(0).getItems().get(0).getIndex()));
    }
}
