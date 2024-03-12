package com.thelocalmarketplace.software;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;
import java.util.Map;

import com.jjjwelectronics.scale.ElectronicScale;
import com.jjjwelectronics.scanner.BarcodeScanner;
import com.tdc.Sink;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinSlot;
import com.tdc.coin.CoinStorageUnit;
import com.tdc.coin.CoinValidator;
import com.thelocalmarketplace.hardware.CoinTray;

public class SelfCheckout {
	private static SelfCheckout instance;
	
	private UserSession currentSession;
	
	private BarcodeScanner barcodeScanner;
	private ElectronicScale electronicScale;
	
	private CoinSlot coinSlot;
	private CoinValidator coinValidator;
	
	private Sink<Coin> nickelSink;
	private Sink<Coin> dimeSink;
	private Sink<Coin> quarterSink;
	private Sink<Coin> loonieSink;
	private Sink<Coin> toonieSink;
	private Sink<Coin> rejectSink;
	
	private SelfCheckout() {
		barcodeScanner = new BarcodeScanner();
		electronicScale = new ElectronicScale();
		coinSlot = new CoinSlot();
		coinValidator = new CoinValidator(
				Currency.getInstance(Locale.CANADA), 
				Arrays.asList(
						BigDecimal.valueOf(0.05),
						BigDecimal.valueOf(0.10),
						BigDecimal.valueOf(0.25),
						BigDecimal.valueOf(1),
						BigDecimal.valueOf(2)));

		nickelSink = new CoinStorageUnit(1000);
		dimeSink = new CoinStorageUnit(1000);
		quarterSink = new CoinStorageUnit(1000);
		loonieSink = new CoinStorageUnit(1000);
		toonieSink = new CoinStorageUnit(1000);
		rejectSink = new CoinTray(1000);
		
		coinValidator.setup(rejectSink, Map.of(
				BigDecimal.valueOf(0.05), nickelSink,
				BigDecimal.valueOf(0.10), dimeSink,
				BigDecimal.valueOf(0.25), quarterSink,
				BigDecimal.valueOf(1), loonieSink,
				BigDecimal.valueOf(2), toonieSink
		), rejectSink);
		
		coinSlot.sink = coinValidator;
		
		currentSession = null;
	}
	
	public static SelfCheckout getInstance() {
		if(instance == null) {
			instance = new SelfCheckout();
		}
		return instance;
	}
	
	/**
	 * Returns the current user session, or null if
	 * there is no current session
	 */
	public UserSession getCurrentSession() {
		return currentSession;
	}
	
	/**
	 * Starts a new user session
	 * @return The user session that was started
	 * @throws RuntimeException If there is already a session in progress
	 */
	public UserSession startNewSession() throws RuntimeException {
		if(currentSession != null) {
			throw new RuntimeException("There is already an active user session.");
		}
		currentSession = new UserSession();
		return currentSession;
	}
	
	/**
	 * Ends the current session
	 */
	public void endCurrentSession() {
		
	}
	
}
