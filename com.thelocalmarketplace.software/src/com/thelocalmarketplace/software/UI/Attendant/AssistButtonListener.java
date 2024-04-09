package com.thelocalmarketplace.software.UI.Attendant;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class AssistButtonListener implements ActionListener {
	private int machineID; 
	private AttendantUI ui; 
	// get the attendant ui and the machineID to indicate which machine called the action method 
	// this way we can bring up the proper transaction viewer
	public AssistButtonListener(AttendantUI ui, int machineID) {
		this.ui = ui; 
		this.machineID = machineID; 
		// get the id and the ui
	}
	
	
	// want to create a new screen for the attendant UI
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton)e.getSource(); 
		// check source of the event, will always be a button
		if(source.getText().equals("Assist")) {
		// clear ui screen
		ui.getFrame().getContentPane().removeAll(); 
		ui.getFrame().getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints(); 
		
		// begin adding transaction viewer(don't have access to that yet)
		
		
		// add a button that can be used to set the screen back to what it was before
		JPanel buttonPanel = new JPanel(); 
		JButton button = new JButton("Close");
		button.setSize(75,50);
		buttonPanel.setPreferredSize(button.getSize());
		button.addActionListener(new AssistButtonListener(ui, machineID));
		buttonPanel.add(button); 
		c = new GridBagConstraints(); 
		c.gridx = 1; 
		c.gridy = 0; 
		c.weighty = 1;
		c.weightx = 1; 
		c.anchor = GridBagConstraints.SOUTHEAST; 
		ui.getFrame().getContentPane().add(button, c); 
		ui.getFrame().validate(); 
		
		}
		else {
			ui.redraw(); 
		}
	}

}
