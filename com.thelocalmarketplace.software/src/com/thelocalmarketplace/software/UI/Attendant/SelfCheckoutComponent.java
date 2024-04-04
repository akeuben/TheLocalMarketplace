package com.thelocalmarketplace.software.UI.Attendant;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import com.thelocalmarketplace.software.UI.components.WrappedJComponent;

public class SelfCheckoutComponent extends JPanel{
	// gonna have a bidirectional communication channel with the self checkout station 
	// add a transaction viewer as a wrapped component as a JList
	private WrappedJComponent<JList<String>> transactionViewer; 
	private WrappedJComponent<JButton> alertButton; 
	private WrappedJComponent<JLabel> statusField;
	private WrappedJComponent<JButton> overrideButton; 
	
	
	// attendant's view of a self checkout machine
	public SelfCheckoutComponent() {
		// gridbaglayout will be used
		setLayout(new GridBagLayout());
		setBackground(Color.GRAY);
		
		// add the alert button
		alertButton = new WrappedJComponent<JButton>(JButton.class, "Alert");
		alertButton.setBackground(Color.GRAY);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; 
		c.gridy = 0;
		c.weightx = 1; 
		c.anchor = GridBagConstraints.NORTHEAST; 
		add(alertButton, c);
		
		// add the status field
		statusField = new WrappedJComponent<JLabel>(JLabel.class, "Status:");
		statusField.setBackground(Color.GRAY);
		statusField.getComponent().setFont(new Font("regular", Font.BOLD, 22));
		c = new GridBagConstraints(); 
		c.gridx = 1; 
		c.gridy = 0; 
		c.weightx = 1; 
		c.weighty = 1; 
		c.anchor = GridBagConstraints.NORTH;
		add(statusField, c);
		
		// add the override button 
		overrideButton = new WrappedJComponent<JButton>(JButton.class, "Override");
		overrideButton.setBackground(Color.GRAY);
		c = new GridBagConstraints();
		c.gridx = 2; 
		c.gridy = 0; 
		c.weightx = 1;
		c.anchor = GridBagConstraints.NORTHWEST; 
		add(overrideButton, c);
		
		// now add the transaction viewer as a jlist using default model
		
		
		
		setVisible(true);
		
	}

}
