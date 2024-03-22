package com.thelocalmarketplace.software.session;

import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.UI.UIObserver;
import com.thelocalmarketplace.software.payment.Transaction;
import com.thelocalmarketplace.software.state.AddBagState;
import com.thelocalmarketplace.software.state.UserSessionState;
import com.thelocalmarketplace.software.state.WaitingforBaggingState;

public class UIHandler extends AbstractUserSessionHandler implements UIObserver {

	public UIHandler(UserSession session) {
		super(session);
	}

	@Override
	public void addBagSelected() {
		//super.getUserSession().getTransaction().addBag();
		//TODO change state to add bag state
	}

	@Override
	public void removeItemSelected(Product product) {
		super.getUserSession().getTransaction().removeItem(product);
		super.getUserSession().setState(UserSessionState.WAITING_FOR_BAGGING);
		// Waits for user to remove item from bagging area
		if (getUserSession().getTransaction().getExpectedMass() != getUserSession().getTransaction().getExpectedMass() + product.getExpectedWeight()) {

			// Changes state back to READY_FOR_ITEM when item is removed
			super.getUserSession().setState(UserSessionState.READY_FOR_ITEM);
		}


	}

	@Override
	public void bulkyItemSelected(Product product) {
		// TODO Change state to block further customer actions
		// TODO Signal to attendant (Cannot be implemented yet)
		boolean attendantApproves = true; //TODO Change to signal to attendant, getting a boolean response
		if (attendantApproves) {
			super.getUserSession().getTransaction().addBulkyItem(product);
		}


		
	}

}
