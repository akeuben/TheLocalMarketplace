package com.thelocalmarketplace.software.session;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.keyboard.KeyboardListener;

public class AttendantKeyboardHandler extends AbstractUserSessionHandler implements KeyboardListener {
	
	private String input = new String();
	
	public AttendantKeyboardHandler(UserSession session) {
		super(session);
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
	public void aKeyHasBeenPressed(String label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aKeyHasBeenReleased(String label) {
		input = input+label;
		
	}
	
	public String getInput() {
		return input;
	}
	
	public void clear() {
		input = null;
	}

}
