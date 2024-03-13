package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.SelfCheckoutType;

public class SelfCheckoutTest {
	
	@Before
	public void setup() {
		
	}
	
	@Test
	public void testDoubleInitilization() {
		SelfCheckout.initialize(SelfCheckoutType.BASIC_CANADA);
		assertThrows(RuntimeException.class, () -> SelfCheckout.initialize(SelfCheckoutType.BASIC_CANADA));
		SelfCheckout.unInitialize();
	}
	
	@Test
	public void testNullType() {
		assertThrows(NullPointerException.class, () -> SelfCheckout.initialize(null));
		SelfCheckout.unInitialize();
	}
	
	@Test
	public void testDoubleSessions() {
		SelfCheckout check = SelfCheckout.initialize(SelfCheckoutType.BASIC_CANADA);
		check.startNewSession();
		assertThrows(RuntimeException.class, () -> check.startNewSession());
		SelfCheckout.unInitialize();
	}
	
	@Test
	public void testSessionEnds() {
		SelfCheckout check = SelfCheckout.initialize(SelfCheckoutType.BASIC_CANADA);
		check.startNewSession();
		assertEquals(check.endCurrentSession(), true);
		SelfCheckout.unInitialize();
	}
	
	@Test
	public void testSessionEndsNull() {
		SelfCheckout check = SelfCheckout.initialize(SelfCheckoutType.BASIC_CANADA);
		assertEquals(check.endCurrentSession(), false);
		SelfCheckout.unInitialize();
	}
}
