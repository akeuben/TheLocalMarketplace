package com.thelocalmarketplace.software.state;

public interface IUserSessionStateActions<T> {
	T addItem();
	T weightChanged();
	T paymentAdded();
}
