package com.oasis.model;

public class Train 
{
	private int trainNo;
	private String trainName;
	
	public Train()	{
		
	}

	public Train(int trainNo, String trainName) {
		this.trainNo = trainNo;
		this.trainName = trainName;
	}

	public int getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(int trainNo) {
		this.trainNo = trainNo;
	}

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}	
	
}
