package com.thelocalmarketplace.software.UI.user;

import javax.swing.JPanel;

import com.thelocalmarketplace.software.Software;
import com.thelocalmarketplace.software.SoftwareObserver;
import com.thelocalmarketplace.software.UI.user.screens.WelcomeScreen;
import com.thelocalmarketplace.software.session.SessionObserver;
import com.thelocalmarketplace.software.session.UserSession;
import com.thelocalmarketplace.software.state.UserSessionState;

public class MainUserPanel extends JPanel implements SessionObserver, SoftwareObserver {
	
	private UserSessionState state = null;
	private int machineID;

	private static final long serialVersionUID = -7881208850880351803L;

	public MainUserPanel(int machineID) {
		this.machineID = machineID;
		Software.getInstance().register(machineID, this);
		redraw();
	}
	
	private void redraw() {
		removeAll();
		if(this.state == null) {
			add(new WelcomeScreen());
			return;
		}
	}

	@Override
	public void onStateChanged(UserSessionState newState) {
		this.state = newState;
	}

	@Override
	public void onSessionStart() {
		UserSession session = Software.getInstance().getCurrentSession(this.machineID);
		session.register(this);
		this.state = session.getState();
	}

	@Override
	public void onSessionEnd() {
		state = null;
	}
	
}
