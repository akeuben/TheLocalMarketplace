package com.thelocalmarketplace.software.state;

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
	
	private Object state;
	
	private UserSessionState(Object sessionState) {
		this.state = sessionState;
	}

	@Override
	public UserSessionState addItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserSessionState weightChanged() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserSessionState paymentAdded() {
		// TODO Auto-generated method stub
		return null;
	}
}
