package cuc.digital.bean;

public class RateRecord {
	private int moive_id;
	private int user_id;
	private double rate;
	public int getMoive_id() {
		return moive_id;
	}
	public void setMoive_id(int moive_id) {
		this.moive_id = moive_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	@Override
	public boolean equals(Object obj) {
		RateRecord record=(RateRecord)obj;
		if(record.getMoive_id()==this.moive_id)
			return true;
		return false;
	}

}
