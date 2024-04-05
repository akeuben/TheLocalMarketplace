package com.thelocalmarketplace.software.UI.hardwaresim;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.software.Software;

public abstract class AbstractAttendantTab extends JPanel {
	
	private static final long serialVersionUID = 5053495616877039194L;
	
	public AbstractAttendantTab(int columns) {
		setLayout(new GridLayout(0, columns, 20, 20));
		setBorder(new EmptyBorder(10, 10, 10, 10));
	}
}
