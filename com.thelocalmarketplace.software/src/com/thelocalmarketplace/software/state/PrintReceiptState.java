package com.thelocalmarketplace.software.state;

import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.printer.IReceiptPrinter;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.payment.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;

public class PrintReceiptState implements IUserSessionState<UserSessionState> {

    private Transaction finalTransactionRecord;
    private ArrayList<String> itemizedTransaction;
    private IReceiptPrinter hardwarePrinter;

    /**
     * Since the receipt to be printed as soon as a full payment has been made
     * The onStateSetMethod should be doing most of the work for this state
     */
    @Override
    public UserSessionState onStateSet() {
        hardwarePrinter = SelfCheckout.getInstance().getHardware().printer;
        finalTransactionRecord = SelfCheckout.getInstance().getCurrentSession().getTransaction();//prob add a null check just incase
        itemizedTransaction = new ArrayList<String>();
        String workingString = "";
        int totalCharsToPrint = 0;
        for (BarcodedProduct product : finalTransactionRecord.getBarcodedProducts()){
            workingString = product.getBarcode().toString();
            workingString += " : $";
            workingString += String.valueOf(product.getPrice());
            itemizedTransaction.add(workingString);
            String strippedString = workingString.replaceAll("\\s", "");
            totalCharsToPrint += strippedString.length();
        }

        //because the printer can know how many more chars and lines it has left we can probably
        //use totalCharToPrint to see if the receipt is even printable
        //assuming it is then move on to the rest
        try {
            if(hardwarePrinter.inkRemaining() < totalCharsToPrint){
                SelfCheckout.getInstance().getCurrentSession().getReceiptPrinterHandler().thePrinterIsOutOfInk();
                SelfCheckout.getInstance().getCurrentSession().setState(UserSessionState.PRINTER_NEEDS_REFILL);
            } else if (hardwarePrinter.paperRemaining() < (totalCharsToPrint/60)) {
                SelfCheckout.getInstance().getCurrentSession().getReceiptPrinterHandler().thePrinterIsOutOfPaper();
                SelfCheckout.getInstance().getCurrentSession().setState(UserSessionState.PRINTER_NEEDS_REFILL);
            }
        } catch (UnsupportedOperationException e){
            //this means we have the bronze receipt printer, so we just have to print the receipt and wait until it goes empty
        }

        //loop through the formatted customer transaction
        for (String barcodePriceString : itemizedTransaction) {
            char[] charArray = barcodePriceString.toCharArray();
                for (char c : charArray) {
                    try {
                        hardwarePrinter.print(c);
                    }
                    catch(EmptyDevice empty){
                        //its not possible to tell if its the ink or paper that ran out so set both flags
                        SelfCheckout.getInstance().getCurrentSession().getReceiptPrinterHandler().thePrinterIsOutOfPaper();
                        SelfCheckout.getInstance().getCurrentSession().getReceiptPrinterHandler().thePrinterIsOutOfInk();
                        SelfCheckout.getInstance().getCurrentSession().setState(UserSessionState.PRINTER_NEEDS_REFILL);
                    }
                    catch(OverloadedDevice overload){
                        try {
                            hardwarePrinter.print('\n');
                            hardwarePrinter.print(c);
                        } catch (EmptyDevice | OverloadedDevice e) {
                            throw new RuntimeException(e);//if another error happens here ill be surprised
                        }

                    }
                }
                try{
                    hardwarePrinter.print('\n');//once an item has been printed out fully move to the next line
                } catch (EmptyDevice e) {
                    //newline char doesn't use ink but will throw out of paper
                    SelfCheckout.getInstance().getCurrentSession().getReceiptPrinterHandler().thePrinterIsOutOfPaper();
                    SelfCheckout.getInstance().getCurrentSession().setState(UserSessionState.PRINTER_NEEDS_REFILL);
                } catch (OverloadedDevice e) {
                    //this really should never happen based on what the printer class looks like but
                    System.out.println("something bad happened in print receipt state");
                    throw new RuntimeException(e);
                }

        }

        hardwarePrinter.cutPaper();
        hardwarePrinter.removeReceipt();

        //After receipt printing the use case states the station should return to a ready state
        SelfCheckout.getInstance().getCurrentSession().setState(UserSessionState.READY_FOR_ITEM);
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

    @Override
    public UserSessionState onPrinterRefilled() {
        return null;
    }
}
