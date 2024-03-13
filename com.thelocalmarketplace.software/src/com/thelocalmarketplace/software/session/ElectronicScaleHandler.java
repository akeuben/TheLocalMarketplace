package com.thelocalmarketplace.software.session;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;

public class ElectronicScaleHandler extends AbstractUserSessionHandler implements ElectronicScaleListener {

	public ElectronicScaleHandler(UserSession session) {
		super(session);
	}

	@Override
	public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {}

	@Override
	public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {}

	@Override
	public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {}

	@Override
	public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {}

	@Override
	public void theMassOnTheScaleHasChanged(IElectronicScale scale, Mass mass) {
		getUserSession().getState().onWeightChanged(mass);
	}

	@Override
	public void theMassOnTheScaleHasExceededItsLimit(IElectronicScale scale) {
		System.out.println("WARNING: The electronic scale has exceeded its limit!");
	}

	@Override
	public void theMassOnTheScaleNoLongerExceedsItsLimit(IElectronicScale scale) {}

}
