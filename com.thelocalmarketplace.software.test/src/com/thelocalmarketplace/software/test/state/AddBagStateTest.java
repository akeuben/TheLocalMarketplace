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
