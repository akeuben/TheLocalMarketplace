package com.thelocalmarketplace.software.state;

import java.math.BigDecimal;

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
	public UserSessionState onScanBarcode(Barcode barcode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserSessionState onWeightChanged(Mass mass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserSessionState onCoinInserted(BigDecimal value) {
		// TODO Auto-generated method stub
		return null;
	}

}
