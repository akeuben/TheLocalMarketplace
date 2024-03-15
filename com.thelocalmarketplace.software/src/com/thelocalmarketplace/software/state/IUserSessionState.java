package com.thelocalmarketplace.software.state;

import java.math.BigDecimal;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodeScanner;
import com.tdc.coin.Coin;

public interface IUserSessionState<T> {
	/**
	 * Called when the state is set to the current state.
	 * Useful for enabling or disabling hardware for this
	 * particular state.
	 * @return The state to transition to after this function returns, or null to stay on the same state.
	 */
	T onStateSet();
	
	/**
	 * Called when the current state is set to a different
	 * state when it was previously this state. Useful for
	 * cleaning up resources used, or resetting any changed 
	 * values.
	 */
	void onStateUnset();
	
	/**
	 * Called when a {@link Barcode} is scanned by any {@link BarcodeScanner}
	 * on the self checkout station.
	 * @param barcode The {@link Barcode} that was scanned
	 * @return The state to transition to after this function returns, or null to stay on the same state.
	 */
	T onScanBarcode(Barcode barcode);
	
	/**
	 * Called when the {@link Mass} of the bagging area is changed.
	 * @param mass The new total mass on the bagging area scale.
	 * @return The state to transition to after this function returns, or null to stay on the same state.
	 */
	T onWeightChanged(Mass mass);
	
	/**
	 * Called when a {@link Coin} is inserted into the coin slot.
	 * @param value The value of the coin that was inserted.
	 * @return The state to transition to after this function returns, or null to stay on the same state.
	 */
	T onCoinInserted(BigDecimal value);
}
