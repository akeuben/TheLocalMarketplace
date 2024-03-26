package com.thelocalmarketplace.software.session;

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
 * Winston Wang - 30185321
 */

import com.thelocalmarketplace.software.payment.Transaction;
import com.thelocalmarketplace.software.state.UserSessionState;

public class UserSession {

    private UserSessionState state = null;
    private Transaction transaction;

    private CoinValidatorHandler coinValidatorHandler;
    private BanknoteValidatorHandler banknoteValidatorHandler;
    private BarcodeHandler barcodeHandler;
    private ElectronicScaleHandler electronicScaleHandler;
    private ReceiptPrinterHandler receiptPrinterHandler;
    private UIHandler uiHandler;
    private CardReaderHandler cardReaderHandler;
    
    /**
     * Create a user session. This holds all data pertaining
     * to the user during a transaction at a self checkout machine.
     */
    public UserSession() {
    	this.transaction = new Transaction();
		
    	// Initialize the event handlers
    	this.coinValidatorHandler = new CoinValidatorHandler(this);
    	this.banknoteValidatorHandler = new BanknoteValidatorHandler(this);
    	this.barcodeHandler = new BarcodeHandler(this);
    	this.electronicScaleHandler = new ElectronicScaleHandler(this);
		this.receiptPrinterHandler = new ReceiptPrinterHandler(this);
    	this.uiHandler = new UIHandler(this);
    	this.cardReaderHandler = new CardReaderHandler(this);
    }
    
    /**
     * Set the state to a new value
     * @param newState The new state to set
     */
    public void setState(UserSessionState newState) {
		if(newState == this.state) return;
    	
    	// Send relevant events and update the state field.
    	if(this.state != null) this.state.onStateUnset();
    	this.state = newState;
    	newState = this.state.onStateSet();
    	if(newState != null) {
    		setState(newState);
    	}
    }
    
    /**
     * Get the state
     * @return The state
     */
    public UserSessionState getState() {
		return state;
	}

	/**
     * Get the transaction related to this state
     * @return The transaction related to this state
     */
    public Transaction getTransaction() {
    	return this.transaction;
    } 
    
    /**
     * Get the CoinValidatorObserver for the current session
     * @return The Coin ValidatorObserver
     */
    public CoinValidatorHandler getCoinValidatorHandler() {
    	return this.coinValidatorHandler;
    }
    
    /**
     * Get the BanknoteValidatorObserver for the current session
     * @return The Banknote ValidatorObserver
     */
    public BanknoteValidatorHandler getBanknoteValidatorHandler() {
    	return this.banknoteValidatorHandler;
    }
    
    /**
     * Get the BarcodeScannerListener for the current session
     * @return The BarcodeScannerListener
     */
	public BarcodeHandler getBarcodeHandler() {
		return barcodeHandler;
	}

	/**
	 * Get the ElectronicScaleListener for the current session
	 * @return The ElectronicScaleListener
	 */
	public ElectronicScaleHandler getElectronicScaleHandler() {
		return electronicScaleHandler;
	}

	/**
	 * Get the ReceiptPrinterHandler for the current session
	 * @return Current ReceiptPrinterHandler
	 */
	public ReceiptPrinterHandler getReceiptPrinterHandler() { return receiptPrinterHandler; }
	
	/**
	 * Get the UIListener for the current session
	 * @return The UIListener
	 */
	public UIHandler getUIHandler() {
		return uiHandler;
	}
	
	/*
	 * Get the CardReaderListener for the current session
	 * @return The CardReaderHandlerListener
	 */
	public CardReaderHandler getCardReaderHandler() {
		return this.cardReaderHandler; 
	}
}
