package com.thelocalmarketplace.software;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

public class SelfCheckoutConfiguration {
	
	public Currency currency;
	public BigDecimal[] coinDenominations;
	
	public int coinDispenserCapacity;
	public int coinStorageUnitCapacity;
	public int coinTrayCapacity;
	
	public SelfCheckoutConfiguration(Currency currency, int coinDispenserCapacity, int coinStorageUnitCapacity, int coinTrayCapacity, BigDecimal... coinDenominations) {
		this.coinDenominations = coinDenominations;
		this.currency = currency;
		this.coinDispenserCapacity = coinDispenserCapacity;
		this.coinStorageUnitCapacity = coinStorageUnitCapacity;
		this.coinTrayCapacity = coinTrayCapacity;
	}
	
	public SelfCheckoutConfiguration() {
		this(Currency.getInstance(Locale.CANADA), 100, 1000, 25, BigDecimal.ONE);
	}
}

	public Currency getCurrency() {
		return currency;
	}

	public int getCoinDispenserCapacity() {
		return coinDispenserCapacity;
	}

	public int getCoinStorageUnitCapacity() {
		return coinStorageUnitCapacity;
	}

	public int getCoinTrayCapacity() {
		return coinTrayCapacity;
	}

	public BigDecimal[] getCoinDenominations() {
		return coinDenominations.clone();
	}