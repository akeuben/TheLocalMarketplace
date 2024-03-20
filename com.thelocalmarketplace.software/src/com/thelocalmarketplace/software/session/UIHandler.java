package com.thelocalmarketplace.software.session;

import com.thelocalmarketplace.software.UI.UIObserver;
import com.thelocalmarketplace.software.payment.Transaction;

public class UIHandler implements UIObserver {

	@Override
	public void addBag() {
		UserSession.getTransaction().addBag();
	}

	@Override
	public void removeItem() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bulkyItem() {
		// TODO Auto-generated method stub
		
	}

}
