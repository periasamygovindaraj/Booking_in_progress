package com.own.book;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Book {
	static Scanner sc=new Scanner(System.in);
	
	static ArrayList<Customer> customers=new ArrayList<Customer>();
	static int total_taxies=4;
	public static void main(String[] args) {
		System.out.println("---------------Call taxi booking---------------");
		
		ArrayList<Taxi> taxies=new ArrayList<Taxi>();
		
		start(taxies);
		
	}
	public static void start(ArrayList<Taxi> taxies) {
		
		System.out.print("1.Booking"+"\n"+"2.Booking Details"+"\n"+"3.Taxi Detail"+"\n"+"4.Exit"+"\n"+"Enter your choice :");
		int key=sc.nextInt();
		Set_time(taxies);
		switch(key)
		{
		case 1:
			System.out.println("Enter your customer id :");
			int customer_id=sc.nextInt();
			System.out.println("Enter your pick-up point(a-f)");
			char pickup_point=sc.next().charAt(0);
			System.out.println("Enter your drop point(a-f)");
			char drop_point=sc.next().charAt(0);
			System.out.println("Enter your pick-up time(a-f)");
			int pick_up_time=sc.nextInt();
			Customer instand_customer=new Customer(customer_id,pickup_point,drop_point,pick_up_time);
			customers.add(instand_customer);
			for(int i=1;i<=total_taxies;i++)
			{
				taxies.add(new Taxi(i,'a',0,9,false,0,instand_customer));
			}
			Taxi available=BookTicket(instand_customer,taxies);
			if(available.booking_status == true)
			{
				System.out.println("your booking is success full with taxi no:"+available.taxi_no);
				earningCalculation(available,instand_customer);
				updateAllData(available,instand_customer);
			}
			else {
				System.out.println("taxi does not available");
			}
			start(taxies);
			break;
		case 2:
			System.out.println("Booking id    Customer id    Taxi_no    pick_up_point   drop_point    pick_up_time    drop_time    earnings");
			for(Taxi taxi_detail:taxies)
			{
				bookingDetail(taxi_detail);
			}
			start(taxies);
			break;
			
		case 3:
			taxiDetail(taxies);
			start(taxies);
			break;
			
		case 4:
			System.exit(0);
			break;
		}
	}

	private static void taxiDetail(ArrayList<Taxi> taxies) {
		
		for(Taxi taxi_detail:taxies)
		{
			System.out.println("-------------------------------------------------");
			System.out.println("Taxi no:    "+taxi_detail.taxi_no+"          CurrentPosition :"+taxi_detail.current_position);
			System.out.println("_________________________________________________");
			System.out.println("--------------------------------------------------");
			bookingDetail(taxi_detail);
			System.out.println("Total earnings :"+taxi_detail.total_earning);
		}
		
	}
	private static void updateAllData(Taxi available, Customer instand_customer) {
		available.current_position=instand_customer.drop_point;
		int calc_time=Math.abs(available.current_position-instand_customer.drop_point);
		available.free_time=available.free_time+calc_time;
		available.customer.drop_point=instand_customer.drop_point;
		available.customer.pickup_point=instand_customer.pickup_point;
		available.customer.pickup_time=instand_customer.pickup_time;
		available.total_earning=available.total_earning+available.earnings;
	}
	private static void earningCalculation(Taxi available, Customer instand_customer) {
		int total_kilometer=(instand_customer.pickup_point-instand_customer.drop_point)*15;
		int rest_kilometer=total_kilometer-5;
		int earnings=(rest_kilometer*10)+100;
		available.earnings=earnings;
	}
	private static void bookingDetail(Taxi taxi_detail) {
				
			if (taxi_detail.booking_status) {
				System.out.print("    "+taxi_detail.customer.booking_Id+"        ");
				System.out.print("     "+taxi_detail.customer.customer_Id+"         ");
				System.out.println("   "+taxi_detail.taxi_no+"       ");
				System.out.println("      "+taxi_detail.customer.pickup_point+"          ");
				System.out.println("    "+taxi_detail.customer.drop_point+"        ");
				System.out.println("     "+taxi_detail.customer.pickup_time+"        ");
				System.out.println("    "+taxi_detail.free_time+"       ");
				System.out.println("    "+taxi_detail.earnings+"       ");
			
			
		}
		
	}
	private static void Set_time(ArrayList<Taxi> taxies2) {
		for(Taxi timed_taxi : taxies2)
		{
			if(timed_taxi.free_time > 24)
			{
				timed_taxi.free_time=1;
			}
		}
		
	}
	private static Taxi BookTicket(Customer instandCustomer , ArrayList<Taxi> taxies) {
		
		int count=0;
		
		ArrayList<Taxi> taxi_in_same_place=new ArrayList<Taxi>();
		Taxi available_taxi=null;
		for(Taxi get_taxi : taxies)
		{
			if (get_taxi.current_position==instandCustomer.pickup_point) {
				taxi_in_same_place.add(get_taxi);
				count++;
			}
		}

		if (count>1) {
			available_taxi=findSmallestEarnings(taxi_in_same_place);
			
		}
		if (count==0) {
			available_taxi=findeNearset(taxies,instandCustomer);
		}
		available_taxi.booking_status=true;
		return available_taxi;
		
	}
	private static Taxi findeNearset(ArrayList<Taxi> taxies_without_same_position, Customer instandCustomer) {
		
		Taxi nearest_taxi=null;
		ArrayList<Taxi> taxi_in_same_place1=new ArrayList<Taxi>();
		int assumed_nearest=Math.abs(taxies_without_same_position.get(0).current_position-instandCustomer.pickup_point);
		for(Taxi available :taxies_without_same_position)
		{
			int taxi_time=Math.abs(available.free_time-instandCustomer.pickup_time);
			int nearest_distance =Math.abs(available.current_position-instandCustomer.pickup_point);
			if (assumed_nearest >= nearest_distance && available.free_time <= instandCustomer.pickup_time && taxi_time >= nearest_distance) {
				assumed_nearest=nearest_distance;
				taxi_in_same_place1.add(available);
			}
		}
		nearest_taxi=findSmallestEarnings(taxi_in_same_place1);
		return nearest_taxi;
		
	}

	private static Taxi findSmallestEarnings(ArrayList<Taxi> taxi_in_same_place ) {
		int money_kept=taxi_in_same_place.get(0).earnings;
		Taxi taxi_with_minimum_earning = null ;
		for(Taxi avail:taxi_in_same_place)
		{
			if (avail.earnings <= money_kept ) {
				money_kept=avail.earnings;
				taxi_with_minimum_earning=avail;
			}
		}
		
		return taxi_with_minimum_earning;
	}

}
