package com.thelocalmarketplace.software.state;

import java.math.BigDecimal;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scanner.Barcode;
import com.tdc.coin.Coin;

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
	READY_FOR_PAYMENT(new ReadyForPaymentState());
	
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
