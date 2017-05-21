package rocketBase;

import java.util.ArrayList;

import org.apache.poi.ss.formula.functions.*;
import org.hibernate.HibernateException;

import exceptions.RateException;
import rocketDomain.RateDomainModel;

public class RateBLL {
	
	private static ArrayList<RateDomainModel> Rates = null;
	public static int MinimumCreditScore;
	public static final double LoanIncome = 0.25;
	public static final double LoanExpenses = 0.36;

	
	public static double getRate(int GivenCreditScore) throws Exception {
		
		/* When run the first time, retrieve and save the Rates and MinimumCreditScore.
		 * Use the saved values for subsequent runs
		 */
		if (Rates == null) {
			try {
				Rates = RateDAL.getAllRates();
				MinimumCreditScore = 900;
				for (int i = 0; i < Rates.size(); i++) {
					int cr = Rates.get(i).getiMinCreditScore();
					if (cr < MinimumCreditScore) {
						MinimumCreditScore = cr;
					}
				}
			} catch (HibernateException e) {
				throw e;
			} catch (Exception e) {
				throw e;
			}
		}
		// The logic below relies on Rates being sorted by decreasing credit score
		RateDomainModel rdm = null;
		double rate = -1.0;
		for (int i = 0; i < Rates.size(); i++) {
			rdm = Rates.get(i);
			if (rdm.getiMinCreditScore() <= GivenCreditScore) {
				rate = rdm.getdInterestRate();
				break;
			}
		}
		if (rate < 0.0) {
			throw new RateException(rdm);
		} else {
			return rate / 100.0;
		}
	}
	
	//  RocketBLL RateBLL.getPayment 
	//		how to use:
	//		https://poi.apache.org/apidocs/org/apache/poi/ss/formula/functions/FinanceLib.html
	
	public static double getPayment(double r, double n, double p, double f, boolean t) {		
		return Math.abs(FinanceLib.pmt(r, n, p, f, t));
	}
		
	public static double maximumPayment(double income, double expenses) {
		if (expenses >= income * LoanExpenses) {
			return 0.0;
		} else {
			return Math.min(income * LoanIncome, income * LoanExpenses - expenses);
		}
	}
}