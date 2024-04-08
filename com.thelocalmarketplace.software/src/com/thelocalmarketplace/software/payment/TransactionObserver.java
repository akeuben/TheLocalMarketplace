package com.thelocalmarketplace.software.payment;

import com.jjjwelectronics.Mass;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedProduct;

public interface TransactionObserver {
	void barcodedProductAdded(BarcodedProduct product);
	void barcodedProductRemoved(BarcodedProduct product);

	void plucodedProductAdded(PLUCodedProduct product, Mass weight);
	void plucodedProductRemoved(PLUCodedProduct product, Mass weight);
	
	void paymentAdded(IPayment payment);	
	void bagAdded(Mass bagMass);
}
