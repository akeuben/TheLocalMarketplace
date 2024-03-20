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

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scanner.Barcode;

public enum UserSessionState implements IUserSessionState<UserSessionState> {
	/**
	 * This state is used when the system
	 * is ready for an item to be added to the bagging area.
	 */
	READY_FOR_ITEM(new ReadyForItemState()),
	/**
	 * This state is used when the system
	 * is waiting for an item to be added to the bagging area
	 */
	WAITING_FOR_BAGGING(new WaitingforBaggingState()),
	/**
	 * This state is used when the system
	 * is ready to accept payment
	 */
	READY_FOR_PAYMENT(new ReadyForPaymentState()),
	/**
	 * This state is used when the system has received full payment and
	 * can end the customers session
	 */
	PRINT_RECEIPT(new PrintReceiptState());
	
	private IUserSessionState<UserSessionState> state;
	
	private UserSessionState(IUserSessionState<UserSessionState> sessionState) {
		this.state = sessionState;
	}

	@Override
	public UserSessionState onStateSet() {
		return state.onStateSet();
	}

	@Override
	public void onStateUnset() {
		state.onStateUnset();
	}

	@Override
	public UserSessionState onScanBarcode(Barcode barcode) {
		return state.onScanBarcode(barcode);
	}

	@Override
	public UserSessionState onWeightChanged(Mass mass) {
		return state.onWeightChanged(mass);
	}

	@Override
	public UserSessionState onCoinInserted(BigDecimal value) {
		return state.onCoinInserted(value);
	}
}
