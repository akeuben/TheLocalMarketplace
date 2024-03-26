package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scale.IElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.jjjwelectronics.scanner.IBarcodeScanner;
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinSlot;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.SelfCheckoutConfiguration;
import com.thelocalmarketplace.software.payment.Transaction;
import com.thelocalmarketplace.software.session.UserSession;
import com.thelocalmarketplace.software.state.UserSessionState;

public class FullSystemTest {

	Barcode barcode1, barcode2, barcode3;
	BarcodedProduct product1, product2, product3;
	
	Coin dollarCoin;
	Coin fakeCoin;
	
	@Before
	public void setup() {
		SelfCheckout.uninitialize();
		SelfCheckout.initialize(new SelfCheckoutConfiguration());
		
		barcode1 = new Barcode(new Numeral[] {
			Numeral.one,
			Numeral.one,
			Numeral.one,
			Numeral.one,
			Numeral.one,
			Numeral.one,
			Numeral.one,
			Numeral.one,
			Numeral.one
		});
		
		barcode2 = new Barcode(new Numeral[] {
			Numeral.two,
			Numeral.two,
			Numeral.two,
			Numeral.two,
			Numeral.two,
			Numeral.two,
			Numeral.two,
			Numeral.two,
			Numeral.two
		});
		
		barcode3 = new Barcode(new Numeral[] {
			Numeral.three,
			Numeral.three,
			Numeral.three,
			Numeral.three,
			Numeral.three,
			Numeral.three,
			Numeral.three,
			Numeral.three,
			Numeral.three
		});

		product1 = new BarcodedProduct(barcode1, "Fake Product 1", 1000, 100);
		product2 = new BarcodedProduct(barcode2, "Fake Product 2", 2000, 200);
		product3 = new BarcodedProduct(barcode3, "Fake Product 3", 3000, 300);

		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode1, product1);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode2, product2);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode3, product3);
		
		dollarCoin = new Coin(Currency.getInstance(Locale.CANADA), BigDecimal.ONE);
		fakeCoin = new Coin(Currency.getInstance(Locale.CHINA), BigDecimal.TEN);
	}
	
	@Test
	public void TestSingleItemTransaction() {
		SelfCheckout sc = SelfCheckout.getInstance();
		UserSession session = sc.startNewSession();
		Transaction transaction = session.getTransaction();

		IBarcodeScanner scanner = SelfCheckout.getInstance().getHardware().mainScanner;
		IElectronicScale baggingArea = SelfCheckout.getInstance().getHardware().baggingArea;
		CoinSlot coinSlot = SelfCheckout.getInstance().getHardware().coinSlot;

		assertEquals(session.getState(), UserSessionState.READY_FOR_ITEM);
		
		// Add product 1
		for(int i = 0; i < 100; i++) scanner.scan(new BarcodedItem(barcode1, new Mass(100.0)));
		
		// We should now be waiting for the item to be bagged
		assertEquals(session.getState(), UserSessionState.WAITING_FOR_BAGGING);
		
		// Check the cost and expected weight are correct
		assertEquals(transaction.getTotalCost().compareTo(BigDecimal.valueOf(10.00)), 0);
		assertEquals(transaction.getExpectedMass().compareTo(new Mass(100.0)), 0);
		
		// Check there is 1 item in the transaction
		assertEquals(transaction.getProducts().length, 1);
		
		// Place the item in the bagging area
		baggingArea.addAnItem(new BarcodedItem(barcode1, new Mass(100.0)));

		// We should now be in the ready for item state
		assertEquals(session.getState(), UserSessionState.READY_FOR_ITEM);
		
		// State our intentions to pay now
		session.setState(UserSessionState.READY_FOR_PAYMENT);
		assertEquals(session.getState(), UserSessionState.READY_FOR_PAYMENT);
		
		// Pay 1 dollar
		try {
			coinSlot.receive(dollarCoin);
		} catch (DisabledException | CashOverloadException e) {
			throw new RuntimeException();
		}
		
		BigDecimal expectedCost = BigDecimal.valueOf(9.00);
		
		assertEquals(transaction.getTotalCost().compareTo(expectedCost), 0);
		
		for(int i = 0; i < 9; i++) {
			try {
				coinSlot.receive(dollarCoin);
				expectedCost = expectedCost.subtract(BigDecimal.ONE);
				assertEquals(transaction.getTotalCost().compareTo(expectedCost), 0);
			} catch (DisabledException | CashOverloadException e) {
				throw new RuntimeException();
			}
		}
		
		assertEquals(transaction.getTotalCost().compareTo(BigDecimal.valueOf(0.99)), 0);
		try {
			coinSlot.receive(dollarCoin);
		} catch (DisabledException | CashOverloadException e) {
			throw new RuntimeException();
		}
		assertNull(SelfCheckout.getInstance().getCurrentSession());
	}
	
	@Test
	public void TestTryAddWeightDuringPayment() {
		SelfCheckout sc = SelfCheckout.getInstance();
		UserSession session = sc.startNewSession();
		Transaction transaction = session.getTransaction();

		IBarcodeScanner scanner = SelfCheckout.getInstance().getHardware().mainScanner;
		IElectronicScale baggingArea = SelfCheckout.getInstance().getHardware().baggingArea;
		
		BarcodedItem item1 = new BarcodedItem(barcode1, new Mass(100.0));

		assertEquals(session.getState(), UserSessionState.READY_FOR_ITEM);
		
		// Add product 1
		for(int i = 0; i < 100; i++) scanner.scan(new BarcodedItem(barcode1, new Mass(100.0)));
		
		// We should now be waiting for the item to be bagged
		assertEquals(session.getState(), UserSessionState.WAITING_FOR_BAGGING);
		
		// Check the cost and expected weight are correct
		assertEquals(transaction.getTotalCost().compareTo(BigDecimal.valueOf(10.00)), 0);
		assertEquals(transaction.getExpectedMass().compareTo(new Mass(100.0)), 0);
		
		// Check there is 1 item in the transaction
		assertEquals(transaction.getProducts().length, 1);
		
		// Place the item in the bagging areas
		baggingArea.addAnItem(item1);

		// We should now be in the ready for item state
		assertEquals(session.getState(), UserSessionState.READY_FOR_ITEM);
		
		// State our intentions to pay now
		session.setState(UserSessionState.READY_FOR_PAYMENT);
		assertEquals(session.getState(), UserSessionState.READY_FOR_PAYMENT);
		
		BarcodedItem item2 = new BarcodedItem(barcode2, new Mass(100.0));
		
		// Place the item in the bagging area
		baggingArea.addAnItem(item2);
		
		// We should now be waiting for the item to be removed.
		assertEquals(session.getState(), UserSessionState.WAITING_FOR_BAGGING);
		
		// Place the item in the bagging area
		baggingArea.removeAnItem(item2);

		// We should now be in the ready for item state
		assertEquals(session.getState(), UserSessionState.READY_FOR_ITEM);
	}
	
	@Test 
	public void TestTryAddWeightWithoutItem() {
		SelfCheckout sc = SelfCheckout.getInstance();
		UserSession session = sc.startNewSession();

		IElectronicScale baggingArea = SelfCheckout.getInstance().getHardware().baggingArea;
		
		BarcodedItem item1 = new BarcodedItem(barcode1, new Mass(100.0));

		assertEquals(session.getState(), UserSessionState.READY_FOR_ITEM);
		
		// Place the item in the bagging area
		baggingArea.addAnItem(item1);
		
		// We should now be waiting for the item to be bagged
		assertEquals(session.getState(), UserSessionState.WAITING_FOR_BAGGING);
		
		// Remove the item from the bagging area
		baggingArea.removeAnItem(item1);

		assertEquals(session.getState(), UserSessionState.READY_FOR_ITEM);
	}
	
	
	// test to see if adding item that's not in the database throws the proper exception
	@Test
	public void testTryAddItemNotInDataBase() {
		SelfCheckout sc  = SelfCheckout.getInstance(); 
		UserSession session = sc.startNewSession();
		
		IBarcodeScanner scanner = sc.getHardware().mainScanner; 
		Numeral[] dummyCode = {Numeral.one, Numeral.two};
		BarcodedItem newItem = new BarcodedItem(new Barcode(dummyCode), new Mass(10.0));
		scanner.scan(newItem); 
		assertEquals(session.getState(), UserSessionState.READY_FOR_ITEM);
	}
}
