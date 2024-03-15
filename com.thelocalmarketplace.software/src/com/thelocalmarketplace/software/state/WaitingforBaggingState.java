package com.thelocalmarketplace.software.state;

import java.math.BigDecimal;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scanner.Barcode;
import com.tdc.coin.Coin;
import com.thelocalmarketplace.software.payment.Transaction;

public class WaitingforBaggingState implements IUserSessionState<UserSessionState> {

	@Override
	public UserSessionState onStateSet() {
		System.out.println("Waiting for item to be bagged..."); // Prompt the user to bag their item
		return null; // State has not changed, so return null
	}

	@Override
	public void onStateUnset() throws RuntimeException{}

	/**The customer should not be able to scan a new item until 
	 * they have placed the previously scanned item in the bagging area.
	 * If the customer scans an item when the self-checkout is in WaitingForBaggingState,
	 * it will not be added to the transaction, and the customer will be
	 * prompted to put the previously scanned item in the bagging area, otherwise
	 * they will not be able to continue their session as the self-checkout will remain
	 * in the WaitingForBaggingState.
	 */
	
	@Override
	public UserSessionState onScanBarcode(Barcode barcode) {
		System.out.println("Place previously scanned item in bagging area before scanning next item"); 
		return null;
	}

	@Override
	public UserSessionState onWeightChanged(Mass mass) {
		// Case 1: weight was removed
		// Case 2: improper weight was added
		// Case 3: proper weight was added
		return null;
	}

	@Override
	public UserSessionState onCoinInserted(BigDecimal value) {
		System.out.println("Not ready for payment, please place item in bagging area"); // Do not accept payment from...
		//...customer when an item is not yet placed in the bagging area
		// Spit coin out
		return null;
	}
}
