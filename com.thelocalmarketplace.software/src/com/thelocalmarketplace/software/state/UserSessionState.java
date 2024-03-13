package com.thelocalmarketplace.software.state;

import java.math.BigDecimal;

import com.jjjwelectronics.Mass;
import com.thelocalmarketplace.hardware.Product;

public enum UserSessionState implements IUserSessionStateActions<UserSessionState> {
	/**
	 * This state is used when the system
	 * is ready for an item to be added to the bagging area.
	 */
	READY_FOR_ITEM(null),
	/**
	 * This state is used when the system
	 * is waiting for an item to be added to the bagging area
	 */
	WAITING_FOR_BAGGING(null),
	/**
	 * This state is used when the system
	 * is ready to accept payment
	 */
	READY_FOR_PAYMENT(null);
	
	private IUserSessionState<UserSessionState> state;
	
	private UserSessionState(IUserSessionState<UserSessionState> sessionState) {
		this.state = sessionState;
	}

	@Override
	public UserSessionState addItem(Product product) {
		return this.state.addItem(product);
	}

	@Override
	public UserSessionState weightChanged(Mass mass) {
		return this.state.weightChanged(mass);
	}

	@Override
	public UserSessionState paymentAdded(BigDecimal amount, PaymentSource source) {
		return this.state.paymentAdded(amount, source);
	}
}
