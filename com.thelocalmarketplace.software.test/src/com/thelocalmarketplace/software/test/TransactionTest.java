package com.thelocalmarketplace.software.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.math.BigDecimal;

import org.hamcrest.number.BigDecimalCloseTo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.software.CashPayment;
import com.thelocalmarketplace.software.Transaction;
import com.thelocalmarketplace.hardware.Product;

public class TransactionTest {
	
	private Product mockedProduct;
    private IPayment mockedPayment;
    
	@Before
	public void setup() {
		// setup code
	}
	
	@Test
	public void testNullItemBarcode() {
		assertThrows(NullPointerException.class, () -> Transaction.addItem((Barcode)null));
	}
	
	@Test
	public void testNullItemProduct() {
		assertThrows(NullPointerException.class, () -> Transaction.addItem((Product)null));
	}
	
	@Test
	public void testNullPayment() {
		assertThrows(NullPointerException.class, () -> Transaction.addPayment(null));
	}
	
	@Test
	public void testPositiveWeight() {
		Assert.assertTrue(Transaction.getExpectedWeight() >= 0);
	}
	
	@Test
	public void testPositiveCost() {
		Assert.assertTrue(Transaction.getTotalCost().compareTo(BigDecimal.ZERO) >= 0);
	}
	
	@Test
	public void testNegativeCost() {
		Assert.assertTrue(Transaction.getTotalCost().compareTo(new BigDecimal(-5)) <0);
	}
	
	@Test
	public void testValidPayment() {
	    CashPayment valid = new CashPayment(BigDecimal.TEN);
	    BigDecimal tCost = Transaction.getTotalCost();
	    Transaction.addPayment(valid);
	    BigDecimal remainder = new BigDecimal(10);
	    BigDecimal newCost = Transaction.getTotalCost();
	    newCost = newCost.add(remainder); // Use add() method to add BigDecimal values
	    assertEquals(tCost, newCost); // Use assertEquals for comparison
	}

	
	@Test
    public void testAddItem() {
//		mockedProdcut;
        Transaction.addItem(mockedProduct);
        assertEquals(1, Transaction.product.size());
    }
}
