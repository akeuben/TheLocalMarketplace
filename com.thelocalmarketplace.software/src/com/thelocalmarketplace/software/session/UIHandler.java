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
		super.getUserSession().setState(UserSessionState.ADD_BAG);
	}

	@Override
	public void removeItemSelected(Product product) {
		super.getUserSession().getTransaction().removeItem(product);
		super.getUserSession().setState(UserSessionState.WAITING_FOR_BAGGING);
		// Waits for user to remove item from bagging area
		//TODO Not sure how this should be implemented. Is there a way to get the current mass of the scale???
		if (super.getUserSession().getTransaction().getExpectedMass() != super.getUserSession().getTransaction().getExpectedMass() + product.getExpectedWeight()) {
			// Changes state back to READY_FOR_ITEM when item is removed
			super.getUserSession().setState(UserSessionState.READY_FOR_ITEM);
		}


	}

	@Override
	public void bulkyItemSelected(Product product) {
		super.getUserSession().setState(UserSessionState.WAITING_FOR_ATTENDANT);
		// Once state changes back to normal, will add the bulky item to the transaction
		//TODO Wait for attendant to change state of transaction back to normal, then add item.
			super.getUserSession().getTransaction().addBulkyItem(product);
		}


		
	}

}
