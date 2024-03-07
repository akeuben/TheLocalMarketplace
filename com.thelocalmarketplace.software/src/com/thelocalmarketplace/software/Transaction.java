package com.thelocalmarketplace.software;

import com.thelocalmarketplace.hardware.BarcodedProduct;
import java.util.ArrayList;
import com.jjjwelectronics.scanner.Barcode;
import static com.thelocalmarketplace.hardware.external.ProductDatabases.BARCODED_PRODUCT_DATABASE;

public class Transaction {

    /**
     * Items contained in an instance of transaction TODO Create constructor
     */
    private static final ArrayList<BarcodedProduct> products = new ArrayList<>();
    
    private static double expectedWeight=0.0;
    
    private static long totalCost = 0;

    /**
     * Adds product to transaction array list
     * Adds weight to total expected weight
     * Adds cost of item to total cost
     * Prints item description
     * @param barcode
     */
    public static void addItem(Barcode barcode) {
        if (barcode != null) {
            BarcodedProduct product = BARCODED_PRODUCT_DATABASE.get(barcode);
            products.add(product);
            totalCost = totalCost + product.getPrice();
            expectedWeight = expectedWeight+product.getExpectedWeight();
            System.out.println(product.getDescription());
        }
        else {
            throw new NullPointerException("barcode");
        }

    }
    
    /**
     * Getter method for expected weight
     * @return expectedWeight
     */
    public double getExpectedWeight() {
		return expectedWeight;
    }
    
    public long getTotalCost() {
    	return totalCost;
    }
    
		
}