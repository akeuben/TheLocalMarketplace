package com.thelocalmarketplace.software.state;

import java.math.BigDecimal;

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

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.card.Card.CardData;
import com.jjjwelectronics.scanner.Barcode;

public class WaitingForAttendantState implements IUserSessionState<UserSessionState> {

    @Override
    public UserSessionState onStateSet() {
    	//for now attendant state will automatically override back to ready for item
        return UserSessionState.READY_FOR_ITEM;
    }

    @Override
    public void onStateUnset() {

    }

    @Override
    public UserSessionState onScanBarcode(Barcode barcode) {
        return null;
    }

    @Override
    public UserSessionState onWeightChanged(Mass mass) {
        return null;
    }

    @Override
    public UserSessionState onCoinInserted(BigDecimal value) {
        return null;
    }

	@Override
	public UserSessionState onCardDataRead(CardData data) {
		return null;
	}
}
