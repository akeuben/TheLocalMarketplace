package com.thelocalmarketplace.software.session;
import com.thelocalmarketplace.software.payment.Transaction;
import com.thelocalmarketplace.software.state.UserSessionState;

import java.math.BigDecimal;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodeScannerListener;
import com.jjjwelectronics.scanner.IBarcodeScanner;
import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.coin.CoinValidator;
import com.tdc.coin.CoinValidatorObserver;

public class UserSession {

    private UserSessionState state;
    private Transaction transaction;

    private CoinValidatorHandler coinValidatorHandler;
    
    /**
     * Create a user session. This holds all data pertaining
     * to the user during a transaction at a self checkout machine.
     */
    public UserSession() {
    	this.transaction = new Transaction(); 
    	setState(UserSessionState.READY_FOR_ITEM);
    	
    	// Initialize the event handlers
    	this.coinValidatorHandler = new CoinValidatorHandler(this);
    }
    
    /**
     * Set the state to a new value
     * @param newState The new state to set
     */
    public void setState(UserSessionState newState) {
    	if(newState == this.state) return;
    	
    	// Send relevant events and update the state field.
    	this.state.onStateUnset();
    	this.state = newState;
    	this.state.onStateSet();
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
     * @return
     */
    public CoinValidatorHandler getCoinValidatorHandler() {
    	return this.coinValidatorHandler;
    }
}
