package com.thelocalmarketplace.software.session;

import java.math.BigDecimal;

import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.coin.CoinValidator;
import com.tdc.coin.CoinValidatorObserver;
import com.thelocalmarketplace.software.state.UserSessionState;

public class CoinValidatorHandler extends AbstractUserSessionHandler implements CoinValidatorObserver {
	public CoinValidatorHandler(UserSession session) {
		super(session);
	}

	@Override
	public void enabled(IComponent<? extends IComponentObserver> component) {}

	@Override
	public void disabled(IComponent<? extends IComponentObserver> component) {}

	@Override
	public void turnedOn(IComponent<? extends IComponentObserver> component) {}

	@Override
	public void turnedOff(IComponent<? extends IComponentObserver> component) {}

	@Override
	public void validCoinDetected(CoinValidator validator, BigDecimal value) {
		UserSessionState newState = getUserSession().getState().onCoinInserted(value);
		if(newState != null) {
			getUserSession().setState(newState); 
		}
	}

	@Override
	public void invalidCoinDetected(CoinValidator validator) {
		// TODO Auto-generated method stub
		
	}
}
