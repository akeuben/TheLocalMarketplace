package com.thelocalmarketplace.software.test.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.tdc.coin.Coin;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.SelfCheckoutConfiguration;
import com.thelocalmarketplace.software.payment.CashPayment;
import com.thelocalmarketplace.software.session.UserSession;
import com.thelocalmarketplace.software.state.UserSessionState;

public class ReadyForPaymentStateTest {
	
	UserSession session;
	
	@Before
	public void setup() {
		SelfCheckout.uninitialize();
		SelfCheckout.initialize(new SelfCheckoutConfiguration());
		session = SelfCheckout.getInstance().startNewSession();
		session.getTransaction().addItem(new BarcodedProduct(new Barcode(new Numeral[] {Numeral.five}), "test product", 100, 100));
	}
	
	@Test
	public void testEmptyTransaction() {
		SelfCheckout.uninitialize();
		SelfCheckout.initialize(new SelfCheckoutConfiguration());
		session = SelfCheckout.getInstance().startNewSession();
		session.setState(UserSessionState.READY_FOR_PAYMENT);
		assertEquals(session.getState(), UserSessionState.READY_FOR_ITEM);
	}
	
	@Test
	public void testCoinSlotEnabled() {
		session.setState(UserSessionState.READY_FOR_PAYMENT);
		// The coin slot should be enabled.
		assertFalse(SelfCheckout.getInstance().getHardware().coinSlot.isDisabled());
	}
	
	@Test
	public void testStateSetWithNoRemainingBalance() {
		session.getTransaction().addPayment(new CashPayment(BigDecimal.valueOf(1)));
		session.setState(UserSessionState.READY_FOR_PAYMENT);
		// The session should have ended.
		assertNull(SelfCheckout.getInstance().getCurrentSession());
	}
	
	@Test
	public void testOnScanBarcode() {
		session.setState(UserSessionState.READY_FOR_PAYMENT);
		
		// The state should not change
		UserSessionState newState = UserSessionState.READY_FOR_PAYMENT.onScanBarcode(null);
		
		assertEquals(newState, null);
	}
	
	@Test
	public void testOnWeightChangedSignificant() {
		session.setState(UserSessionState.READY_FOR_PAYMENT);
		
		// The state should not change
		UserSessionState newState = UserSessionState.READY_FOR_PAYMENT.onWeightChanged(new Mass(150.00));

		assertEquals(newState, UserSessionState.WAITING_FOR_BAGGING);
	}
	
	@Test
	public void testOnWeightChangedInsignificant() {
		session.setState(UserSessionState.READY_FOR_PAYMENT);
		
		// The state should not change
		UserSessionState newState = UserSessionState.READY_FOR_PAYMENT.onWeightChanged(new Mass(100.01));
		
		assertEquals(newState, null);
	}
	
	@Test
	public void testInsertingCoins() {
		session.getTransaction().addItem(new BarcodedProduct(new Barcode(new Numeral[] {Numeral.five}), "test product", 100, 100));
		session.setState(UserSessionState.READY_FOR_PAYMENT);
		UserSessionState newState = UserSessionState.READY_FOR_PAYMENT.onCoinInserted(new BigDecimal(1));
		assertEquals(newState, null);
		assertEquals(session.getTransaction().getTotalCost(), BigDecimal.valueOf(1));
		UserSessionState.READY_FOR_PAYMENT.onCoinInserted(new BigDecimal(1));
		assertNull(SelfCheckout.getInstance().getCurrentSession());
	}
}
