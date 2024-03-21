package com.thelocalmarketplace.software.session;

import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.UI.UIObserver;
import com.thelocalmarketplace.software.payment.Transaction;

public class UIHandler extends AbstractUserSessionHandler implements UIObserver {

	public UIHandler(UserSession session) {
		super(session);
	}

	@Override
	public void addBagSelected() {
		super.getUserSession().getTransaction().addBag();
	}

	@Override
	public void removeItemSelected(Product product) {
		// TODO Change state to block further customer actions
		super.getUserSession().getTransaction().removeItem(product);
		// TODO Change state to allow further customer actions
		// TODO Indicate to customer to remove item from baggin area or shopping cart
		
	}

	@Override
	public void bulkyItemSelected() {
		// TODO Auto-generated method stub
		
	}

}
