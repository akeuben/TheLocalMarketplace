package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertThrows;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

//import com.tdc.coin.Coin;
import com.thelocalmarketplace.software.Transaction;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.Product;

public class TransactionTest {
	//private Coin coinOne;
	//private Coin coinTwo;
	private Transaction transaction;
	private Product productOne;
	private Product productTwo;
	private Numeral num;
	private Barcode bc;
	//private BarcodedProduct productZeroCost;
	//private BarcodedProduct productZeroWeight;
	
	@Before
	public void setup() {
		this.transaction = new Transaction();
		this.num = num.eight;
		this.bc= new Barcode(new Numeral[] {num});
		//this.num = (Numeral)one((byte)1);
		//this.coinOne = new Coin(Currency.getInstance("CAD"), new BigDecimal(1.0));
		//this.coinTwo = new Coin(Currency.getInstance("CAD"), new BigDecimal(1.0));
		this.productOne = new BarcodedProduct(bc, "test1", 1, 1);
		this.productTwo = new BarcodedProduct(bc, "test2", 2, 2);
		//this.productZeroCost = new BarcodedProduct(null, "test1", 0, 1);
		//this.productZeroWeight = new BarcodedProduct(null, "test2", 1, 0);
		
	}
	
	//@Test
	//public void testNullItemBarcode() {
		//assertThrows(NullPointerException.class, () -> Transaction.addItem((Barcode)null));
	//}
	
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
		Assert.assertTrue(transaction.getExpectedWeight() >= 0);
	}
	
	@Test
	public void testPositiveCost() {
		Assert.assertTrue(transaction.getTotalCost().compareTo(BigDecimal.ZERO) >= 0);
	}
	
	@Test
	public void testAddOneItemWeight() {
		transaction.addItem(productOne);
		Assert.assertEquals(transaction.getExpectedWeight(), productOne.getExpectedWeight(), 0);
	}
	
	@Test
	public void testAddMultipleItemsWeight() {
		transaction.addItem(productOne);
		transaction.addItem(productTwo);
		Assert.assertEquals(transaction.getExpectedWeight(), productOne.getExpectedWeight()+productTwo.getExpectedWeight(), 0);
	}
	
	@Test
	public void testAddOneItemCost() {
		transaction.addItem(productOne);
		Assert.assertEquals(transaction.getTotalCost(), BigDecimal.valueOf(productOne.getPrice()));
	}
	
	@Test
	public void testAddMultipleItemsCost() {
		transaction.addItem(productOne);
		transaction.addItem(productTwo);
		Assert.assertEquals(transaction.getTotalCost(), BigDecimal.valueOf(productOne.getPrice()+productTwo.getPrice()));
	}
	
}
