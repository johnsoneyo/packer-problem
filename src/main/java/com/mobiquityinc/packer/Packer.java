/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiquityinc.packer;

import com.mobiquityinc.packer.exception.APIException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author johnson3yo
 */
public class Packer {

    public static List<String> pack(String filePath) throws APIException {

        List<String> lines = Collections.emptyList();
        List<String> result = new ArrayList();
        try {
            lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
            if(lines.size()==0){
                throw new APIException("file constraint failure : file is invalid ");
            }
                   
            //process each file line item 
            for (String line : lines) {
                String weightLimit = line.split(":")[0].trim();
                if (weightLimit == null) {
                    throw new APIException("file sample "
                            + "usage : 81 : (1,53.38,€45) "
                            + "(2,88.62,€98) (3,78.48,€3) "
                            + "(4,72.30,€76) (5,30.18,€9) "
                            + "(6,46.34,€48)");
                }
                if (Double.parseDouble(weightLimit) > 100) {
                    throw new APIException("weight constraint violated : maximum weight shouldnt be more than 100");
                }
                String remainingIndex = line.split(":")[1];
                String[] unparsedItems = remainingIndex.trim().split("\\s");

                // The remainingIndex string items are been parsed to a java item object 
                // in this block, an APIException is thrown if a currency isnt specified in the cost 
                // field
                ArrayList<Item> parsedItems = new ArrayList();
                for (String unparsedItem : unparsedItems) {

                    String removedBrackets = unparsedItem.replaceAll("\\(", "").replaceAll("\\)", "").trim();

                    String[] commaSeparatedItems = removedBrackets.split(",");
                    char currency = commaSeparatedItems[2].charAt(0);
                    if(Character.isDigit(currency)){
                        throw new APIException("currency constraint failed : cost amount must  be a currency ");
                    }
                    parsedItems.add(new Item(Integer.parseInt(commaSeparatedItems[0]),
                            Double.valueOf(commaSeparatedItems[1]),
                            Integer.parseInt(commaSeparatedItems[2].substring(1))));
                }

                // all generated sub arrays for an array is generated into this List<List<Item>> using
                // the recursive subarray generator 
                ArrayList<ArrayList<Item>> generatedSubsets = generateAllSubsets(parsedItems);
                ArrayList<Item> cases = null;             
                double maximumValue = 1;
              
                //
                for (int i = 0; i < generatedSubsets.size(); i++) {
                    ArrayList<Item> tempSet = generatedSubsets.get(i);

                    double totalWeight = tempSet.stream().mapToDouble(Item::getWeight).sum();
                    int totalValue = tempSet.stream().mapToInt(Item::getCost).sum();

                    if (totalWeight <= Double.parseDouble(weightLimit) && totalValue >= maximumValue) {
                        maximumValue = totalValue;
                         cases = tempSet;
                    }
                }
             
                if (cases == null) {
                    System.out.println("-");
                    result.add("-");
                } else {
                   // imploring java 8's stream api to collect indexes found and join the result into a comma separated string 
                   String collect = cases.stream().map(found -> String.valueOf(found.index)).collect(Collectors.joining(","));
                   result.add(collect);
                }
            }
        } catch (Exception e) {
            
            // do something
          if(e instanceof IOException){
              throw new APIException("file not be found in the specified file path");
          }
          if(e instanceof APIException){
              throw new APIException(((APIException) e).getMessage());
          }
        }
        return result;
    }

    private static ArrayList<Item> cloneSet(ArrayList<Item> input) {
        ArrayList<Item> clone = new ArrayList();

        for (int i = 0; i < input.size(); i++) {
            clone.add(input.get(i));
        }

        return clone;
    }

    //recursive function generate subsets 
    public static void generateAllSubsets(ArrayList<ArrayList<Item>> allSubsets, ArrayList<Item> set, int currentIndex) {
        //base case
        // gets satisfied when index equals size of the items set
        if (currentIndex == set.size()) {
            return;
        }

        int allSubSetsSize = allSubsets.size();
        ArrayList<Item> newSet;

        for (int i = 0; i < allSubSetsSize; i++) {
            newSet = cloneSet(allSubsets.get(i));
            newSet.add(set.get(currentIndex));
            allSubsets.add(newSet);
        }

        generateAllSubsets(allSubsets, set, currentIndex + 1);
    }

    public static ArrayList<ArrayList<Item>> generateAllSubsets(ArrayList<Item> set) {
        ArrayList<ArrayList<Item>> allSubsets = new ArrayList();

        allSubsets.add(new ArrayList<Item>());

        generateAllSubsets(allSubsets, set, 0);

        return allSubsets;
    }

}

 class Item {

    int index;
    double weight;
    int cost;

    Item() {

    }

    public Item(int index, double weight, int cost) {
        this.index = index;
        this.weight = weight;
        this.cost = cost;
    }

    public int getIndex() {
        return index;
    }

    public double getWeight() {
        return weight;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Item{" + "index=" + index + ", weight=" + weight + ", cost=" + cost + '}';
    }

  

    

}
