package com.thelocalmarketplace.software;

/**
 * SENG 300 Project - Group 1:
 * 
 * Avery Keuben - 30170731
 * Moiz Siddiqui - 30150291
 * Ammaar Melethil - 30141956
 * Joey Fisher - 30105628
 * Ethan Pangilinan - 30179143
 * Joshua Kraft - 30171525
 * Nathan Vaters - 30121908
 * Max Butcher - 30149202
 * Neeraj Ghansela - 30157473
 * Ansel Sulejmani - 30178521
 * Suleman Basit - 30132816
 * Jacob Boyden - 30193220
 * Cheshta Sharma - 30064538
 * Callum Bates - 30188601
 * Armughan Mustafa - 30154601
 * Connor Ell - 30073291
 * Saif Farag - 30195046
 * Ivan Agalakov - 30172107
 * Samuel Turner - 10064857
 * Stephanie Sevilla - 30176781
 * Winston Wang - ????????
 */

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
}