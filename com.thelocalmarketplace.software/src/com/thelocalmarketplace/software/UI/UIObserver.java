package com.thelocalmarketplace.software.UI;

public interface UIObserver {
	
	/**
	 * announces when user selects option to add their own bags
	 */
	void addBagSelected();
	
	/**
	 * announces when user selects option to remove item from order
	 */
	void removeItemSelected(Product product);
	
	/**
	 * announces when user selects option to add bulky item to order
	 */
	void bulkyItemSelected(Product product);

}
