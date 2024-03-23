package com.thelocalmarketplace.software.test.state;

import static org.junit.Assert.assertEquals;

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
import com.thelocalmarketplace.software.payment.Transaction;
import com.thelocalmarketplace.software.session.UserSession;
import com.thelocalmarketplace.software.state.UserSessionState;

public class AddBagStateTest {
	private UserSession session;
	private BarcodedItem bag;
	private Numeral num;
	private Barcode bc;
	private BarcodedItem bagTooHeavy;
	
	@Before
	public void setup() {
		SelfCheckout.uninitialize();
		SelfCheckout.initialize(new SelfCheckoutConfiguration());
		this.session = SelfCheckout.getInstance().startNewSession();
		this.num = Numeral.eight;
		this.bc= new Barcode(new Numeral[] {num});
		this.bag= new BarcodedItem(bc, new Mass(10));
		this.bagTooHeavy= new BarcodedItem(bc,new Mass(999999999));
	}
	
	@Test
	public void testAddBag() {
		SelfCheckout.getInstance().getHardware().baggingArea.addAnItem(bag);
		session.getUIHandler().addBagSelected();
		assertEquals(session.getTransaction().getExpectedMass(), bag.getMass());
	}
	
	@Test
	public void testAddTooMuchWeight() {
		SelfCheckout.getInstance().getHardware().baggingArea.addAnItem(bagTooHeavy);
		session.getUIHandler().addBagSelected();
		assertEquals(session.getState(), UserSessionState.ADD_BAG);
	}
	
	@Test
	public void testStateAfterAddBag() {
		SelfCheckout.getInstance().getHardware().baggingArea.addAnItem(bag);
		session.getUIHandler().addBagSelected();
		assertEquals(session.getState(), UserSessionState.READY_FOR_ITEM);
	}
	
	@Test
	public void testOnWeightChange() {
		// The state should not change
		UserSessionState newState = UserSessionState.ADD_BAG.onWeightChanged(null);
		assertEquals(newState, null);
	}
	
	@Test
	public void testOnScanBarcode() {
		// The state should not change
		UserSessionState newState = UserSessionState.ADD_BAG.onScanBarcode(null);
		assertEquals(newState, null);
	}
	
	@Test
	public void testOnCoinInserted() {
		// The state should not change
		UserSessionState newState = UserSessionState.ADD_BAG.onCoinInserted(null);
		assertEquals(newState, null);
	}

}
