package rocketServer;

import java.io.IOException;

import org.hibernate.HibernateException;

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
			
			try {
				double rate = RateBLL.getRate(lq.getiCreditScore());
				lq.setdRate(rate);
				lq.setdPayment(RateBLL.getPayment(rate / 12.0, lq.getiTerm() * 12.0, lq.getdAmount(), 0.0, false));
			} catch (HibernateException e)  {
				sendToAll(e);
				return;
			} catch (RateException e) {
				sendToAll(e);
				return;
			} catch (Exception e) {
				sendToAll(e);
				return;
			}

			lq.setdPayment(RateBLL.maximumPayment(lq.getdIncome(), lq.getdExpenses()));

			sendToAll(lq);
		}
	}
}