package com.thelocalmarketplace.software.state;

import java.math.BigDecimal;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.payment.Transaction;

public class WaitingForItemRemovalState implements IUserSessionState<UserSessionState> {
    @Override
    public UserSessionState onStateSet() {
    	return null;
    }

    @Override
    public void onStateUnset() {

    }

    @Override
    public UserSessionState onScanBarcode(Barcode barcode) {
        return null;
    }

    @Override
    public UserSessionState onWeightChanged(Mass mass) {
        return null;
    }

    @Override
    public UserSessionState onCoinInserted(BigDecimal value) {
        return null;
    }
}
