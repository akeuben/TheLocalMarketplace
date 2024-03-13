package com.thelocalmarketplace.software.feature;

import com.jjjwelectronics.scanner.BarcodeScanner;
import com.thelocalmarketplace.software.UserSession;

public class LaserScannerFeature implements SelfCheckoutFeature {
	
	BarcodeScanner scanner;
	
	public LaserScannerFeature() {
		scanner = new BarcodeScanner();
	}

	@Override
	public void onUserSessionStart(UserSession session) {
		scanner.register(session);
	}

	@Override
	public void onUserSessionEnd(UserSession session) {
		scanner.deregister(session);
	}

}
