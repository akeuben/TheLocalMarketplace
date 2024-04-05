package com.thelocalmarketplace.software.UI.hardwaresim;

import com.thelocalmarketplace.software.UI.hardwaresim.components.ScaleComponent;

public class ScaleTab extends AbstractHardwareSimTab {

	private static final long serialVersionUID = -5582251485153083830L;
	

	public ScaleTab(int machineId) {
		super(machineId, 2);
		
		add(new ScaleComponent(getHardware().getBaggingArea(), "Bagging Area"));
		add(new ScaleComponent(getHardware().getScanningArea(), "Scanning Area"));
	}
	
	
}
