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
import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.SelfCheckoutConfiguration;
import com.thelocalmarketplace.software.session.UserSession;
import com.thelocalmarketplace.software.state.UserSessionState;

public class WaitingForBaggingStateTest {
	
	UserSession session;
	
	@Before
	public void setup() {
		SelfCheckout.uninitialize();
		SelfCheckout.initialize(new SelfCheckoutConfiguration());
		session = SelfCheckout.getInstance().startNewSession();
	}
	
	@Test
	public void testCoinSlotDisabled() {
		session.setState(UserSessionState.WAITING_FOR_BAGGING);
		// The coin slot should be disabled.
		assertTrue(SelfCheckout.getInstance().getHardware().coinSlot.isDisabled());
	}
	
	@Test
	public void testStateChangedToAddItemIfWeightCorrectOnStateSet() {
		session.setState(UserSessionState.WAITING_FOR_BAGGING);
		// The coin slot should be disabled.
		assertTrue(session.getState().equals(UserSessionState.READY_FOR_ITEM));
	}
	
	@Test
	public void testOnStateSetValidParameters() {
		session.getTransaction().addItem(new BarcodedProduct(new Barcode(new Numeral[] {Numeral.five}), "test product", 100, 100));
		session.setState(UserSessionState.WAITING_FOR_BAGGING);
		// The coin slot should be disabled.
		assertTrue(session.getState().equals(UserSessionState.WAITING_FOR_BAGGING));
	}
	
	@Test
	public void testOnStateSetOverloadWeight() {
		SelfCheckout.getInstance().getHardware().baggingArea.addAnItem(new BarcodedItem(new Barcode(new Numeral[] {Numeral.five}), new Mass(9999999999999999999999999999999999999999999.0)));
		session.setState(UserSessionState.WAITING_FOR_BAGGING);
		
		// The coin slot should be disabled.
		assertTrue(session.getState().equals(UserSessionState.WAITING_FOR_BAGGING));
	}
	
	@Test
	public void testOnStateSetTooMuchWeight() {
		SelfCheckout.getInstance().getHardware().baggingArea.addAnItem(new BarcodedItem(new Barcode(new Numeral[] {Numeral.five}), new Mass(9999.0)));
		session.setState(UserSessionState.WAITING_FOR_BAGGING);
		
		// The coin slot should be disabled.
		assertTrue(session.getState().equals(UserSessionState.WAITING_FOR_BAGGING));
	}
	
	@Test
	public void testOnScanBarcode() {
		session.getTransaction().addItem(new BarcodedProduct(new Barcode(new Numeral[] {Numeral.five}), "test product", 100, 100));
		session.setState(UserSessionState.WAITING_FOR_BAGGING);
		
		// The state should not change
		UserSessionState newState = UserSessionState.WAITING_FOR_BAGGING.onScanBarcode(null);
		
		assertEquals(newState, null);
	}
	
	@Test
	public void testOnCoinInserted() {
		session.getTransaction().addItem(new BarcodedProduct(new Barcode(new Numeral[] {Numeral.five}), "test product", 100, 100));
		session.setState(UserSessionState.WAITING_FOR_BAGGING);
		
		// The state should not change
		UserSessionState newState = UserSessionState.WAITING_FOR_BAGGING.onCoinInserted(null);
		
		assertEquals(newState, null);
	}
	
	@Test
	public void testOnWeightChangedNotEnough() {
		session.getTransaction().addItem(new BarcodedProduct(new Barcode(new Numeral[] {Numeral.five}), "test product", 100, 100));
		session.setState(UserSessionState.WAITING_FOR_BAGGING);
		
		// The state should not change
		UserSessionState newState = UserSessionState.WAITING_FOR_BAGGING.onWeightChanged(new Mass(50.0));

		assertEquals(null, newState);
	}
	
	@Test
	public void testOnWeightChangedTooMuch() {
		session.getTransaction().addItem(new BarcodedProduct(new Barcode(new Numeral[] {Numeral.five}), "test product", 100, 100));
		session.setState(UserSessionState.WAITING_FOR_BAGGING);
		
		// The state should not change
		UserSessionState newState = UserSessionState.WAITING_FOR_BAGGING.onWeightChanged(new Mass(2000.0));

		assertEquals(null, newState);
	}
	
	@Test
	public void testOnWeightChangedJustRight() {
		session.getTransaction().addItem(new BarcodedProduct(new Barcode(new Numeral[] {Numeral.five}), "test product", 100, 100));
		session.setState(UserSessionState.WAITING_FOR_BAGGING);
		
		// The returned state should be item
		UserSessionState newState = UserSessionState.WAITING_FOR_BAGGING.onWeightChanged(new Mass(100.0));

		assertEquals(UserSessionState.READY_FOR_ITEM, newState);
	}
	
	@Test
	public void testOnWeightChangedWithinMargin() {
		session.getTransaction().addItem(new BarcodedProduct(new Barcode(new Numeral[] {Numeral.five}), "test product", 100, 100));
		session.setState(UserSessionState.WAITING_FOR_BAGGING);
		
		// The state should not change
		UserSessionState newState = UserSessionState.WAITING_FOR_BAGGING.onWeightChanged(new Mass(101.0));
		
		assertEquals(UserSessionState.READY_FOR_ITEM, newState);
	}
}
