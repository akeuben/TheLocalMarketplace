package com.thelocalmarketplace.software.state;

import com.thelocalmarketplace.software.session.UserSession;

public class WaitingForAttendantState implements IUserSessionState<UserSessionState> {

    @Override
    public UserSessionState onStateSet(UserSession session) {
		// Disable the coin slot to prevent the user from inserting a coin while the software
		// is not in the correct state
		session.getHardware().getCoinSlot().disable();
		session.getHardware().getBanknoteInput().disable();
		
        return null;
    }



}
