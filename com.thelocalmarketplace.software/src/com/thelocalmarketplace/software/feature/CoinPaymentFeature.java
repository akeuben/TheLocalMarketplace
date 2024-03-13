package com.thelocalmarketplace.software.feature;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tdc.Sink;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinSlot;
import com.tdc.coin.CoinStorageUnit;
import com.tdc.coin.CoinValidator;
import com.thelocalmarketplace.hardware.CoinTray;
import com.thelocalmarketplace.software.UserSession;

public class CoinPaymentFeature implements SelfCheckoutFeature {
	
	private CoinTray rejectTray;
	private CoinTray overflowTray;
	private CoinSlot coinSlot;
	private CoinValidator coinValidator;
	
	private Map<BigDecimal, Sink<Coin>> coinHolders;
	
	public CoinPaymentFeature(List<BigDecimal> supportedDenomination, Currency currency, int coinStorageCapacity) {
		// Create the coin system
		coinSlot = new CoinSlot();
		coinValidator = new CoinValidator(currency, supportedDenomination);
		
		rejectTray = new CoinTray(coinStorageCapacity);
		overflowTray = new CoinTray(coinStorageCapacity);
		
		coinHolders = new HashMap<>();
		
		for(BigDecimal denomination : supportedDenomination) {
			coinHolders.put(denomination, new CoinStorageUnit(coinStorageCapacity));
		}
		
		coinValidator.setup(rejectTray, coinHolders, overflowTray);
		
		coinSlot.sink = coinValidator;
	}

	@Override
	public void onUserSessionStart(UserSession session) {
		coinValidator.attach(session);
	}
	
	@Override
	public void onUserSessionEnd(UserSession session) {
		coinValidator.detach(session);
	}

	public CoinTray getRejectTray() {
		return rejectTray;
	}

	public CoinSlot getCoinSlot() {
		return coinSlot;
	}

	public CoinValidator getCoinValidator() {
		return coinValidator;
	}

	public Map<BigDecimal, Sink<Coin>> getCoinHolders() {
		return coinHolders;
	}
	
}
