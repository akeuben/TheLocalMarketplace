package com.thelocalmarketplace.software;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import com.thelocalmarketplace.software.feature.CoinPaymentFeature;
import com.thelocalmarketplace.software.feature.ElectronicScaleFeature;
import com.thelocalmarketplace.software.feature.LaserScannerFeature;
import com.thelocalmarketplace.software.feature.SelfCheckoutFeature;

public enum SelfCheckoutType {
	BASIC_CANADA(
		new LaserScannerFeature(),
		new ElectronicScaleFeature(),
		new CoinPaymentFeature(Arrays.asList(
			BigDecimal.valueOf(0.05),
			BigDecimal.valueOf(0.1),
			BigDecimal.valueOf(0.25),
			BigDecimal.valueOf(1),
			BigDecimal.valueOf(2)
		), Currency.getInstance(Locale.CANADA), 1000)
	);
	
	private List<SelfCheckoutFeature> features;
	
	private SelfCheckoutType(SelfCheckoutFeature... features) {
		this.features = Arrays.asList(features);
	}
	
	public List<SelfCheckoutFeature> getSupportedFeatures() {
		return this.features;
	}
}
