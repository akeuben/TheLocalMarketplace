package com.thelocalmarketplace.software;

import com.thelocalmarketplace.hardware.BarcodedProduct;
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
    private static final ArrayList<BarcodedProduct> products = new ArrayList<>();
    
    private static double expectedWeight=0.0;
    
    private static long totalCost = 0;

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
            totalCost = totalCost + product.getPrice();
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
            totalCost += product.getPrice();
            expectedWeight += product.getExpectedWeight();
            System.out.println(product.getDescription());
        }
        else {
            throw new NullPointerException("barcode");
        }
    }

    /**
     * Reset Transaction
     * A method to reset the transaction could be useful after successful payment
     */
    private static void resetTransaction() {
        products.clear();
        totalCost = 0;
        expectedWeight = 0.0;
    }


    /**
     *
     * Adds a payment to the transaction by storing in HashMap payments
     * @param paymentMethod, type of payment method used, must be initialized so amountPaid is already defined
     */
    public static void addPayment(IPayment paymentMethod) {
        UUID transactionId = UUID.randomUUID(); // Generate a unique ID for this transaction/payment
        if (paymentMethod.processPayment(totalCost)) {
            payments.put(transactionId, paymentMethod);
            System.out.println("Payment added successfully for transaction ID: " + transactionId);
            // Assuming the entire total cost is paid successfully
            totalCost = 0;
//            resetTransaction(); // Optionally, reset the transaction for the next custmer
        } else {
            System.out.println("Payment failed to process");
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