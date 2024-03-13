package com.thelocalmarketplace.software.feature;

import com.thelocalmarketplace.software.UserSession;

public interface SelfCheckoutFeature {
	/**
	 * Called when a new user session is started
	 * @param session The new user session
	 */
	void onUserSessionStart(UserSession session);
	
	/**
	 * Called when a user session ends
	 * @param session The session that ended.
	 */
	void onUserSessionEnd(UserSession session);
}
