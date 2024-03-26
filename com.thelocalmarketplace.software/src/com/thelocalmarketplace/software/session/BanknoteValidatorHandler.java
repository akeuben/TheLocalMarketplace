package com.thelocalmarketplace.software.session;

import java.math.BigDecimal;
import java.util.Currency;

import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.banknote.BanknoteValidator;
import com.tdc.banknote.BanknoteValidatorObserver;

public class BanknoteValidatorHandler extends AbstractUserSessionHandler implements BanknoteValidatorObserver {

	public BanknoteValidatorHandler(UserSession session) {
		super(session);
	}

	@Override
	public void enabled(IComponent<? extends IComponentObserver> component) {
	}

	@Override
	public void disabled(IComponent<? extends IComponentObserver> component) {
	}

	@Override
	public void turnedOn(IComponent<? extends IComponentObserver> component) {
	}

	@Override
	public void turnedOff(IComponent<? extends IComponentObserver> component) {
	}

	@Override
	public void goodBanknote(BanknoteValidator validator, Currency currency, BigDecimal denomination) {
		getUserSession().getState().onBanknoteInserted(denomination);
	}

	@Override
	public void badBanknote(BanknoteValidator validator) {
		// TODO Auto-generated method stub

	}

}
