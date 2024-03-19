package com.thelocalmarketplace.software.session;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.printer.ReceiptPrinterListener;
import com.jjjwelectronics.scale.ElectronicScaleListener;

public class ReceiptPrinterHandler extends AbstractUserSessionHandler implements ReceiptPrinterListener {
    public ReceiptPrinterHandler(UserSession session) { super(session); }

    @Override
    public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {

    }

    @Override
    public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {

    }

    @Override
    public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {

    }

    @Override
    public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {

    }

    @Override
    public void thePrinterIsOutOfPaper() {

    }

    @Override
    public void thePrinterIsOutOfInk() {

    }

    @Override
    public void thePrinterHasLowInk() {

    }

    @Override
    public void thePrinterHasLowPaper() {

    }

    @Override
    public void paperHasBeenAddedToThePrinter() {

    }

    @Override
    public void inkHasBeenAddedToThePrinter() {

    }
}
