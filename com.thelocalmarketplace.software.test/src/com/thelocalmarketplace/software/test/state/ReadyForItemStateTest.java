package com.thelocalmarketplace.software.test.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.SelfCheckoutConfiguration;
import com.thelocalmarketplace.software.session.UserSession;
import com.thelocalmarketplace.software.state.UserSessionState;

public class ReadyForItemStateTest {
	
	UserSession session;
	
	@Before
	public void setup() {
		SelfCheckout.uninitialize();
		SelfCheckout.initialize(new SelfCheckoutConfiguration());
		session = SelfCheckout.getInstance().startNewSession();
	}
	
	@Test
	public void testCoinSlotDisabled() {
		session.setState(UserSessionState.READY_FOR_ITEM);
		// The coin slot should be disabled.
		assertTrue(SelfCheckout.getInstance().getHardware().coinSlot.isDisabled());
	}
	
	@Test
	public void testOnCoinInserted() {
		session.getTransaction().addItem(new BarcodedProduct(new Barcode(new Numeral[] {Numeral.five}), "test product", 100, 100));
		session.setState(UserSessionState.READY_FOR_ITEM);
		
		// The state should not change
		UserSessionState newState = UserSessionState.READY_FOR_ITEM.onCoinInserted(null);
		
		assertEquals(newState, null);
	}
	
	@Test
	public void testOnWeightChangedSignificant() {
		session.setState(UserSessionState.READY_FOR_ITEM);
		
		// The state should not change
		UserSessionState newState = UserSessionState.READY_FOR_ITEM.onWeightChanged(new Mass(50.0));

		assertEquals(newState, UserSessionState.WAITING_FOR_BAGGING);
	}
	
	@Test
	public void testOnWeightChangedInsignificant() {
		session.setState(UserSessionState.READY_FOR_ITEM);
		
		// The state should not change
		UserSessionState newState = UserSessionState.READY_FOR_ITEM.onWeightChanged(new Mass(0.001));

		assertEquals(newState, null);
	}
	
	@Test
	public void testScanBarcodeInDatabase() {
		session.setState(UserSessionState.READY_FOR_ITEM);
		
		Barcode barcode = new Barcode(new Numeral[] {Numeral.eight});
		
		BarcodedProduct product = new BarcodedProduct(barcode, "test product", 1000, 100);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode, product);
		
		// The state should not change
		UserSessionState newState = UserSessionState.READY_FOR_ITEM.onScanBarcode(barcode);

		assertEquals(session.getTransaction().getProducts()[0], product);
		
		assertEquals(newState, UserSessionState.WAITING_FOR_BAGGING);
	}
	
	@Test
	public void testScanBarcodeNotInDatabase() {
		session.setState(UserSessionState.READY_FOR_ITEM);
		
		Barcode barcode = new Barcode(new Numeral[] {Numeral.seven});
		
		BarcodedProduct product = new BarcodedProduct(barcode, "test product", 1000, 100);
		
		// The state should not change
		UserSessionState newState = UserSessionState.READY_FOR_ITEM.onScanBarcode(barcode);

		assertEquals(session.getTransaction().getProducts().length, 0);
		
		assertEquals(newState, null);
	}
}
