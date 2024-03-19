package com.thelocalmarketplace.software.test;

/**
 * SENG 300 Project - Group 1:
 * 
 * Avery Keuben - 30170731
 * Moiz Siddiqui - 30150291
 * Ammaar Melethil - 30141956
 * Joey Fisher - 30105628
 * Ethan Pangilinan - 30179143
 * Joshua Kraft - 30171525
 * Nathan Vaters - 30121908
 * Max Butcher - 30149202
 * Neeraj Ghansela - 30157473
 * Ansel Sulejmani - 30178521
 * Suleman Basit - 30132816
 * Jacob Boyden - 30193220
 * Cheshta Sharma - 30064538
 * Callum Bates - 30188601
 * Armughan Mustafa - 30154601
 * Connor Ell - 30073291
 * Saif Farag - 30195046
 * Ivan Agalakov - 30172107
 * Samuel Turner - 10064857
 * Stephanie Sevilla - 30176781
 * Winston Wang - ????????
 */

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
import com.thelocalmarketplace.software.payment.CashPayment;
import com.thelocalmarketplace.software.payment.Transaction;

public class TransactionTest {
	private Transaction transaction;
	private BarcodedProduct productOne;
	private BarcodedProduct productTwo;
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
		assertThrows(NullPointerException.class, () -> transaction.addItem(null));
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
	    assertEquals(valid, transaction.getPayments()[0]);
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
