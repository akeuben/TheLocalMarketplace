package com.thelocalmarketplace.software.UI.user.screens;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.thelocalmarketplace.software.Software;
import com.thelocalmarketplace.software.UI.components.TransactionView;
import com.thelocalmarketplace.software.UI.user.components.StatusBarComponent;

public class WaitingForBaggingScreen extends AbstractUserScreen {

	private static final long serialVersionUID = 6147707410164322045L;
	
	TransactionView view;
	StatusBarComponent statusbar;
	
	public WaitingForBaggingScreen(int machineID) {
		super(machineID);

		setLayout(new GridBagLayout());

		view = new TransactionView(machineID);
		view.connect(Software.getInstance().getCurrentSession(machineID).getTransaction());
		statusbar = new StatusBarComponent((e) -> {});
		statusbar.setInfoStatus("Place item in the bagging area.");

		redraw();
	}

	@Override
	public void redraw() {
		removeAll();
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTH;
		JScrollPane pane = new JScrollPane(view);
		add(pane, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.SOUTH;
		add(statusbar, gbc);
		
		revalidate();
		repaint();
	}
	
	@Override
	public void onScreenRemoved() {
		view.disconnect(Software.getInstance().getCurrentSession(machineID).getTransaction());
	}
}
