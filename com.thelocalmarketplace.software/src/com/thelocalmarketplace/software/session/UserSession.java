package com.thelocalmarketplace.software.session;
import com.thelocalmarketplace.software.payment.Transaction;
import com.thelocalmarketplace.software.state.UserSessionState;

public class UserSession {

    private UserSessionState state;
    private Transaction transaction;

    private CoinValidatorHandler coinValidatorHandler;
    private BarcodeHandler barcodeHandler;
    private ElectronicScaleHandler electronicScaleHandler;
    
    /**
     * Create a user session. This holds all data pertaining
     * to the user during a transaction at a self checkout machine.
     */
    public UserSession() {
    	this.transaction = new Transaction(); 
    	setState(UserSessionState.READY_FOR_ITEM);
    	
    	// Initialize the event handlers
    	this.coinValidatorHandler = new CoinValidatorHandler(this);
    	this.barcodeHandler = new BarcodeHandler(this);
    	this.electronicScaleHandler = new ElectronicScaleHandler(this);
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
     * @return The Coin ValidatorObserver
     */
    public CoinValidatorHandler getCoinValidatorHandler() {
    	return this.coinValidatorHandler;
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
}
