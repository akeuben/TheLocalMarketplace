package com.thelocalmarketplace.software.state;

import com.thelocalmarketplace.software.SelfCheckout;

public class AddingBagsState implements IUserSessionState<UserSessionState> {

    @Override
    public UserSessionState onStateSet() {
        // Disable the coin slot to prevent the user from inserting a coin while the software
        // is not in the correct state
        SelfCheckout.getInstance().getHardware().getCoinSlot().disable();
        SelfCheckout.getInstance().getHardware().getBanknoteInput().disable();
        return null;
    }

}
