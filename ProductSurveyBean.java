package com.xyz;

import java.util.ArrayList;

public class ProductSurveyBean {
	
	String productName;
	String[] demandArray;
	ArrayList<Double> surveyPrices;
	
	public ArrayList<Double> getSurveyPrices() {
		return surveyPrices;
	}
	public void setSurveyPrices(ArrayList<Double> surveyPrices) {
		this.surveyPrices = surveyPrices;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String[] getDemandArray() {
		return demandArray;
	}
	public void setDemandArray(String[] demandArray) {
		this.demandArray = demandArray;
	}
	

}
