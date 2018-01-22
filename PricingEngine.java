package com.xyz;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;

public class PricingEngine {

	

	public static void main(String[] args) {
		
		HashMap<String, ProductSurveyBean> productMap = readFileAndCreateMap("C:\\Pricing.txt");
		
		for(String productName : productMap.keySet())
		{
			ProductSurveyBean productSurveyBean = productMap.get(productName);
			compareBusinessRule(productSurveyBean);
			
		}
		
}
	
	private static void compareBusinessRule(ProductSurveyBean productSurveyBean)
	{
		double chosenPrice = 0;
		double lowestPrice = productSurveyBean.getSurveyPrices().get(0);
		double sumOfSurveyPrices = 0;
		int noOfSurveys = productSurveyBean.getSurveyPrices().size();
		boolean choseLowest = true;
		
		while (choseLowest)
		{
		for(double surveyPrice :  productSurveyBean.getSurveyPrices())
        {
			sumOfSurveyPrices = sumOfSurveyPrices + surveyPrice;
			lowestPrice = ((surveyPrice < lowestPrice) ? surveyPrice : lowestPrice);     
        }
		
		double averageOfSurveyPrices = sumOfSurveyPrices / noOfSurveys;
		if((lowestPrice < (averageOfSurveyPrices*0.5))  || (lowestPrice > ( lowestPrice + averageOfSurveyPrices*0.5)))
		{
			
			productSurveyBean.getSurveyPrices().remove(productSurveyBean.getSurveyPrices().indexOf(lowestPrice));
			choseLowest = true;
			lowestPrice = productSurveyBean.getSurveyPrices().get(0);
		}
		else
		{
			choseLowest = false;
		}
		
		}
		
		String supply = productSurveyBean.getDemandArray()[0];
		String demand = productSurveyBean.getDemandArray()[1];
		if(supply!= null && demand!= null)
		{
			if(supply.trim().equalsIgnoreCase("H") && demand.trim().equalsIgnoreCase("H"))
			{
				chosenPrice = lowestPrice;
			}
			else if(supply.trim().equalsIgnoreCase("L") && demand.trim().equalsIgnoreCase("L"))
			{
				chosenPrice = (lowestPrice*0.1) + lowestPrice;
			}
			else if(supply.trim().equalsIgnoreCase("L") && demand.trim().equalsIgnoreCase("H"))
			{
				chosenPrice = (lowestPrice*0.05) + lowestPrice;
			}
			else if(supply.trim().equalsIgnoreCase("H") && demand.trim().equalsIgnoreCase("L"))
			{
				chosenPrice = lowestPrice - (lowestPrice*0.05);
			}
		}
		
		
		
		System.out.println("ChosenPrice.........:"+ chosenPrice);
	}
	
	private static HashMap readFileAndCreateMap(String fileLocationAndName)
	{
		HashMap<String, ProductSurveyBean> productMap = new HashMap<>();
		//Perform all file level validations before proceeding.. thhat part is not coded
		try {
			FileReader reader = new FileReader(fileLocationAndName);
			BufferedReader buffReader = new BufferedReader(reader);
			String nextRecord = null;
			ArrayList<String> recordList = null;
			String data = null;
			int noOfProducts = 0;
			int noOfSurveys = 0;
			String[] demandArray = new String[2];
			
			nextRecord = buffReader.readLine();
			//String currentRecord = buffReader.readLine();
			while (nextRecord != null && nextRecord.trim().length() > 0) 
			{
				
				recordList = new ArrayList<String>();
				ArrayList<Double> surveyList = new ArrayList<>();
				//data = currentRecord;
				String result[] = nextRecord.split(" "); 
				if(result.length == 1 )
				{
					if(noOfProducts == 0) 
					{
						noOfProducts = Integer.parseInt(result[0]);
					}
					else
					{
						noOfSurveys = Integer.parseInt(result[0]);
					}
					
				}
				else
				{
					if(noOfProducts !=0 && noOfSurveys == 0 )
					{
						demandArray[0] = result[1];
						demandArray[1] = result[2];
						ProductSurveyBean productSurveyBean = new ProductSurveyBean();
						productSurveyBean.setProductName(result[0]);
						productSurveyBean.setDemandArray(demandArray);
						productSurveyBean.setSurveyPrices(new ArrayList<>());
						productMap.put(result[0], productSurveyBean);
						
					}
					else
					{
						ProductSurveyBean prodSurveyBean = productMap.get(result[0]);
						if(prodSurveyBean != null)
						{
						prodSurveyBean.getSurveyPrices().add(Double.parseDouble(result[2]));
						}
						
					}
				}
				
				nextRecord = buffReader.readLine();
			}
			
			buffReader.close();
			} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return productMap;
	}

	}
