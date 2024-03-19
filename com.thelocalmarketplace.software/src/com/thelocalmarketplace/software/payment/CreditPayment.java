package com.thelocalmarketplace.software.payment;

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

import java.io.IOException;
import java.math.BigDecimal;

import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardData;
import com.jjjwelectronics.card.Card.CardSwipeData;
import com.jjjwelectronics.card.CardReaderListener;
import com.jjjwelectronics.card.AbstractCardReader;
import com.thelocalmarketplace.hardware.external.CardIssuer;
import com.thelocalmarketplace.software.session.UserSession;
import com.thelocalmarketplace.software.SelfCheckout;

public class CreditPayment implements IPayment{
	private BigDecimal amountDue;
	private BigDecimal amountPaid;
	private CardIssuer issuer;
	
	
	//TODO: utilize CardReaderListener
	//TODO: signature stuff
	//TODO: handling of multiple card payments 
	//TODO: find a proper way to differentiate between credit and debit card
	
	public CreditPayment(CardIssuer issuer, Card creditCard, String type) {
		this.issuer = issuer;
		amountDue = SelfCheckout.getInstance().getCurrentSession().getTransaction().getTotalCost(); // May need to change this to handle multiple card payments
		
		
		// Just handles swipe card for now, need to figure out how to use CardReaderListener
		if (type.toLowerCase().equals("swipe")) {
			boolean success = swipeCard(creditCard);
			if (success) {
				amountPaid = amountDue;			
			}
		}
	}
	
	/*
	 * Retrieves data from card to hold and post the transaction
	 * 
	 * @param credit card being used
	 * @return T/F if swipeCard is successful
	 */
	
	public boolean swipeCard(Card card) {
		CardSwipeData data;
		try {
			data = card.swipe();
			
			if(data.getType().toLowerCase().equals("credit")) { // Check if card is a credit card
				long holdNumber = issuer.authorizeHold(data.getNumber(), amountDue.doubleValue());
				
				if (holdNumber != -1) {
					boolean posted = issuer.postTransaction(data.getNumber(), holdNumber, amountDue.doubleValue());
					
					if (posted) {
						return true; // Successful postTransaction
					} else {
		                issuer.releaseHold(data.getNumber(), holdNumber); // If postTransaction fails and throws an exception, attempt to release the hold
						return false; // Indicate failure due to postTransaction failure
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return false; // Indicate failure due to IOException
		}
		
		return false; // Indicate failure due to holdNumber being -1
		
	}


	@Override
	public BigDecimal getAmountPaid() {
		return amountPaid;
	}

}
