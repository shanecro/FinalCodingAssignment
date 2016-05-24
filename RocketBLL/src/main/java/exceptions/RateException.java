package exceptions;

import rocketDomain.RateDomainModel;

public class RateException extends Exception {

	//	* Add RateRomainModel as an attribute
	private RateDomainModel rdm;
	//	* Create a constructor, passing in RateDomainModel
	public RateException(RateDomainModel rdm){
		this.rdm = rdm;
	}
	//	* Create a getter (no setter, set value only in Constructor)
	public RateDomainModel getRdm() {
		return rdm;
	}
}
