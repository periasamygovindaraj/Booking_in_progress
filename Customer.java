package com.own.book;

public class Customer {
	static int customer_Id;
	static int booking_Id=0;
	char pickup_point;
	char drop_point;
	int pickup_time;
	//test
	
	public Customer(int customre_Id,char pickup_point,char drop_point,int pickup_time) {
		this.customer_Id=customre_Id;
		this.pickup_point=pickup_point;
		this.drop_point=drop_point;
		this.pickup_time=pickup_time;
		//test
	}
}
