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

public class DebitPayment implements CardReaderListener, IPayment {

	private Card userCard;
	private CardIssuer bank;
	private BigDecimal amountDue;
	private BigDecimal amountPaid = BigDecimal.valueOf(0);

	
	//TODO understand utilization of listener 
	//TODO configure for the different self checkout types
	
	
	/**
	 * Initializes DebitPayment instance, where the bank the card belongs to, the card itself and type of payment needs to be specified
	 * @param bank The bank that the card belongs to
	 * @param userCard Card of the customer 
	 * @param type Type of payment that is wished to be made by the user (whether swipe, tap etc.)
	 */
	
	public DebitPayment(CardIssuer bank, Card userCard, String type) {
		this.bank = bank;
		this.userCard = userCard;

		if (type.equals("swipe")) {
			// if a swipe payment wants to be made then call the apporopriate method
			boolean success = swipePayment();
			if(success) {
				// in the event the transaction is sucessful set paid amount to the due amount
				this.amountPaid = this.amountDue; 	
			}
			
			
		}
	}

	/**
	 * Will attempt to post a transaction using a debit card via swiping
	 * @return result of transaction, true if successful, false if not
	 */
	
	
	public boolean swipePayment() {
		
		Scanner sc = new Scanner(System.in);
		
		this.amountDue = SelfCheckout.getInstance().getCurrentSession().getTransaction().getTotalCost();
		System.out.println("Please swipe your debit card.");
		try {
			Card.CardSwipeData data = this.userCard.swipe();
			System.out.println("Card has been successfully swiped.");
			
			System.out.println("Please enter your signature");
			String sig = sc.nextLine(); // TODO figure out what to do with the signature
			// after swiping authorize the transaction
			long blockNum = this.bank.authorizeHold(data.getNumber(), this.amountDue.doubleValue());
			if (blockNum != -1) {
				// if the hold is successful then post the transaction
				boolean posted = this.bank.postTransaction(data.getNumber(), blockNum, this.amountDue.doubleValue());
				if (!posted) {
					// if transaction unsuccessful then release the hold
					this.bank.releaseHold(data.getNumber(), blockNum);

				}
				// once that is all done then return the result of the transaction being posted
				return posted;

			}

		} catch (IOException e) {
			System.out.println("Error while swiping debit card, please try again.");
		}

		return false;
	}

	@Override
	public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub

	}

	@Override
	public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub

	}

	@Override
	public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub

	}

	@Override
	public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {
		// TODO Auto-generated method stub

	}

	@Override
	public BigDecimal getAmountPaid() {
		
		return this.amountPaid;
	}

	@Override
	public void aCardHasBeenSwiped() {
		// TODO Auto-generated method stub

	}

	@Override
	public void theDataFromACardHasBeenRead(CardData data) {
		// TODO Auto-generated method stub

	}

}
