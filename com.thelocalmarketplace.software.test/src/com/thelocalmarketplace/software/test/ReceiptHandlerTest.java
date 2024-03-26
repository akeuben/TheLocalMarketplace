package com.thelocalmarketplace.software.test;

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

import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.SelfCheckoutConfiguration;
import com.thelocalmarketplace.software.session.ReceiptPrinterHandler;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ReceiptHandlerTest {
	
	@Before
	public void setUp() {
		SelfCheckout.uninitialize();
		SelfCheckout.initialize(new SelfCheckoutConfiguration());
		SelfCheckout.getInstance().startNewSession();
	}

    @Test
    public void testRefillFlagsSetWhenNeitherFlagSet() {
        ReceiptPrinterHandler printerHandler = new ReceiptPrinterHandler(null);
        assertFalse(printerHandler.refillFlagsSet());
    }

    @Test
    public void testRefillFlagsSetWhenInkFlagSet() {
        ReceiptPrinterHandler printerHandler = new ReceiptPrinterHandler(null);
        printerHandler.thePrinterIsOutOfInk();
        assertTrue(printerHandler.refillFlagsSet());
    }

    @Test
    public void testRefillFlagsSetWhenPaperFlagSet() {
        ReceiptPrinterHandler printerHandler = new ReceiptPrinterHandler(null);
        printerHandler.thePrinterIsOutOfPaper();
        assertTrue(printerHandler.refillFlagsSet());
    }

    @Test
    public void testRefillFlagsSetWhenBothFlagsSet() {
        ReceiptPrinterHandler printerHandler = new ReceiptPrinterHandler(null);
        printerHandler.thePrinterIsOutOfPaper();
        printerHandler.thePrinterIsOutOfInk();
        assertTrue(printerHandler.refillFlagsSet());
    }

    @Test
    public void testRefillFlagsSetWhenFlagsResetAfterRefill() {
        ReceiptPrinterHandler printerHandler = new ReceiptPrinterHandler(null);
        printerHandler.thePrinterIsOutOfPaper();
        printerHandler.thePrinterIsOutOfInk();
        assertTrue(printerHandler.refillFlagsSet());
        printerHandler.paperHasBeenAddedToThePrinter();
        assertTrue(printerHandler.refillFlagsSet());
        printerHandler.inkHasBeenAddedToThePrinter();
        assertFalse(printerHandler.refillFlagsSet());
    }
}
