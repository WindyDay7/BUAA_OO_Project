package oo.app;

public enum TaxiStatus {
	STATIONARY,SERVING,WAITING4SERVING,ORDERTAKING;
	static public TaxiStatus parseTaxiStatus(String tmpstatus) {
		switch(tmpstatus) {
		case "STATIONARY":{
			return TaxiStatus.STATIONARY;
		}
		case "SERVING":{
			return TaxiStatus.SERVING;
		}
		case "WAITING4SERVING":{
			return TaxiStatus.WAITING4SERVING;
		}
		case "ORDERTAKING":{
			return TaxiStatus.ORDERTAKING;
		}
		default:{
			return TaxiStatus.WAITING4SERVING;
		}
		}
	}
}
