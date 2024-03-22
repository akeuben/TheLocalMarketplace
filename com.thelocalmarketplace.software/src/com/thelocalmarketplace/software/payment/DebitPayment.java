package com.thelocalmarketplace.software.payment;

import java.io.IOException;
import java.math.BigDecimal;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardData;
import com.jjjwelectronics.card.CardReaderListener;
import com.thelocalmarketplace.hardware.external.CardIssuer;
import com.thelocalmarketplace.software.SelfCheckout;
import java.util.Scanner; 

public class DebitPayment extends AbstractCardPayment implements CardReaderListener{

	private BigDecimal amountDue;

	// TODO signature verification, still not sure on this one 
	// TODO how to differentiate between debit and credit, seems there is literally no discernable way
	// for now going to write up some tests with what we have
	
	public DebitPayment() {
		super(BigDecimal.ZERO);
		
	}

	/**
	 * Will attempt to post a transaction using a debit card via swiping
	 * @return result of transaction, true if successful, false if not
	 */
	
	
	public boolean swipePayment(CardData data) {
		
		
		
		this.amountDue = SelfCheckout.getInstance().getCurrentSession().getTransaction().getTotalCost();
		// check to see if the bank that corresponds to the card's type exists 
		if(BankDataBase.getInstance().getDataBase().containsKey(data.getType().toLowerCase())) {
			
				CardIssuer bank = BankDataBase.getInstance().getDataBase().get(data.getType().toLowerCase());
			
			
				long blockNum = bank.authorizeHold(data.getNumber(), this.amountDue.doubleValue());
				if (blockNum != -1) {
					// if the hold is successful then post the transaction
					boolean posted = bank.postTransaction(data.getNumber(), blockNum, this.amountDue.doubleValue());
						// Whether transaction is valid or not release the hold
						bank.releaseHold(data.getNumber(), blockNum);
						// transaction was successful so update the amount that was paid
						setAmountPaid(this.amountDue);
					// once that is all done then return the result of the transaction being posted
					return posted;

				}	

		}
		// if the bank doesn't exist then simply return false
		return false;
	}

	@Override
	public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {
		

	}

	@Override
	public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {

	}

	@Override
	public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {
		

	}

	@Override
	public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {
		

	}

	@Override
	public void aCardHasBeenSwiped() {
		

	}

	@Override
	public void theDataFromACardHasBeenRead(CardData data) {

	}

}
