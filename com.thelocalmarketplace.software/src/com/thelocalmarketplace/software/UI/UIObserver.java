package com.thelocalmarketplace.software.UI;

public interface UIObserver {
	
	/**
	 * announces when user selects option to add their own bags
	 */
	void addBag();
	
	/**
	 * announces when user selects option to remove item from order
	 */
	void removeItem();
	
	/**
	 * announces when user selects option to add bulky item to order
	 */
	void bulkyItem();

}
