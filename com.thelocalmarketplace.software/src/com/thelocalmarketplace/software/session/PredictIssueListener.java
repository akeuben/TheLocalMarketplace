package com.thelocalmarketplace.software.session;

public interface PredictIssueListener {
 
	/**
	 * Signals an event that a coins full issue will shortly occur
	 */
	void notifyPredictCoinsFull(UserSession session);
	
	
	/**
	 * Signals an event that a bank notes full issue will shortly occur
	 */
	void notifyPredictBanknotesFull(UserSession session);
	
	/**
	 * Signals an event that a low coins issue will shortly occur
	 */
	void notifyPredictLowCoins(UserSession session);
	
	/**
	 * Signals an event that a low bank notes issue will shortly occur
	 */
	void notifyPredictLowBanknotes(UserSession session);
	/**
	 * Signals that no issues are present
	 */
	void notifyNoIssues(UserSession session);
	
	

}
