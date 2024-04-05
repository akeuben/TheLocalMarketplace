package com.thelocalmarketplace.software.UI.hardwaresim;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.jjjwelectronics.OverloadedDevice;
import com.thelocalmarketplace.software.UI.components.ErrorPopup;

public class PrinterTab extends AbstractHardwareSimTab {

	private static final long serialVersionUID = -3729078427461109594L;

	JLabel inkRemainingLabel, paperRemainingLabel;
	
	public PrinterTab(int machineId) {
		super(machineId, 2);
		
		inkRemainingLabel = new JLabel("<loading>");
		paperRemainingLabel = new JLabel("<loading>");
		
		add(new JLabel("Ink Remaining: "));
		add(inkRemainingLabel);
		add(new JLabel("Paper Remaining: "));
		add(paperRemainingLabel);
		
		JButton refillInkBtn = new JButton("Refill Ink (100 units)");
		JButton refillPaperBtn = new JButton("Refill Paper (100 units)");
		
		refillInkBtn.addActionListener(this::refillInk);
		refillPaperBtn.addActionListener(this::refillPaper);
		
		add(refillInkBtn);
		add(refillPaperBtn);
		
		refreshUI();
	}

	
	void refillInk(ActionEvent e) {
		try {
			getHardware().getPrinter().addInk(100);
		} catch (OverloadedDevice e1) {
			ErrorPopup.showError("Failed to refill ink", "The ink is already full!");
		}
		
		refreshUI();
	}
	
	void refillPaper(ActionEvent e) {
		try {
			getHardware().getPrinter().addPaper(100);
		} catch (OverloadedDevice e1) {
			ErrorPopup.showError("Failed to refill paper", "The paper is already full!");
		}
		
		refreshUI();
	}
	
	void refreshUI() {
		try {
			inkRemainingLabel.setText("" + getHardware().getPrinter().inkRemaining());
		} catch(UnsupportedOperationException e) {
			inkRemainingLabel.setText("unsupported");
		}

		try {
			paperRemainingLabel.setText("" + getHardware().getPrinter().paperRemaining());
		} catch(UnsupportedOperationException e) {
			paperRemainingLabel.setText("unsupported");
		}
	}
}
