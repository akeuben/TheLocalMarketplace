package com.thelocalmarketplace.software.state;

public interface IStateChangedObserver {
	void onStateSet();
	void onStateUnset();
}
