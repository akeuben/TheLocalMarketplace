package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.software.attendant.EnableStation;
import com.thelocalmarketplace.software.payment.CashPayment;

import powerutility.PowerGrid;

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

public class EnableStationTest {
	@Before
    public void setup() {


    }
	
    @Test
    public void testNullStation() {
    	AbstractSelfCheckoutStation station = null;
    	assertThrows(NullPointerException.class, () -> new EnableStation(station));
    }
    
    @Test
    public void testStationOn() {
    	AbstractSelfCheckoutStation station = new SelfCheckoutStationBronze();
    	station.plugIn(PowerGrid.instance());
    	EnableStation en = new EnableStation(station);
    	boolean exceptionThrown = false;
    	try {
    		en.enable();
    	} catch (Exception e) {
    		exceptionThrown = true;
    		e.printStackTrace();
    	}
    	assertFalse("An unhandled exception was thrown", exceptionThrown);
    }
}
