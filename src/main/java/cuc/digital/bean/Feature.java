package cuc.digital.bean;

public class Feature {
	private String name;//特征的名称
	private int amount=0;//有多少个用户
	private double rating=0.0d;//特征的值
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	
}
