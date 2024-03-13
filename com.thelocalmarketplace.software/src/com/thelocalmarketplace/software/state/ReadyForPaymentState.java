package com.thelocalmarketplace.software.state;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scanner.Barcode;
import com.tdc.coin.Coin;

public class ReadyForPaymentState implements IUserSessionState<UserSessionState> {

	@Override
	public void onStateSet() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStateUnset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserSessionState onScanBarcode(Barcode product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserSessionState onWeightChanged(Mass mass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserSessionState onCoinInserted(Coin coin) {
		// TODO Auto-generated method stub
		return null;
	}

}
