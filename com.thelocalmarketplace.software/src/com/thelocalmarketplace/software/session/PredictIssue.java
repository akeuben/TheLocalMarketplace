package com.thelocalmarketplace.software.session;

/**
 * SENG 300 Project - Group 1:
 * 
 * Avery Keuben - 30170731
 * Moiz Siddiqui - 30150291
 * Ammaar Melethil - 30141956
 * Joey Fisher - 30105628
 * Ethan Pangilinan - 30179143
 * Joshua Kraft - 30171525
 * Nathan Vaters - 30121908
 * Max Butcher - 30149202
 * Neeraj Ghansela - 30157473
 * Ansel Sulejmani - 30178521
 * Suleman Basit - 30132816
 * Jacob Boyden - 30193220
 * Cheshta Sharma - 30064538
 * Callum Bates - 30188601
 * Armughan Mustafa - 30154601
 * Connor Ell - 30073291
 * Saif Farag - 30195046
 * Ivan Agalakov - 30172107
 * Samuel Turner - 10064857
 * Stephanie Sevilla - 30176781
 * Winston Wang - 30185321
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import com.tdc.banknote.BanknoteStorageUnit;
import com.tdc.banknote.IBanknoteDispenser;
import com.tdc.coin.CoinStorageUnit;
import com.tdc.coin.ICoinDispenser;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

/**
 * Use cases predict low ink/paper were not implemented as the hardware already signals if current levels are <= 10% of the threshold
 */
public class PredictIssue {
	
		private UserSession session;
		
		public ArrayList<PredictIssueHandler> listeners = new ArrayList<>();
		
		private CoinStorageUnit coinStorage;
		private BanknoteStorageUnit banknoteStorage;
		private Map<BigDecimal, IBanknoteDispenser> banknoteDispensers;
		private Map<BigDecimal, ICoinDispenser> coinDispensers;
		
		public boolean lowCoins;
		public boolean fullCoins;
		public boolean lowBanknotes;
		public boolean fullBanknotes;
		
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
		
		
		/**
		 * Predicts if coinStorage is full
		 */
		private void predictCoinsFull() {

			if(!coinStorage.hasSpace()) {
				notifyCoinsFull(session);
				fullCoins = true;
			} else {
				notifyNoIssues(session);
				fullCoins = false;
			}
			
		}
		
		/**
		 * Predicts if banknoteStorage is full
		 */
		private void predictBanknotesFull() {
			
			if (!banknoteStorage.hasSpace()) {
				notifyBanknotesFull(session);
				fullBanknotes = true;
			} else {
				notifyNoIssues(session);
				fullBanknotes = false;
			}
		}
		
		/**
		 * Predicts if coinDispenser has low coins (25% of threshold)
		 */
		private void predictLowCoins() {
			
			for (ICoinDispenser dispenser : coinDispensers.values()) {
				int maxCoins = dispenser.getCapacity();
				int currentCoins = dispenser.size();
				
				if (currentCoins <= Math.floorDiv(maxCoins, 4)) {
					notifyCoinsLow(session);
					lowCoins = true;
				} else {
					notifyNoIssues(session);
					lowCoins = false;
				}
			}
		}
		
		/**
		 * Predicts if banknoteDispenser has low bank notes (25% of threshold)
		 */
		private void predictLowBankNotes() {
			
			for (IBanknoteDispenser dispenser : banknoteDispensers.values()) {
				int maxNotes = dispenser.getCapacity();
				int currentNotes = dispenser.size();
				
				if (currentNotes <= Math.floorDiv(maxNotes, 4)) {
					notifyBanknotesLow(session);
					lowBanknotes = true;
				} else {
					notifyNoIssues(session);  
					lowBanknotes = false;
				}
			}
		}
		
		
		/**
		 * Predicts all issues 
		 */
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
		

	    private void notifyCoinsLow(UserSession session) {
	    	for (PredictIssueHandler l : listeners)
				l.notifyPredictLowCoins(session);
	    }

	    private void notifyBanknotesLow(UserSession session) {
	    	for (PredictIssueHandler l : listeners)
				l.notifyPredictLowBanknotes(session);
	    }

	    private void notifyCoinsFull(UserSession session) {
	    	for (PredictIssueHandler l : listeners)
				l.notifyPredictCoinsFull(session);
	    }

	    private void notifyBanknotesFull(UserSession session) {
	    	for (PredictIssueHandler l : listeners)
				l.notifyPredictBanknotesFull(session);
	    }

		private void notifyNoIssues(UserSession session) {
			for (PredictIssueHandler l : listeners)
				l.notifyNoIssues(session);
		}

		public synchronized boolean deregister(PredictIssueHandler listener) {
			return listeners.remove(listener);
		}

		public synchronized void deregisterAll() {
			listeners.clear();
		}

		public final synchronized void register(PredictIssueHandler listener) {
			listeners.add(listener);
		}


}
