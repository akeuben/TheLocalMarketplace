package com.thelocalmarketplace.software.state;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scanner.Barcode;
import com.tdc.coin.Coin;

public interface IUserSessionState<T> {
	void onStateSet();
	void onStateUnset();
	
	T onScanBarcode(Barcode product);
	T onWeightChanged(Mass mass);
	T onCoinInserted(Coin coin);
}
