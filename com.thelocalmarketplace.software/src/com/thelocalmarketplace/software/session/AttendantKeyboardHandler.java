package com.thelocalmarketplace.software.session;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.keyboard.KeyboardListener;
import com.jjjwelectronics.scale.AbstractElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.external.ProductDatabases;
import com.thelocalmarketplace.software.Software;

public class AttendantKeyboardHandler extends AbstractUserSessionHandler implements KeyboardListener {
	
	private String input = new String();
	private ArrayList<Product> matchingItems = new ArrayList<>();
	
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
		
	}

	@Override
	public void aKeyHasBeenReleased(String label) {
		
		//if attendant selects enter and there no database check has been done, a database check is done to find elements containing keyword
		if (label=="Enter"&&matchingItems==null) {
			//checks barcoded item database to see if any items match object description
			for (Map.Entry<Barcode, BarcodedProduct> entry :ProductDatabases.BARCODED_PRODUCT_DATABASE.entrySet()) {
			BarcodedProduct itemToCheck = entry.getValue();
			if (itemToCheck.getDescription().contains(input)) {
				//if item is found, item is added to potential items array list
				matchingItems.add(itemToCheck);
	
			}
		}
		
		//checks PLUcoded item database to see if any items match object description
		for (Entry<PriceLookUpCode, PLUCodedProduct> entry :ProductDatabases.PLU_PRODUCT_DATABASE.entrySet()) {
			PLUCodedProduct itemToCheck = entry.getValue();
			if (itemToCheck.getDescription()==input) {
				//if item is found, item is added to potential items array list
				matchingItems.add(itemToCheck);
				}
			}
			input = null;
		}
		
		//if attendant selects enter after a database check has been done, input is used to make a selection from the choices and add item to transaction
		else if (label=="Enter"&& matchingItems!=null) {
			Integer i = new Integer(0);
			try {
				i = Integer.parseInt(input);
				getUserSession().getTransaction().addItem((BarcodedProduct) matchingItems.get(i-1));
			}
			catch (ClassCastException e) {
				i = Integer.parseInt(input);
				Mass massOnScale;
				try {
					massOnScale = (((AbstractElectronicScale) Software.getInstance().getHardware(0).getScanningArea()).getCurrentMassOnTheScale());
				} catch (OverloadedDevice f) {
					throw new RuntimeException("The scale is currently overloaded.");
				}
				getUserSession().getTransaction().addItem((PLUCodedProduct) matchingItems.get(i-1), massOnScale);
			}
			catch (NumberFormatException e) {
			}
			input=null;
		}
		
		//if backspace chosen, last element from input string removed
		else if (label=="Backspace") {
			input = input.substring(0, input.length()-1);
		}
		
		//concatonates label to end of input string
		else if (label.length()==1) {
			input += label;
		}
		
	}
	
	


}
