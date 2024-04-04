package com.thelocalmarketplace.software.UI;

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

import com.jjjwelectronics.Item;
import com.thelocalmarketplace.hardware.BarcodedProduct;

public interface UIObserver {
	
	/**
	 * announces when user selects option to add their own bags
	 */
	void addBagSelected(Item bag);
	
	/**
	 * announces when user selects option to remove item from order
	 */
	void removeItemSelected(BarcodedProduct product);
	
	/**
	 * announces when user selects option to skip bagging the current product
	 */
	void skipBaggingSelected(BarcodedProduct product);
	
}
