package com.thelocalmarketplace.software.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;


import com.tdc.banknote.BanknoteStorageUnit;
import com.tdc.banknote.IBanknoteDispenser;
import com.tdc.coin.CoinStorageUnit;
import com.tdc.coin.ICoinDispenser;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.software.state.UserSessionState;


public class PredictIssue {
	
		private UserSession session;
		
		public ArrayList<PredictIssueListener> listeners = new ArrayList<>();
		
		private CoinStorageUnit coinStorage;
		private BanknoteStorageUnit banknoteStorage;
		private Map<BigDecimal, IBanknoteDispenser> banknoteDispensers;
		private Map<BigDecimal, ICoinDispenser> coinDispensers;
		
		private boolean lowCoins;
		private boolean fullCoins;
		private boolean lowBanknotes;
		private boolean fullBanknotes;
		
		public boolean hasIssue = false;
		
		
		public PredictIssue(UserSession session) {
			this.session = session;
			
			// Get info on current hardware
			AbstractSelfCheckoutStation station = session.getHardware();
			
			coinStorage = station.getCoinStorage();
			banknoteStorage = station.getBanknoteStorage();
			coinDispensers = station.getCoinDispensers();
			banknoteDispensers = station.getBanknoteDispensers();
			
			
		}
		
		
		private void predictCoinsFull() {

			if(!coinStorage.hasSpace()) {
//				notifyCoinsFull(session);
				fullCoins = true;
			} else {
//				notifyNoIssues(session);
				fullCoins = false;
			}
			
		}
		
		private void predictBanknotesFull() {
			
			if (!banknoteStorage.hasSpace()) {
//				notifyBanknotesFull(session);
				fullBanknotes = true;
			} else {
//				notifyNoIssues(session);
				fullBanknotes = false;
			}
		}
		
		
		private void predictLowCoins() {
			
			for (ICoinDispenser dispenser : coinDispensers.values()) {
				int maxCoins = dispenser.getCapacity();
				int currentCoins = dispenser.size();
				
				if (currentCoins <= Math.floorDiv(maxCoins, 4)) {
//					notifyCoinsLow(session);
					lowCoins = true;
				} else {
//					notifyNoIssues(session);
					lowCoins = false;
				}
			}
		}
		
		
		private void predictLowBankNotes() {
			
			for (IBanknoteDispenser dispenser : banknoteDispensers.values()) {
				int maxNotes = dispenser.getCapacity();
				int currentNotes = dispenser.size();
				
				if (currentNotes <= Math.floorDiv(maxNotes, 4)) {
//					notifyBanknotesLow(session);
					lowBanknotes = true;
				} else {
//					notifyNoIssues(session);
					lowBanknotes = false;
				}
			}
		}
		
		
		public void predictAllIssues() {
			predictCoinsFull();
			predictBanknotesFull();
			predictLowCoins();
			predictLowBankNotes();
			
			boolean[] issues = {fullCoins, fullBanknotes, lowCoins, lowBanknotes};
			for (boolean i : issues) {
				if (i) {
					hasIssue = true;
				}
			}
			
		}
		
		
		
	
		
		
		
		
		
		

//	    private void notifyCoinsLow(UserSession session) {
//	    	for (PredictIssueListener l : listeners)
//				l.notifyPredictLowCoins(session);
//	    }
//
//	    private void notifyBanknotesLow(UserSession session) {
//	    	for (PredictIssueListener l : listeners)
//				l.notifyPredictLowBanknotes(session);
//	    }
//
//	    private void notifyCoinsFull(UserSession session) {
//	    	for (PredictIssueListener l : listeners)
//				l.notifyPredictCoinsFull(session);
//	    }
//
//	    private void notifyBanknotesFull(UserSession session) {
//	    	for (PredictIssueListener l : listeners)
//				l.notifyPredictBanknotesFull(session);
//	    }
//
//		private void notifyNoIssues(UserSession session) {
//			for (PredictIssueListener l : listeners)
//				l.notifyNoIssues(session);
//		}
//
//		public synchronized boolean deregister(PredictIssueListener listener) {
//			return listeners.remove(listener);
//		}
//
//		public synchronized void deregisterAll() {
//			listeners.clear();
//		}
//
//		public final synchronized void register(PredictIssueListener listener) {
//			listeners.add(listener);
//		}


}
