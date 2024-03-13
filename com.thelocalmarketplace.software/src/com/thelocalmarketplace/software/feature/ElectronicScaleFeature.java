package com.thelocalmarketplace.software.feature;

import com.jjjwelectronics.scale.ElectronicScale;
import com.thelocalmarketplace.software.session.UserSession;

public class ElectronicScaleFeature implements SelfCheckoutFeature {
	
	ElectronicScale scale;
	
	public ElectronicScaleFeature() {
		scale = new ElectronicScale();
	}

	@Override
	public void onUserSessionStart(UserSession session) {
		scale.register(session);
	}

	@Override
	public void onUserSessionEnd(UserSession session) {
		scale.deregister(session);
	}

	public ElectronicScale getScale() {
		return scale;
	}

}
