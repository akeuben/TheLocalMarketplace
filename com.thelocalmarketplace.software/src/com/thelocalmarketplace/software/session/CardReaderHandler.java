package com.thelocalmarketplace.software.session;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.card.AbstractCardReader;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardData;
import com.jjjwelectronics.card.CardReaderListener;
import com.thelocalmarketplace.software.state.UserSessionState;

public class CardReaderHandler extends AbstractUserSessionHandler implements CardReaderListener {

	public CardReaderHandler(UserSession session) {
		super(session);
	}

	@Override
	public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {}

	@Override
	public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {}

	@Override
	public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {}

	@Override
	public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {}

	@Override
	public void aCardHasBeenSwiped() {} // will be a stub for now

	@Override
	public void theDataFromACardHasBeenRead(CardData data) {
		UserSessionState newState = getUserSession().getState().onCardDataRead(data);
		if(newState != null) {
			getUserSession().setState(newState);
		}

	}

}
