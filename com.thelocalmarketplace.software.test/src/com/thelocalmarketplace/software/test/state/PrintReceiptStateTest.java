package com.thelocalmarketplace.software.test.state;
import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.OverloadedDevice;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.SelfCheckoutConfiguration;
import com.thelocalmarketplace.software.payment.Transaction;
import com.thelocalmarketplace.software.session.UserSession;
import com.thelocalmarketplace.software.state.PrintReceiptState;
import com.thelocalmarketplace.software.state.UserSessionState;
import com.jjjwelectronics.printer.IReceiptPrinter;
import com.jjjwelectronics.scanner.Barcode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class PrintReceiptStateTest {
	private UserSession session;
     private IReceiptPrinter hardwarePrinter;
     private SelfCheckout selfCheckout;
     private UserSession userSession;
     private Transaction transaction;
    private PrintReceiptState state;
    private Barcode tempBarcode;
    private Barcode tempBarcode1;
    private Mass tempMass;
    private ArrayList<String> itemizedTransaction;
    private int totalCharsToPrint;


    @Before
    public void setUp() {
   
    		SelfCheckout.uninitialize();
    		SelfCheckout.initialize(new SelfCheckoutConfiguration());
    		session = SelfCheckout.getInstance().startNewSession();
    		state = new PrintReceiptState();

    }

    @Test
  public void testOnStateSet() {
    	
    	    state.onStateSet();

    }
    
    @Test
    public void testOnStateUnset() {
        state.onStateUnset();
        // Verify no state changes or method calls
    }
    
    @Test
    public void testOnWeightChanged() {
		UserSessionState result = state.onWeightChanged(tempMass);
        assertNull(result);
        // Verify no state changes or method calls
    }
    
    @Test
    public void testOnScanBarcode() {
		UserSessionState result = state.onScanBarcode(tempBarcode);
        assertNull(result);
        // Verify no state changes or method calls
    }
    
    @Test
    public void testOnCoinInserted() {
        UserSessionState result = state.onCoinInserted(BigDecimal.ONE);
        assertNull(result);
        // Verify no state changes or method calls
    }

    @Test
    public void testOnPrinterRefilled() {
        UserSessionState result = state.onPrinterRefilled();
        assertNull(result);
        // Verify no state changes or method calls
    }
}
