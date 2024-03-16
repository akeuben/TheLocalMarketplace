package com.thelocalmarketplace.software.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.software.payment.CashPayment;
import com.thelocalmarketplace.software.payment.Transaction;

public class TransactionTest {
	private Transaction transaction;
	private Product productOne;
	private Product productTwo;
	private Numeral num;
	private Barcode bc;
    
	@Before
	public void setup() {
		this.transaction = new Transaction();
		this.num = Numeral.eight;
		this.bc= new Barcode(new Numeral[] {num});
		this.productOne = new BarcodedProduct(bc, "test1", 100, 1);
		this.productTwo = new BarcodedProduct(bc, "test2", 200, 2);
		
	}
	
	@Test
	public void testNullItemProduct() {
		assertThrows(NullPointerException.class, () -> transaction.addItem((BarcodedProduct)null));
	}
	
	@Test
	public void testNullPayment() {
		assertThrows(NullPointerException.class, () -> transaction.addPayment(null));
	}
	
	@Test
	public void testPositiveWeight() {
		int comparisonResult = transaction.getExpectedMass().compareTo(Mass.ZERO);
		Assert.assertTrue(comparisonResult == 0 || comparisonResult == 1);
	}
	
	@Test
	public void testPositiveCost() {
		Assert.assertTrue(transaction.getTotalCost().compareTo(BigDecimal.ZERO) >= 0);
	}
	
	@Test
	public void testAddOneItemWeight() {
		transaction.addItem(productOne);
		Mass productOneMass = new Mass(productOne.getExpectedWeight());
		Assert.assertTrue(productOneMass.compareTo(transaction.getExpectedMass()) == 0);
	}
	
	@Test
	public void testAddMultipleItemsWeight() {
		transaction.addItem(productOne);
		Mass productOneMass = new Mass(productOne.getExpectedWeight());
		transaction.addItem(productTwo);
		Mass productTwoMass = new Mass(productTwo.getExpectedWeight());
		Mass combinedProductMass = productOneMass.sum(productTwoMass);
		
		Assert.assertTrue(combinedProductMass.compareTo(transaction.getExpectedMass()) == 0);
	}
	
	@Test
	public void testAddOneItemCost() {
		transaction.addItem(productOne);
		Assert.assertEquals(transaction.getTotalCost().compareTo(BigDecimal.valueOf(1.00)), 0);
	}
	
	@Test
	public void testAddMultipleItemsCost() {
		transaction.addItem(productOne);
		transaction.addItem(productTwo);
		Assert.assertEquals(transaction.getTotalCost().compareTo(BigDecimal.valueOf(3.00)), 0);
	}
	
	@Test
	public void testValidPayment() {
	    CashPayment valid = new CashPayment(BigDecimal.TEN);
	    BigDecimal tCost = transaction.getTotalCost();
	    transaction.addPayment(valid);
	    BigDecimal remainder = new BigDecimal(10);
	    BigDecimal newCost = transaction.getTotalCost();
	    newCost = newCost.add(remainder); // Use add() method to add BigDecimal values
	    assertEquals(tCost, newCost); // Use assertEquals for comparison
	}

	
	@Test
    public void testAddItem() {
        transaction.addItem(productOne);
        assertEquals(1, transaction.getProducts().length);
    }
}
