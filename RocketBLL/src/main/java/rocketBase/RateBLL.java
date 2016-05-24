package rocketBase;

import java.util.ArrayList;

import org.apache.poi.ss.formula.functions.*;

import exceptions.RateException;
import rocketDomain.RateDomainModel;

public class RateBLL {

	private static RateDAL _RateDAL = new RateDAL();
	
	public static double getRate(int givenCreditScore) throws RateException 
	{
		// - RocketBLL RateBLL.getRate - make sure you throw any exception
		
		//		Call RateDAL.getAllRates... this returns an array of rates
		//		write the code that will search the rates to determine the 
		//		interest rate for the given credit score
		//		hints:  you have to sort the rates...  you can do this by using
		//			a comparator... or by using an OrderBy statement in the HQL
		
		
		ArrayList<RateDomainModel> rates = RateDAL.getAllRates();
		int size = rates.size();
		System.out.println ("Rates size: " + size);
		RateDomainModel rdmLast = null;
		for (RateDomainModel rdm : rates) { 	
			if (rdm.getiMinCreditScore() <= givenCreditScore ) {
				return rdm.getdInterestRate();
			}
			rdmLast = rdm;
		}
		
		//no rates found for my credit score, so throw exception
		throw new RateException(rdmLast);
	}
	
	
	// - RocketBLL RateBLL.getPayment 
	//		how to use:
	//		https://poi.apache.org/apidocs/org/apache/poi/ss/formula/functions/FinanceLib.html
	
	public static double getPayment(double r, double n, double p, double f, boolean t)
	{		
		return FinanceLib.pmt(r, n*12, p, f, t);
	}


public static double maximumPayment(double income, double expenses) {
	if (expenses >= income * LoanExpenses) {
		return 0.0;
	} else {
		return Math.min(income * LoanIncome, income * LoanExpenses - expenses);
	}
}
}
