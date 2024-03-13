package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertThrows;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.software.Transaction;
import com.thelocalmarketplace.hardware.Product;

public class TransactionTest {
	
	@Before
	public void setup() {
		// setup code
	}
	
	//@Test
	//public void testNullItemBarcode() {
		//assertThrows(NullPointerException.class, () -> Transaction.addItem((Barcode)null));
	//}
	
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
	
}
