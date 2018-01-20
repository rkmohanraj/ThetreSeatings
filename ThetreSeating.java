import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.Iterator;

public class ThetreSeating {
	
	int[][] seatArray;
	HashMap<String, Integer> customerTktMap = new LinkedHashMap<>();
	

	public static void main(String[] args) {
		
		
		ThetreSeating thetreSeating = new ThetreSeating();
		thetreSeating.enterSeating();
		thetreSeating.enterOrder();
		thetreSeating.allocateSeat();

	}
	
	
	private void enterSeating()
	{
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter max rows in the thetre:");
		int rows = input.nextInt();
		System.out.println("Enter max sections in the thetre:");
		int section = input.nextInt();
		
		System.out.println("Max rows: "+rows);
		System.out.println("Max sections: "+section);
		
		seatArray =new int[rows][section];
		int displayRow;
		int displaySection;
		
		for (int i = 0; i < seatArray.length; i++) 
		{ 
			displayRow = i+1;
			for (int j = 0; j < seatArray[i].length; j++) 
			{ 
				displaySection = j+1;
				System.out.println("Enter no. of seats in Row : "+ displayRow + " Section : "+displaySection + "\n **** Enter 0 if no sets for the given section ***");
				seatArray[i][j] = input.nextInt(); 	
			} 
			
		}
		
		System.out.println("Seating arrangement: \n");
		for (int[] a : seatArray) 
		{ 
			for (int i : a) 
			{ 
				System.out.print(i + "\t"); 
			} 
			System.out.println("\n"); 
		}
	
	}
	
	
	private void enterOrder()
	{
		
		Scanner input = new Scanner(System.in);
		String custName;
		int tickets;
		while(true)
		{
		System.out.println("Enter Customer Name: ");
		custName = input.nextLine();
		System.out.println("Enter no. of Tickets: ");
		tickets = input.nextInt();
		if(customerTktMap.containsKey(custName))
		{
			System.out.println("Customer name already exists. Please enter different name");
		}
		else
		{
			customerTktMap.put(custName, tickets);
			System.out.println("Order Feeded : "+ custName +"    "+tickets);
		}		
		System.out.println("Press Enter to continue or E to Exit feeding");		
		input.nextLine();
		 
			if (input.nextLine().length() !=0)
			{
				break;
			}
		} 
		System.out.println(customerTktMap);
	}
	
	private void allocateSeat()
	{
	    int totalAvailableSeats = 0;
	    int dispRow = 0;
	    int displaySec = 0;
	    HashMap<String, String> outputMap = new LinkedHashMap<>();
		for (int i=0; i < seatArray.length; i++)
	    {
	        for (int j=0; j < seatArray[i].length; j++)
	        {
	        	totalAvailableSeats = totalAvailableSeats + seatArray [i][j];
	        }
	    }
		
		System.out.println("totalSeats: "+totalAvailableSeats);
		int tktRequired = 0;
		for (String custName : customerTktMap.keySet()){
            System.out.println(custName+" "+customerTktMap.get(custName));
            tktRequired = customerTktMap.get(custName);
            
            if(tktRequired > totalAvailableSeats)
            {
            	outputMap.put(custName, " Sorry, we can't handle your party.");
            }
            else 
            {
            	boolean setAllocated = false;
            	for (int i=0; i < seatArray.length; i++)
        	    {
        	        for (int j=0; j < seatArray[i].length; j++)
        	        {
        	        	if(seatArray [i][j] >= tktRequired)
        	        	{
        	        		dispRow = i+1;
        	        		displaySec = j+1;
        	        		outputMap.put(custName, " Row "+dispRow+" Section "+displaySec);
        	        		seatArray [i][j] = seatArray [i][j] - tktRequired;
        	        		totalAvailableSeats = totalAvailableSeats - tktRequired;
        	        		setAllocated = true;
        	        		break;
        	        	}
        	        }
        	        if(setAllocated)
    	        	{ break;}
    	        	else
    	        	{
    	        		outputMap.put(custName, " Call to split party.");
    	        	}
        	    }
            }
        }
		System.out.println("outputMap:"+outputMap);
		System.out.println("*****************************************OUTPUT********************************************************************");
		for(String custName : outputMap.keySet())
		{
			System.out.println(custName+" "+outputMap.get(custName)+"\n");
		}
		
		System.out.println("*******************************************************************************************************************");
	
	}

}
