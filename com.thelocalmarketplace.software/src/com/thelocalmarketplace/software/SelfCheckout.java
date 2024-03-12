package com.thelocalmarketplace.software;

public class SelfCheckout {
	private static SelfCheckout instance = new SelfCheckout();
	private UserSession currentSession;


	public static SelfCheckout getSelfCheckoutInstance(){ return instance; }
}
