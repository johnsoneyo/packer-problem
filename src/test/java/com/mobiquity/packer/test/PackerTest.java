package com.mobiquity.packer.test;

import com.mobiquityinc.packer.Packer;
import com.mobiquityinc.packer.exception.APIException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author johnson3yo
 */
public class PackerTest {

    private Path path = null;

    private final String itemWithoutCurrencyFile = "/home/johnson3yo/itemWithoutCurrency.txt";
    private final String itemWithoutWidthThresholdDemactionColon = "/home/johnson3yo/itemWithoutWidthThresholdDemactionColon.txt";
    private final String itemWithValidLine = "/home/johnson3yo/itemWithValidLine";
    private final String filePath = "/home/johnson3yo/packer.txt";
    private final String itemFileEmpty = "/home/johnson3yo/itemFileEmpty.txt";

    @Before
    public void loadpackerFileFromDirectory() {
        path = Paths.get(filePath);
    }

    @Test(expected = APIException.class)
    public void itemWithoutCurrency() throws APIException {
        Packer.pack(itemWithoutCurrencyFile);
    }

    @Test(expected = APIException.class)
    public void itemWithoutWidthThresholdDemactionColon() throws APIException {
        Packer.pack(itemWithoutWidthThresholdDemactionColon);
    }

    @Test
    public void itemWithCurrencyandvalidinformation() throws APIException {
        List<String> result = Packer.pack(filePath);
        Assert.assertTrue(result.size() > 0);
        Assert.assertTrue(containsAComma(result));
    }

    @Test(expected = APIException.class)
    public void itemFileEmpty() throws APIException {
        Packer.pack(itemFileEmpty);
    }

    private boolean containsAComma(List<String> result) {
        for (String res : result) {
            return res.split(",") != null;
        }
        return false;
    }

}
