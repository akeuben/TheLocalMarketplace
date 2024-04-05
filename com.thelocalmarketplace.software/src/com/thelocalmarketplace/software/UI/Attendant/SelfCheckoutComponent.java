package com.thelocalmarketplace.software.UI.Attendant;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.thelocalmarketplace.software.UI.components.WrappedJComponent;

public class SelfCheckoutComponent extends JPanel{
	// gonna have a bidirectional communication channel with the self checkout station 
	// add a transaction viewer as a wrapped component as a JList
	private JPanel transactionViewer;
	private JList<String> transactionList; 
	private DefaultListModel<String> transactionListModel; 
	private WrappedJComponent<JButton> alertButton; 
	private WrappedJComponent<JLabel> statusField;
	private WrappedJComponent<JButton> overrideButton;  
	private WrappedJComponent<JButton> assistButton; 
	
	// attendant's view of a self checkout machine
	public SelfCheckoutComponent() {
		// gridbaglayout will be used
		setLayout(new GridBagLayout());
		setBackground(Color.GRAY);
		
		// add the alert button
		alertButton = new WrappedJComponent<JButton>(JButton.class, "Alert");
		
		alertButton.setBackground(Color.GRAY);
		alertButton.getComponent().setSize(75, 50);
		alertButton.getComponent().addActionListener(new AlertButtonListener());
		alertButton.setPreferredSize(alertButton.getComponent().getSize());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0; 
		c.gridy = 0;
		c.weightx = 1; 
		c.anchor = GridBagConstraints.NORTH; 
		add(alertButton, c);
		
		// add the status field
		statusField = new WrappedJComponent<JLabel>(JLabel.class, "Status: Ready For Payment");
		statusField.getComponent().setSize(300, 90);
		statusField.setBackground(Color.GRAY);
		statusField.getComponent().setFont(new Font("regular", Font.BOLD, 22));
		statusField.setPreferredSize(statusField.getComponent().getSize());
		c = new GridBagConstraints(); 
		c.gridx = 1; 
		c.gridy = 0; 
		c.weightx = 1; 
		c.weighty = 0; 
		c.anchor = GridBagConstraints.NORTH;
		add(statusField, c);
		
		// add the override button 
		overrideButton = new WrappedJComponent<JButton>(JButton.class, "Override");
		overrideButton.getComponent().setSize(75,50);
		overrideButton.setPreferredSize(overrideButton.getComponent().getSize());
		overrideButton.setBackground(Color.GRAY);
		c = new GridBagConstraints();
		c.gridx = 2; 
		c.gridy = 0; 
		c.weightx = 0;
		c.anchor = GridBagConstraints.NORTH; 
		add(overrideButton, c);
		
		// now add the transaction viewer as a JList using default model
		transactionListModel = new DefaultListModel<String>();
		transactionViewer = new JPanel();
		transactionList = new JList<String>(transactionListModel);
		JScrollPane scroller = new JScrollPane(transactionList);
		transactionViewer.setLayout(new GridLayout(1,1));
		transactionViewer.add(scroller);
		transactionViewer.setBackground(Color.GRAY); 
		transactionViewer.setBorder(BorderFactory.createEmptyBorder(0,0 ,100,0));
		c = new GridBagConstraints();
		c.gridx = 1; 
		c.gridy = 1; 
		c.weightx = 1; 
		c.weighty  = 1; 
		c.gridheight = 2; 
		c.fill = GridBagConstraints.BOTH; 
		add(transactionViewer, c);
		
		// add the assist button
		assistButton = new WrappedJComponent<JButton>(JButton.class, "Assist");
		assistButton.getComponent().setSize(75, 50);
		assistButton.setPreferredSize(assistButton.getComponent().getSize());
		assistButton.setBackground(Color.GRAY);
		
		c = new GridBagConstraints();
		c.gridy = 2; 
		c.gridx = 2; 
		c.weightx = 0.1; 
		c.weighty = 0.1; 
		c.anchor = GridBagConstraints.SOUTHWEST; 
		add(assistButton, c);
		setVisible(true);
		
	}
	

}
