package com.thelocalmarketplace.software.UI.hardwaresim;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.software.Software;

public abstract class AbstractHardwareSimTab extends JPanel {
	
	private static final long serialVersionUID = 5053495616877039194L;
	private int machineId;
	
	public AbstractHardwareSimTab(int machineId, int columns) {
		this.machineId = machineId;
		setLayout(new GridLayout(0, columns, 20, 20));
		setBorder(new EmptyBorder(10, 10, 10, 10));
	}
	
	public AbstractSelfCheckoutStation getHardware() {
		return Software.getInstance().getHardware(machineId);
	}
}
