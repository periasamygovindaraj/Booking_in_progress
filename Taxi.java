package com.own.book;

public class Taxi {
	 int taxi_no;
	int free_time;
	char current_position;
	int earnings;
	Customer customer;
	boolean booking_status;
	int total_earning;
	Taxi(int taxi_no,char current_position,int earnings,int free_time,boolean booking_status,int total_earning,Customer customer)
	{
		this.taxi_no=taxi_no;
		this.current_position=current_position;
		this.earnings=earnings;
		this.booking_status=booking_status;
		this.free_time=free_time;
		this.total_earning=total_earning;
		this.customer=customer;
	}
	
}
