package com.thelocalmarketplace.software.session;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.software.UI.UIObserver;
import com.thelocalmarketplace.software.payment.TransactionItem;
import com.thelocalmarketplace.software.state.UserSessionState;

public class UIHandler extends AbstractUserSessionHandler implements UIObserver {

	public UIHandler(UserSession session) {
		super(session);
	}

	@Override
	public void addBagSelected() {
		super.getUserSession().getTransaction().addOwnBag();
		super.getUserSession().setState(UserSessionState.ADDING_BAGS_STATE);
		//Program will wait until bagging is corrected and state is changed back to ready.
	}

	@Override
	public void removeItemSelected(TransactionItem product) {
		super.getUserSession().getTransaction().removeItem(product);
		super.getUserSession().setState(UserSessionState.WAITING_FOR_BAGGING);
		//Program will wait until bagging is corrected and state is changed back to ready.
	}
	
	@Override
	public void purchasingBagsSelected (int numberofBags) {
		super.getUserSession().getTransaction().purchaseBags(numberofBags);
	}

	@Override
	public void skipBaggingSelected() {
		super.getUserSession().setState(UserSessionState.WAITING_FOR_ATTENDANT);
	}

	@Override
	public void doneAddingBagsSelected() {
		super.getUserSession().setState(UserSessionState.WAITING_FOR_BAGGING);
	}

	@Override
	public void addFromPLU(PriceLookUpCode code) {
		getUserSession().setState(getUserSession().getState().onPLUentered(getUserSession(), code));
	}
}
