package com.thelocalmarketplace.software.UI.hardwaresim;

import com.thelocalmarketplace.software.UI.hardwaresim.components.ScannerComponent;

public class ScannerTab extends AbstractHardwareSimTab {

	private static final long serialVersionUID = 8792155096453005653L;
	

	public ScannerTab(int machineId) {
		super(machineId, 2);

		add(new ScannerComponent(getHardware().getMainScanner()));
		add(new ScannerComponent(getHardware().getHandheldScanner()));
	}
	
}
