package com.thelocalmarketplace.software.UI.user.screens;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class WelcomeScreen extends JPanel {

	private static final long serialVersionUID = 7502139702763571623L;

	public WelcomeScreen() {
		JLabel label = new JLabel("Touch anywhere to start!");
		label.setAlignmentX(CENTER_ALIGNMENT);
		add(label);
	}
	
}
