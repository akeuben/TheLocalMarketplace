package com.thelocalmarketplace.software.test.state;

import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

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

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.software.SelfCheckoutConfiguration;
import com.thelocalmarketplace.software.Software;
import com.thelocalmarketplace.software.session.UserSession;
import com.thelocalmarketplace.software.state.PrintReceiptState;
import com.thelocalmarketplace.software.state.UserSessionState;
import com.thelocalmarketplace.software.test.stubs.TestableAttendantStation;
import com.thelocalmarketplace.software.test.stubs.TestableSelfCheckoutStationGold;

import powerutility.PowerGrid;


public class PrintReceiptStateTest {
    private PrintReceiptState state;
    private UserSession session;
    private Barcode tempBarcode;
    private Mass tempMass;


    @Before
    public void setUp() {
		PowerGrid.engageUninterruptiblePowerSource();
		Software.uninitialize();
		Software.initialize(new SelfCheckoutConfiguration(TestableSelfCheckoutStationGold.class, TestableAttendantStation.class), 1);
		session = Software.getInstance().startNewSession(0);
		state = new PrintReceiptState();
    }

    @Test
    public void testOnStateSet() {
	    state.onStateSet(session);
    }
    
    @Test
    public void testOnStateUnset() {
        state.onStateUnset(session);
        // Verify no state changes or method calls
    }
    
    @Test
    public void testOnWeightChanged() {
		UserSessionState result = state.onWeightChanged(session, tempMass);
        assertNull(result);
        // Verify no state changes or method calls
    }
    
    @Test
    public void testOnScanBarcode() {
		UserSessionState result = state.onScanBarcode(session, tempBarcode);
        assertNull(result);
        // Verify no state changes or method calls
    }
    
    @Test
    public void testOnCoinInserted() {
        UserSessionState result = state.onCoinInserted(session, BigDecimal.ONE);
        assertNull(result);
        // Verify no state changes or method calls
    }

    @Test
    public void testOnPrinterRefilled() {
        UserSessionState result = state.onPrinterRefilled(session);
        assertNull(result);
        // Verify no state changes or method calls
    }
}
