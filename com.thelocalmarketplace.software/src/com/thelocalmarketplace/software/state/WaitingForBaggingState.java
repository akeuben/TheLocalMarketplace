package com.thelocalmarketplace.software.state;

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
import java.math.BigInteger;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.payment.Transaction;

public class WaitingForBaggingState implements IUserSessionState<UserSessionState> {
	
	private final Mass MAXIMUM_MASS_DIFFERENCE = new Mass(BigInteger.valueOf(10*Mass.MICROGRAMS_PER_GRAM)); // Maximum weight discrepancy allowed

	@Override
	public UserSessionState onStateSet() {
		// Disable the coin slot to prevent the user from inserting a coin while the software
		// is not in the correct state
		SelfCheckout.getInstance().getHardware().coinSlot.disable();
		
		// The mass may already be in range (for example, if the weight of the added item was really small.
		// Go directly back to the READY_FOR_ITEM state
		
		// get the mass
		Mass mass;
		try {
			mass = SelfCheckout.getInstance().getHardware().baggingArea.getCurrentMassOnTheScale();
		} catch (OverloadedDevice | RuntimeException e) { // Catch errors when retrieving mass from scale
			return null; //TODO: Notify attendant of the error
		}
		
		// Item is too light to initiate a mass change
		Transaction currentTransaction = SelfCheckout.getInstance().getCurrentSession().getTransaction(); // Get current transaction
		Mass expectedMass = currentTransaction.getExpectedMass(); // Get expected mass
		Mass absoluteDifference = expectedMass.difference(mass).abs(); // Compare expected and actual mass of item placed in bagging area
		System.out.println(absoluteDifference.compareTo(MAXIMUM_MASS_DIFFERENCE));
		if(absoluteDifference.compareTo(MAXIMUM_MASS_DIFFERENCE) == -1) { // If item falls within the scale's sensitivity window,
			return UserSessionState.READY_FOR_ITEM; 					  // go back to ReadyForItemState
		}
			
		return null; // If item is not the correct weight, weight for correct item to be placed/removed from bagging area
		
	}

	@Override
	public void onStateUnset() throws RuntimeException{}
	
	@Override
	public UserSessionState onScanBarcode(Barcode barcode) {
		return null; // State does not change if user scans an additional item
	}
	
	@Override
	public UserSessionState onWeightChanged(Mass mass) {
		Transaction currentTransaction = SelfCheckout.getInstance().getCurrentSession().getTransaction(); // Get current transaction
		Mass expectedMass = currentTransaction.getExpectedMass(); // Get expected mass
		Mass absoluteDifference = expectedMass.difference(mass).abs(); // Compare expected and actual mass of item placed in bagging area
		
		if(absoluteDifference.compareTo(MAXIMUM_MASS_DIFFERENCE) == -1) { // If item falls within the scale's sensitivity window,
			return UserSessionState.READY_FOR_ITEM; 					  // go back to ReadyForItemState
		}
			
		return null; // If item is not the correct weight, weight for correct item to be placed/removed from bagging area
	}
	
	@Override
	public UserSessionState onCoinInserted(BigDecimal value) {
		return null; // State does not change if user inserts coin
	}
}
