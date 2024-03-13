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
	}
	
	@Test
	public void testNullType() {
		assertThrows(NullPointerException.class, () -> SelfCheckout.initialize(null));
	}
	
	@Test
	public void testDoubleSessions() {
		SelfCheckout check = SelfCheckout.initialize(SelfCheckoutType.BASIC_CANADA);
		check.startNewSession();
		assertThrows(RuntimeException.class, () -> check.startNewSession());
	}
	
	@Test
	public void testSessionEnds() {
		SelfCheckout check = SelfCheckout.initialize(SelfCheckoutType.BASIC_CANADA);
		check.startNewSession();
		assertEquals(check.endCurrentSession(), true);
	}
	
	@Test
	public void testSessionEndsNull() {
		SelfCheckout check = SelfCheckout.initialize(SelfCheckoutType.BASIC_CANADA);
		assertEquals(check.endCurrentSession(), false);
	}
}
