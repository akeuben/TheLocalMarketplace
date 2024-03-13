package com.thelocalmarketplace.software.state;

import java.math.BigDecimal;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scanner.Barcode;
import com.tdc.coin.Coin;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.software.feature.ElectronicScaleFeature;
import com.thelocalmarketplace.software.payment.Transaction;

import java.util.*; 
public class ReadyForItemState implements IUserSessionState<UserSessionState> {

	@Override
	public void onStateSet() {
		// do nothing, don't need to set anything
		
	}

	@Override
	public void onStateUnset() throws RuntimeException{
		
	}

	@Override
	public UserSessionState onScanBarcode(Barcode barcode) {
		Product barcodeProduct = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode);
		Transaction currentTransaction = SelfCheckout.getInstance().getCurrentSession().getTransaction();
		currentTransaction.addItem(barcodeProduct);
		if(SelfCheckout.getInstance().supportsFeature(ElectronicScaleFeature.class)){
			return UserSessionState.WAITING_FOR_BAGGING;	
		}
		else {
			return null; 
		}
	}

	@Override
	public UserSessionState onWeightChanged(Mass mass) {
		
		return null;
	}

	@Override
	public UserSessionState onCoinInserted(BigDecimal value) {
		// TODO Auto-generated method stub
		return null;
	}

}
