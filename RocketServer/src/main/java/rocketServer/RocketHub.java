package rocketServer;

import java.io.IOException;

import exceptions.RateException;
import netgame.common.Hub;
import rocketBase.RateBLL;
import rocketData.LoanRequest;


public class RocketHub extends Hub {

	private RateBLL _RateBLL = new RateBLL();
	
	public RocketHub(int port) throws IOException {
		super(port);
	}

	@Override
	protected void messageReceived(int ClientID, Object message) {
		System.out.println("Message Received by Hub");
		
		if (message instanceof LoanRequest) {
			resetOutput();
			
			LoanRequest lq = (LoanRequest) message;
			
			//	 - RocketHub.messageReceived

			//	You will have to:
			//	Determine the rate with the given credit score (call RateBLL.getRate)
			//		If exception, show error message, stop processing
			//		If no exception, continue
			double dRate;
			try {
				 dRate = RateBLL.getRate(lq.getiCreditScore());
			} catch (RateException e) {
				System.out.println("Given Credit Score " + lq.getiCreditScore() + " does not meet minimum requirement of " +
									e.getRdm().getiMinCreditScore());
				e.printStackTrace(); 
				return;
			}
			//	Determine if payment, call RateBLL.getPayment
			lq.setdPayment(RateBLL.getPayment(dRate, lq.getiTerm(), lq.getdAmount(), 0.0, true));
			//	
			//	you should update lq, and then send lq back to the caller(s)
			
			sendToAll(lq);
		}
	}
}
