package com.thelocalmarketplace.software;

import com.thelocalmarketplace.hardware.SelfCheckoutStation;
import com.thelocalmarketplace.software.session.UserSession;

import powerutility.PowerGrid;

public class SelfCheckout {
	
	private static SelfCheckout instance;
	
	private UserSession currentSession;
	
	private SelfCheckoutStation hardware;
	
	private SelfCheckout() {
		currentSession = null;
		
		hardware = new SelfCheckoutStation();
		PowerGrid.engageUninterruptiblePowerSource();
		hardware.plugIn(PowerGrid.instance());
		hardware.turnOn();
	}
	
	/**
	 * Get the instance of the self checkout
	 * @return The instance of the self checkout
	 * @throws RuntimeException if there is no current instance
	 */
	public static SelfCheckout getInstance() throws RuntimeException {
		if(instance == null) {
			throw new RuntimeException("The self checkout machine has not been initialized yet.");
		}
		return instance;
	}
	
	/**
	 * Initializes the self checkout machine
	 * @param type The type of machine
	 * @return The instance of the self checkout
	 * @throws RuntimeException If there is already a self checkout instance
	 */
	public static SelfCheckout initialize(SelfCheckoutConfiguration configuration) throws RuntimeException {
		if(instance != null) throw new RuntimeException("There is already a self checkout initialized!");
		
		instance = new SelfCheckout();
		
		// Initialize the hardware
		SelfCheckoutStation.configureCurrency(configuration.currency);
		SelfCheckoutStation.configureCoinDenominations(configuration.coinDenominations);
		SelfCheckoutStation.configureCoinDispenserCapacity(configuration.coinDispenserCapacity);
		SelfCheckoutStation.configureCoinStorageUnitCapacity(configuration.coinStorageUnitCapacity);
		SelfCheckoutStation.configureCoinTrayCapacity(configuration.coinTrayCapacity);
		
		return instance;
	}
	
	/**
	 * Uninitializes the self checkout machine.
	 */
	public static void uninitialize() {
		if(instance == null) return;
		
		instance.endCurrentSession();
		
		instance = null;
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
		
		// Remove old listeners
		hardware.scanner.deregisterAll();
		hardware.baggingArea.deregisterAll();
		hardware.coinValidator.detachAll();
		
		// Register listeners
		hardware.scanner.register(currentSession.getBarcodeHandler());
		hardware.baggingArea.register(currentSession.getElectronicScaleHandler());
		hardware.coinValidator.attach(currentSession.getCoinValidatorHandler());
		
		return currentSession;
	}
	
	/**
	 * Ends the current session
	 * @return true, if a session was ended. false, if there was
	 * no active session
	 */
	public boolean endCurrentSession() {
		if(currentSession == null) return false;
		
		currentSession = null;
		return true;
	}

	/**
	 * Gets the hardware for the self checkout station.
	 * @return The hardware of the self checkout station.
	 */
	public SelfCheckoutStation getHardware() {
		return hardware;
	}
}
