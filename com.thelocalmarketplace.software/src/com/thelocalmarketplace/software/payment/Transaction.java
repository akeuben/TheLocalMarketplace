	package com.thelocalmarketplace.software.payment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.jjjwelectronics.Mass;
import com.thelocalmarketplace.hardware.Product;

public class Transaction {

    /**
     * Items contained in an instance of transaction TODO Create constructor
     */
    private final ArrayList<Product> products = new ArrayList<>();
    
    private Mass expectedMass = Mass.ZERO;
    
    private BigDecimal totalCost = BigDecimal.ZERO;

    private final HashMap<UUID, Payment> payments = new HashMap<>();
    


    /**
     * Adds a product into the current transaction
     * Adds weight to total expected weight
     * Adds cost of item to total cost
     * @param product item being added to transaction/products
     */
    public void addItem(Product product) {
        if (product != null) {
            products.add(product);
            totalCost = totalCost.add(BigDecimal.valueOf(product.getPrice()).divide(BigDecimal.valueOf(100)));
            expectedMass = expectedMass.sum(new Mass(BigInteger.valueOf((int) (product.getExpectedWeight() * Mass.MICROGRAMS_PER_GRAM))));
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
    public void addPayment(Payment payment) {
    	UUID transactionId = UUID.randomUUID(); // Generate a unique ID for this transaction/payment
    	payments.put(transactionId, payment); // Add payment to HashMap
    	totalCost = totalCost.subtract(payment.getAmountPaid());
    }
    
    
    /**
     * Prints item descriptions and costs that have been added to transaction 
     */
    //public static void printReceipt() {
    //	for (int i = 0; i < products.size(); i++ ) {
    //		Product printProduct = products.get(i);
    //		System.out.println(printProduct.getDescription()+"\t" + printProduct.getPrice());
    //	}
    //	System.out.println("Total cost: " + totalCost);
    //}

    
    /**
     * Getter method for expected weight
     * @return expectedWeight
     */
    public Mass getExpectedMass() {
		return expectedMass;
    }
    
    /**
     * Getter method for total cost
     * @return totalCost
     */
    public BigDecimal getTotalCost() {
    	return totalCost;
    }


	public Product[] getProducts() {
		Product[] products = new Product[0];
		products = this.products.toArray(products);
		return products;
	}


	public Payment[] getPayments() {
		Payment[] payments = new Payment[0];
		payments = this.payments.values().toArray(payments);
		return payments;
	}
}