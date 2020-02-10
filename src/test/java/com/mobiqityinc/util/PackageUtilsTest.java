package com.mobiqityinc.util;

import com.mobiquityinc.domain.Item;
import com.mobiquityinc.domain.Package;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.util.PackageUtils;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PackageUtilsTest {

    private static double packageMaxWeight = 100;
    private static double maxWeight = 100;
    private static double maxCost = 100;

    @Test
    public void shouldConvertTokenToItem() {

        String token = "(1,53.38,€45)";
        try {
            Item item = PackageUtils.convertTokenToItem(token, maxWeight, maxCost);
            assertNotNull(item);
            assertEquals("53.38", String.valueOf(item.getWeight()));
            assertEquals("45.0", String.valueOf(item.getCost()));
            assertEquals("1", String.valueOf(item.getIndex()));
        } catch (APIException ex) {
            Assert.fail();
        }
    }

    @Test(expected = APIException.class)
    public void shouldThrowExceptionIfItemWeightInvalid() throws APIException {
        String token = "(1,101,€45)";
        PackageUtils.convertTokenToItem(token, maxWeight, maxCost);
    }

    @Test(expected = APIException.class)
    public void shouldThrowExceptionIfItemCostExceeded() throws APIException {
        String token = "(1,53.38,€101)";
        PackageUtils.convertTokenToItem(token, maxWeight, maxCost);
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionIfInvalid() throws Exception {
        String token = "(1 53.38 45)";
        PackageUtils.convertTokenToItem(token, maxWeight, maxCost);
    }

    @Test
    public void shouldConvertLineToPackage() {
        String line = "81 : (1,53.38,€45)";
        try {
            Package pack = PackageUtils.convertLineToPackage(line, packageMaxWeight, maxWeight, maxCost);

            assertEquals("81.0", String.valueOf(pack.getWeightLimit()));
            assertNotNull(pack.getItems());
            assertEquals(1, pack.getItems().size());
            assertEquals("53.38", String.valueOf(pack.getItems().get(0).getWeight()));
            assertEquals("45.0", String.valueOf(pack.getItems().get(0).getCost()));
            assertEquals("1", String.valueOf(pack.getItems().get(0).getIndex()));
        } catch (APIException ex) {
            Assert.fail();
        }
    }

    @Test(expected = APIException.class)
    public void shouldThrowExceptionIfMissingItem() throws APIException {
        String line = "81 :";
        PackageUtils.convertLineToPackage(line, packageMaxWeight, maxWeight, maxCost);
    }

    @Test(expected = APIException.class)
    public void shouldThrowExceptionIfMissingWeightLimit() throws APIException {
        String line = ": (1,53.38,€45)";
        PackageUtils.convertLineToPackage(line, packageMaxWeight, maxWeight, maxCost);
    }

    @Test(expected = APIException.class)
    public void shouldThrowExceptionIfExceedWeightLimit() throws APIException {
        String line = "101 : (1,53.38,€45)";
        PackageUtils.convertLineToPackage(line, packageMaxWeight, maxWeight, maxCost);
    }
}
