package com.thelocalmarketplace.software.session;

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

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.bag.ReusableBag;
import com.jjjwelectronics.scale.AbstractElectronicScale;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.UI.UIObserver;
import com.thelocalmarketplace.software.state.UserSessionState;

import java.math.BigInteger;

public class UIHandler extends AbstractUserSessionHandler implements UIObserver {

	public UIHandler(UserSession session) {
		super(session);
	}

	@Override
	public void addBagSelected(ReusableBag bag) {
		//sets state to add bag state
		getUserSession().setState(UserSessionState.WAITING_FOR_BAGGING);
		//adds bag to transaction and checks weight
		SelfCheckout.getInstance().getHardware().getBaggingArea().addAnItem(bag);
		getUserSession().setState(UserSessionState.WAITING_FOR_ATTENDANT);
	}

	@Override
	public void removeItemSelected(BarcodedProduct product) {
		BarcodedItem item = new BarcodedItem(product.getBarcode(), new Mass(product.getExpectedWeight()));
		super.getUserSession().setState(UserSessionState.WAITING_FOR_ATTENDANT);
		super.getUserSession().getTransaction().removeItem(product);
		super.getUserSession().setState(UserSessionState.WAITING_FOR_BAGGING);
		//removeAnItem handles mass changes
		SelfCheckout.getInstance().getHardware().getBaggingArea().removeAnItem(item);
	}

	@Override
	public void skipBaggingSelected(BarcodedProduct product) {
		// TODO: Wait for attendant to change state of transaction back to normal, then add item.
		super.getUserSession().setState(UserSessionState.WAITING_FOR_ATTENDANT);
		// TODO: Once state changes back to normal, will add the bulky item to the transaction (currently working with no attendant feedback)
		super.getUserSession().getTransaction().skipBagging(product);
		super.getUserSession().setState(UserSessionState.READY_FOR_ITEM);
	}
}
