package com.thelocalmarketplace.software.state;

import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.printer.IReceiptPrinter;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.payment.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;

public class FullPaymentReceivedState implements IUserSessionState<UserSessionState> {

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
            if(hardwarePrinter.inkRemaining() < totalCharsToPrint | hardwarePrinter.paperRemaining() < (totalCharsToPrint/60) ){
                //here prob just jump to notifying the attendant station because this receipt wont be able to fully print
            }
        } catch (UnsupportedOperationException e){
            //this means we have the bronze receipt printer so we just have to print the receipt and wait until it goes empty
        }


        for (String barcodePriceString : itemizedTransaction) {
            char[] charArray = barcodePriceString.toCharArray();
            try {
                for (char c : charArray) {
                    hardwarePrinter.print(c);
                }
                hardwarePrinter.print('\n');
            }
            catch(EmptyDevice empty){
                //do something if the ink or paper runs out (notfiy attendant station somehow)
            }
            catch(OverloadedDevice overload){
                //60 characters on the line have been exceeded so figure out what we want to do when that happens?
            }
        }

        hardwarePrinter.cutPaper();
        hardwarePrinter.removeReceipt();
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
