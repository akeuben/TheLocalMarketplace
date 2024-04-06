package com.thelocalmarketplace.software.session;

import java.util.Map;
import java.util.Map.Entry;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.keyboard.KeyboardListener;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

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
		if (label == "Enter") {
			//checks barcoded item database to see if any items match object description
			for (Map.Entry<Barcode, BarcodedProduct> entry :ProductDatabases.BARCODED_PRODUCT_DATABASE.entrySet()) {
				BarcodedProduct itemToCheck = entry.getValue();
				if (itemToCheck.getDescription()==input) {
					//if item is found, item is added to transaction
					getUserSession().getTransaction().addItem(itemToCheck);
					input = null;
				}
			}
			//if item is not found, PLU coded item data base is checked
			//NOT WORKING, WAITING ON IMPLEMENTATION OF ADDING PLU ITEMS TO TRANSACTION
			
			if (input!=null) {
				for (Entry<PriceLookUpCode, PLUCodedProduct> entry :ProductDatabases.PLU_PRODUCT_DATABASE.entrySet()) {
					PLUCodedProduct itemToCheck = entry.getValue();
					/*
					if (itemToCheck.getDescription()==input) {
						getUserSession().getTransaction().addItem(itemToCheck);
						input = null;
					}
					*/
				}
			}
			
		}
		else {
		input = input+label;
		}
		
	}

}
