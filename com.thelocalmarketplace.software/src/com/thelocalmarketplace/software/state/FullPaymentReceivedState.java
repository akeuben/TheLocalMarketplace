package com.thelocalmarketplace.software.state;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scanner.Barcode;

import java.math.BigDecimal;

public class FullPaymentReceivedState implements IUserSessionState<UserSessionState> {
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
