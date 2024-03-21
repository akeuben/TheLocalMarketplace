package com.thelocalmarketplace.software.session;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.printer.ReceiptPrinterListener;
import com.jjjwelectronics.scale.ElectronicScaleListener;

import java.util.ArrayList;

public class ReceiptPrinterHandler extends AbstractUserSessionHandler implements ReceiptPrinterListener {

    private boolean fillInkFlag = false;
    private boolean fillPaperFlag = false;

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
        fillPaperFlag = true;
    }

    @Override
    public void thePrinterIsOutOfInk() {
        fillInkFlag = true;
    }

    @Override
    public void thePrinterHasLowInk() {
        fillInkFlag = true;
    }

    @Override
    public void thePrinterHasLowPaper() {
        fillPaperFlag = true;
    }

    @Override
    public void paperHasBeenAddedToThePrinter() {
        fillPaperFlag = false;
    }

    @Override
    public void inkHasBeenAddedToThePrinter() {
        fillInkFlag = false;
    }

    public boolean refillFlagsSet(){
        if (fillInkFlag | fillPaperFlag){
            return true;
        }
        else {
            return false;
        }
    }
}
