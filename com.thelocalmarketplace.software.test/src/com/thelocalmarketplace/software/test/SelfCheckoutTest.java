package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.SelfCheckoutConfiguration;

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
	public void testGetInstanceNoInstance() {
		assertThrows(RuntimeException.class, () -> SelfCheckout.getInstance());
	}
	
}
