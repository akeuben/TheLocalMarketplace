package com.thelocalmarketplace.software.session;

public class AbstractUserSessionHandler {
	private UserSession session;
	
	public AbstractUserSessionHandler(UserSession session) {
		this.session = session;
	}
	
	protected UserSession getUserSession() {
		return this.session;
	}
}
