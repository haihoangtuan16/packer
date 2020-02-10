package com.mobiqityinc.packer;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.Packer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PackerTest {

    @Test(expected = APIException.class)
    public void shouldThrowAPIExceptionIfFileNotFound() throws APIException {
        String filePath = "src/test/resources/nofile.txt";
        String solutions = Packer.pack(filePath);
    }

    @Test(expected = APIException.class)
    public void shouldThrowAPIExceptionIfEmptyFile() throws APIException {
        String filePath = "src/test/resources/input_empty.txt";
        String solutions = Packer.pack(filePath);
    }

    @Test(expected = APIException.class)
    public void shouldThrowAPIExceptionIfFileHaveEmptyRow() throws APIException {
        String filePath = "src/test/resources/input_empty_row.txt";
        String solutions = Packer.pack(filePath);
    }

    @Test(expected = APIException.class)
    public void shouldThrowAPIExceptionIfMissingWeightLimit() throws APIException {
        String filePath = "src/test/resources/input_invalid_format.txt";
        String solutions = Packer.pack(filePath);
    }

    @Test(expected = APIException.class)
    public void shouldThrowAPIExceptionIfMissingItems() throws APIException {
        String filePath = "src/test/resources/input_invalid_format2.txt";
        String solutions = Packer.pack(filePath);
    }

    @Test(expected = APIException.class)
    public void shouldThrowAPIExceptionIfInvalidWeightLimit() throws APIException {
        String filePath = "src/test/resources/input_invalid_weight_limit.txt";
        String solutions = Packer.pack(filePath);
    }

    @Test(expected = APIException.class)
    public void shouldThrowAPIExceptionIfInvalidItemWeight() throws APIException {
        String filePath = "src/test/resources/input_invalid_item_weight.txt";
        String solutions = Packer.pack(filePath);
    }

    @Test(expected = APIException.class)
    public void shouldThrowAPIExceptionIfInvalidItemCost() throws APIException {
        String filePath = "src/test/resources/input_invalid_item_cost.txt";
        String solutions = Packer.pack(filePath);
    }

    @Test(expected = APIException.class)
    public void shouldThrowAPIExceptionIfInvalidData() throws APIException {
        String filePath = "src/test/resources/input_invalid_data.txt";
        String solutions = Packer.pack(filePath);
    }

    @Test
    public void shouldFindSolution() throws APIException {
        String filePath = "src/test/resources/input1.txt";
        String result = "4\n" + "\n" + "2,7\n" +  "8,9\n";
        String solutions = Packer.pack(filePath);
        assertEquals(result, solutions);
    }

    @Test
    public void shouldReturnWeightLessPackageIfSameCost() throws APIException {
        String filePath = "src/test/resources/input2.txt";
        String result = "4\n" + "\n" + "2,8\n" +  "8,9\n";
        String solutions = Packer.pack(filePath);
        assertEquals(result, solutions);
    }

    @Test
    public void shouldReturnSolutionLessThanMaxItems() throws APIException {
        String filePath = "src/test/resources/input4.txt";
        String result = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15\n";
        String solutions = Packer.pack(filePath);
        assertEquals(result, solutions);
    }
}
