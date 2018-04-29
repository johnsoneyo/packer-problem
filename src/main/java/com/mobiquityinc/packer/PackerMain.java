/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiquityinc.packer;

import com.mobiquityinc.packer.exception.APIException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author johnson3yo
 */
public class PackerMain {

    public static void main(String[] args) {
        String filePath = "/home/johnson3yo/packer.txt";
        try {
           List<String> items = Packer.pack(filePath);
           for(String item : items)
                System.out.println(item);
        } catch (APIException ex) {
           Logger.getLogger(PackerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
