package com.thelocalmarketplace.software.UI.Attendant;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseButtonListener implements ActionListener {
	private AttendantUI ui;
	public CloseButtonListener(AttendantUI ui) {
		this.ui = ui; 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.ui.getFrame().removeAll(); 
		this.ui = new AttendantUI(); 

	}

}
