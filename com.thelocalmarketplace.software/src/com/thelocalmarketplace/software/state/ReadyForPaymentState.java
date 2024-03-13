package com.thelocalmarketplace.software.state;

import com.jjjwelectronics.Mass;
import com.thelocalmarketplace.hardware.Product;

import java.math.BigDecimal;

public class ReadyForPaymentState implements IUserSessionState<UserSessionState> {

    @Override
    public void onStateSet() {

    }

    @Override
    public void onStateUnset() {

    }

    @Override
    public UserSessionState addItem(Product product) {
        return null;
    }

    @Override
    public UserSessionState weightChanged(Mass mass) {
        return null;
    }

    @Override
    public UserSessionState paymentAdded(BigDecimal amount, PaymentSource source) {
        return null;
    }
}
