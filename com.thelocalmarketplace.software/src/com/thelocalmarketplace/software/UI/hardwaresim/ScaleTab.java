package com.thelocalmarketplace.software.UI.hardwaresim;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.thelocalmarketplace.software.SelfCheckout;
import com.thelocalmarketplace.software.UI.hardwaresim.components.ScaleComponent;

public class ScaleTab extends JPanel {

	private static final long serialVersionUID = -5582251485153083830L;
	

	public ScaleTab() {
		setLayout(new GridLayout(0, 2, 20, 20));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		
		add(new ScaleComponent(SelfCheckout.getInstance().getHardware().getBaggingArea(), "Bagging Area"));
		add(new ScaleComponent(SelfCheckout.getInstance().getHardware().getScanningArea(), "Scanning Area"));
	}
	
	
}
