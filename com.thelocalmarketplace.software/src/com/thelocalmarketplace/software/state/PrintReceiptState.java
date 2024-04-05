package com.thelocalmarketplace.software.state;

import java.util.ArrayList;

/**
 * SENG 300 Project - Group 1:
 *
 * Avery Keuben - 30170731
 * Moiz Siddiqui - 30150291
 * Ammaar Melethil - 30141956
 * Joey Fisher - 30105628
 * Ethan Pangilinan - 30179143
 * Joshua Kraft - 30171525
 * Nathan Vaters - 30121908
 * Max Butcher - 30149202
 * Neeraj Ghansela - 30157473
 * Ansel Sulejmani - 30178521
 * Suleman Basit - 30132816
 * Jacob Boyden - 30193220
 * Cheshta Sharma - 30064538
 * Callum Bates - 30188601
 * Armughan Mustafa - 30154601
 * Connor Ell - 30073291
 * Saif Farag - 30195046
 * Ivan Agalakov - 30172107
 * Samuel Turner - 10064857
 * Stephanie Sevilla - 30176781
 * Winston Wang - 30185321
 */

import com.thelocalmarketplace.software.session.UserSession;
import com.thelocalmarketplace.software.Software;
import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.printer.IReceiptPrinter;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.software.payment.Transaction;


public class PrintReceiptState implements IUserSessionState<UserSessionState> {

    private Transaction finalTransactionRecord;
    private ArrayList<String> itemizedTransaction;
    private IReceiptPrinter hardwarePrinter;

    /**
     * Since the receipt to be printed as soon as a full payment has been made
     * The onStateSetMethod should be doing most of the work for this state
     */
    @Override
    public UserSessionState onStateSet(UserSession session) {
        hardwarePrinter = session.getHardware().getPrinter();
        finalTransactionRecord = session.getTransaction();//prob add a null check just incase
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
                session.getReceiptPrinterHandler().thePrinterIsOutOfInk();
                session.setState(UserSessionState.PRINTER_NEEDS_REFILL);
            } else if (hardwarePrinter.paperRemaining() < (totalCharsToPrint/60)) {
                session.getReceiptPrinterHandler().thePrinterIsOutOfPaper();
                session.setState(UserSessionState.PRINTER_NEEDS_REFILL);
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
                        session.getReceiptPrinterHandler().thePrinterIsOutOfPaper();
                        session.getReceiptPrinterHandler().thePrinterIsOutOfInk();
                        session.setState(UserSessionState.PRINTER_NEEDS_REFILL);
                    }
                    catch(OverloadedDevice overload){
                        try {
                            hardwarePrinter.print('\n');
                            hardwarePrinter.print(c);
                        } catch (OverloadedDevice e) {
                            throw new RuntimeException(e);//if another error happens here ill be surprised
                        }
                        catch(EmptyDevice empty){
                            //its not possible to tell if its the ink or paper that ran out so set both flags
                            session.getReceiptPrinterHandler().thePrinterIsOutOfPaper();
                            session.getReceiptPrinterHandler().thePrinterIsOutOfInk();
                            session.setState(UserSessionState.PRINTER_NEEDS_REFILL);
                        }

                    }
                }
                try{
                    hardwarePrinter.print('\n');//once an item has been printed out fully move to the next line
                } catch (EmptyDevice e) {
                    //newline char doesn't use ink but will throw out of paper
                    session.getReceiptPrinterHandler().thePrinterIsOutOfPaper();
                    session.setState(UserSessionState.PRINTER_NEEDS_REFILL);
                } catch (OverloadedDevice e) {
                    //this really should never happen based on what the printer class looks like but
                    System.out.println("something bad happened in print receipt state");
                    throw new RuntimeException(e);
                }

        }

        //After receipt printing the use case states the station should return to a ready state
        hardwarePrinter.cutPaper();
        hardwarePrinter.removeReceipt();
        Software.getInstance().endCurrentSession(session.getMachineID());
        return null;
    }
}
