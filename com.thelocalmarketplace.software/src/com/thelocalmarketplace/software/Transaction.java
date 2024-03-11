package com.thelocalmarketplace.software;

import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import com.thelocalmarketplace.software.IPayment;
import java.util.HashMap;
import java.util.UUID;
import com.jjjwelectronics.scanner.Barcode;
import static com.thelocalmarketplace.hardware.external.ProductDatabases.BARCODED_PRODUCT_DATABASE;

public class Transaction {

    /**
     * Items contained in an instance of transaction TODO Create constructor
     */
    private static final ArrayList<Product> products = new ArrayList<>();
    
    private static double expectedWeight=0.0;
    
    private static BigDecimal totalCost = BigDecimal.ZERO;

    private static final HashMap<UUID, IPayment> payments = new HashMap<>();

    /**
     * Adds product to transaction array list
     * Adds weight to total expected weight
     * Adds cost of item to total cost
     * Prints item description
     * @param barcode
     */
    public static void addItem(Barcode barcode) { //TODO Remove method when necessary
        if (barcode != null) {
            BarcodedProduct product = BARCODED_PRODUCT_DATABASE.get(barcode);
            products.add(product);
            totalCost = totalCost.add(BigDecimal.valueOf(product.getPrice()));
            expectedWeight = expectedWeight+product.getExpectedWeight();
            System.out.println(product.getDescription());
        }
        else {
            throw new NullPointerException("barcode");
        }

    }

    /**
     * Adds a product into the current transaction
     * @param product item being added to transaction/products
     */
    public static void addItem(Product product) {
        if (product != null) {
            products.add(product);
            totalCost = totalCost.add(BigDecimal.valueOf(product.getPrice()));
            expectedWeight += product.getExpectedWeight();
            System.out.println(product.getDescription());
        }
        else {
            throw new NullPointerException("product");
        }
    }



    /**
     *
     * Adds a payment to the transaction by storing in HashMap payments
     * @param paymentMethod, type of payment method used, must be initialized so amountPaid is already defined
     */
    public static void addPayment(IPayment paymentMethod) {
    	UUID transactionId = UUID.randomUUID(); // Generate a unique ID for this transaction/payment
    	payments.put(transactionId, paymentMethod); // Add payment to HashMap
    	totalCost = totalCost.subtract(paymentMethod.getAmountPaid());
    }

    

    
    /**
     * Getter method for expected weight
     * @return expectedWeight
     */
    public static double getExpectedWeight() {
		return expectedWeight;
    }
    
    public static BigDecimal getTotalCost() {
    	return totalCost;
    }
    
		
}