package com.thelocalmarketplace.software.state;

import java.math.BigDecimal;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scanner.Barcode;

public interface IUserSessionState<T> {
	T onStateSet();
	void onStateUnset();
	
	T onScanBarcode(Barcode barcode);
	T onWeightChanged(Mass mass);
	T onCoinInserted(BigDecimal value);
}
