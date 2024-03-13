package com.thelocalmarketplace.software.state;

import java.math.BigDecimal;

import com.jjjwelectronics.Mass;
import com.thelocalmarketplace.hardware.Product;

public interface IUserSessionStateActions<T> {
	T addItem(Product product);
	T weightChanged(Mass mass);
	T paymentAdded(BigDecimal amount, PaymentSource source);
}
