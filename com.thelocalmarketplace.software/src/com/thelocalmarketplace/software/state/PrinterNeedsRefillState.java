package com.thelocalmarketplace.software.state;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.software.SelfCheckout;

import java.math.BigDecimal;

public class PrinterNeedsRefillState implements IUserSessionState<UserSessionState> {
    @Override
    public UserSessionState onStateSet() {
        SelfCheckout.getInstance().attendantStationFlagged = true;
        return null;
    }

    @Override
    public void onStateUnset() {
        SelfCheckout.getInstance().attendantStationFlagged = false;
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

    @Override
    public UserSessionState onPrinterRefilled() {
        SelfCheckout.getInstance().getCurrentSession().setState(UserSessionState.READY_FOR_PAYMENT);
        return null;
    }
}
