package com.thelocalmarketplace.software;

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

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.hardware.SelfCheckoutStationSilver;

import com.thelocalmarketplace.software.session.UserSession;
import com.thelocalmarketplace.software.state.UserSessionState;

import powerutility.PowerGrid;

public class SelfCheckout {
	
	private static SelfCheckout instance;
	
	private UserSession currentSession;
	
	private AbstractSelfCheckoutStation hardware;
	
	private SelfCheckout(SelfCheckoutConfiguration configuration) {
		currentSession = null;
		
		switch(configuration.rating) {
		case BRONZE:
			hardware = new SelfCheckoutStationBronze();
			break;
		case GOLD:
			hardware = new SelfCheckoutStationGold();
			break;
		case SILVER:
			hardware = new SelfCheckoutStationSilver();
			break;
		default:
			throw new RuntimeException("Invalid configuration");
		}
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
		
		// Initialize the hardware
		AbstractSelfCheckoutStation.configureCurrency(configuration.currency);
		AbstractSelfCheckoutStation.configureBanknoteDenominations(configuration.banknoteDenominations);
		AbstractSelfCheckoutStation.configureBanknoteStorageUnitCapacity(configuration.banknoteStorageCapacity);
		AbstractSelfCheckoutStation.configureReusableBagDispenserCapacity(configuration.reusableBagDispenserCapacity);
		AbstractSelfCheckoutStation.configureCoinDenominations(configuration.coinDenominations);
		AbstractSelfCheckoutStation.configureCoinDispenserCapacity(configuration.coinDispenserCapacity);
		AbstractSelfCheckoutStation.configureCoinStorageUnitCapacity(configuration.coinStorageUnitCapacity);
		AbstractSelfCheckoutStation.configureCoinTrayCapacity(configuration.coinTrayCapacity);
		instance = new SelfCheckout(configuration);
		
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
    	
		currentSession.setState(UserSessionState.READY_FOR_ITEM);
		
		// Remove old listeners
		hardware.mainScanner.deregisterAll();
		hardware.baggingArea.deregisterAll();
		hardware.coinValidator.detachAll();
		
		// Register listeners
		hardware.mainScanner.register(currentSession.getBarcodeHandler());
		hardware.baggingArea.register(currentSession.getElectronicScaleHandler());
		hardware.cardReader.register(currentSession.getCardReaderHandler());
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
	public AbstractSelfCheckoutStation getHardware() {
		return hardware;
	}
}
