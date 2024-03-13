package com.thelocalmarketplace.software;

import java.util.List;
import java.util.Map;

import com.thelocalmarketplace.software.feature.SelfCheckoutFeature;

public class SelfCheckout {
	
	private static SelfCheckout instance;
	
	private UserSession currentSession;
	
	private Map<Class<? extends SelfCheckoutFeature>, SelfCheckoutFeature> features;
	
	private SelfCheckout(List<SelfCheckoutFeature> features) {
		// Add the specified features.
		for(SelfCheckoutFeature feature : features) {
			this.features.put(feature.getClass(), feature);
		}
		currentSession = null;
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
	public static SelfCheckout initialize(SelfCheckoutType type) throws RuntimeException {
		if(instance == null) throw new RuntimeException("There is already a self checkout initialized!");
		instance = new SelfCheckout(type.getSupportedFeatures());
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
		for(SelfCheckoutFeature feature : this.features.values()) {
			feature.onUserSessionStart(currentSession);
		}
		return currentSession;
	}
	
	/**
	 * Ends the current session
	 * @return true, if a session was ended. false, if there was
	 * no active session
	 */
	public boolean endCurrentSession() {
		if(currentSession == null) return false;
		for(SelfCheckoutFeature feature : this.features.values()) {
			feature.onUserSessionEnd(currentSession);
		}
		currentSession = null;
		return true;
	}
	
	/**
	 * Checks whether the specified feature is supported for this self checkout machine
	 * @param feature The feature to test for
	 * @return True if the self checkout supports this feature, false otherwise.
	 */
	public boolean supportsFeature(Class<? extends SelfCheckoutFeature> feature) {
		return this.features.containsKey(feature);
	}
	
}
