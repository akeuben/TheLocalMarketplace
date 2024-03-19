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
 * Winston Wang - ????????
 */

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.SelfCheckoutConfiguration;
import com.thelocalmarketplace.software.SelfCheckoutConfiguration.MachineRating;

public class SelfCheckoutTest {
	
	@Before
	public void setup() {
		SelfCheckout.uninitialize();
	}
	
	@Test
	public void testDoubleInitilization() {
		SelfCheckout.initialize(new SelfCheckoutConfiguration());
		assertThrows(RuntimeException.class, () -> SelfCheckout.initialize(new SelfCheckoutConfiguration()));
		SelfCheckout.uninitialize();
	}
	
	@Test
	public void testNullType() {
		assertThrows(NullPointerException.class, () -> SelfCheckout.initialize(null));
		SelfCheckout.uninitialize();
	}
	
	@Test
	public void testDoubleSessions() {
		SelfCheckout check = SelfCheckout.initialize(new SelfCheckoutConfiguration());
		check.startNewSession();
		assertThrows(RuntimeException.class, () -> check.startNewSession());
		SelfCheckout.uninitialize();
	}
	
	@Test
	public void testSessionEnds() {
		SelfCheckout check = SelfCheckout.initialize(new SelfCheckoutConfiguration());
		check.startNewSession();
		assertEquals(check.endCurrentSession(), true);
		SelfCheckout.uninitialize();
	}
	
	@Test
	public void testSessionEndsNull() {
		SelfCheckout check = SelfCheckout.initialize(new SelfCheckoutConfiguration());
		assertEquals(check.endCurrentSession(), false);
		SelfCheckout.uninitialize();
	}

	@Test
	public void testIfConfigCorrectlyApplied() {
		Currency currencyTest = Currency.getInstance(Locale.CANADA);
		BigDecimal[] coinDenominationsTest = new BigDecimal[]{BigDecimal.valueOf(0.25), BigDecimal.valueOf(1.00)};
		BigDecimal[] banknoteDenominationsTest = new BigDecimal[]{BigDecimal.valueOf(10), BigDecimal.valueOf(20)};
		int StorageCapTest = 250;
		int DispenserCapTest = 50;
		int TrayCapTest = 15;

		SelfCheckoutConfiguration config = new SelfCheckoutConfiguration(
				SelfCheckoutConfiguration.MachineRating.BRONZE,
				currencyTest,
				DispenserCapTest,
				StorageCapTest,
				TrayCapTest,
				coinDenominationsTest,
				banknoteDenominationsTest,
				100,
				100
				);

		SelfCheckout check = SelfCheckout.initialize(config);
		check.startNewSession();

		assertEquals(Currency.getInstance(Locale.CANADA), config.getCurrency());
		assertEquals(50 , config.getCoinDispenserCapacity());
		assertEquals(250, config.getCoinStorageUnitCapacity());
		assertEquals(15 , config.getCoinTrayCapacity());
		assertArrayEquals(new BigDecimal[]{BigDecimal.valueOf(0.25), BigDecimal.valueOf(1.00)}, config.getCoinDenominations());

		SelfCheckout.uninitialize();
	}
  
	@Test
	public void testGetInstanceNoInstance() {
		assertThrows(RuntimeException.class, () -> SelfCheckout.getInstance());
	}
	
}
