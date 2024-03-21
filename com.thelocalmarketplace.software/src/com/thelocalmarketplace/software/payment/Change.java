package com.thelocalmarketplace.software.payment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Map;

import com.tdc.*;
import com.tdc.banknote.AbstractBanknoteDispenser;
import com.tdc.coin.AbstractCoinDispenser;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import ca.ucalgary.seng300.simulation.SimulationException; 

public class Change {
	/* Global variables */
//	private boolean banknotePause = false;
//	private boolean debug = false;
//	private boolean coinPause = false;
//	private AbstractSelfCheckoutStation station;
	private ArrayList<BigDecimal> coinsToReturn = new ArrayList<BigDecimal>();
	private ArrayList<Integer> banknotesToReturn = new ArrayList<Integer>();
	private Map<BigDecimal, AbstractCoinDispenser> coinDispensers;
	private Map<Integer, AbstractBanknoteDispenser> banknoteDispensers;
	private int[] banknoteDenominations;
	private List<BigDecimal> coinDenominations;
	

	public BigDecimal returnChange(Currency currency) {
		
		
		
		return BigDecimal.ZERO;
		
	}
	
	public void calculateChange(double totalChange) throws OverloadException{
		
	}
}
