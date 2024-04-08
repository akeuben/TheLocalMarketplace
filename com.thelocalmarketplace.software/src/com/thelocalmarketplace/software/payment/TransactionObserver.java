package com.thelocalmarketplace.software.payment;

import com.jjjwelectronics.Mass;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.software.payment.Transaction.PLUCodedProductAdded;

public interface TransactionObserver {
	void barcodedProductAdded(BarcodedProduct product);
	void barcodedProductRemoved(BarcodedProduct product);

	void plucodedProductAdded(PLUCodedProductAdded product, Mass weight);
	void plucodedProductRemoved(PLUCodedProductAdded product, Mass weight);
	
	void paymentAdded(IPayment payment);	
	void bagAdded(Mass bagMass);
}
