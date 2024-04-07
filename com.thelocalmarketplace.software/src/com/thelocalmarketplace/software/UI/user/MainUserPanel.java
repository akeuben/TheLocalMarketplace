package com.thelocalmarketplace.software.UI.user;

import java.awt.desktop.UserSessionListener;

import javax.swing.JPanel;

import com.thelocalmarketplace.software.session.SessionObserver;
import com.thelocalmarketplace.software.state.UserSessionState;

public class MainUserPanel extends JPanel implements SessionObserver {

	private static final long serialVersionUID = -7881208850880351803L;

	public MainUserPanel() {
		
	}

	@Override
	public void onStateChanged(UserSessionState newState) {
	}
	
}
